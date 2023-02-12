package com.vuzz.forgestory.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.vuzz.forgestory.common.utils.VarsUtils;
import com.vuzz.forgestory.common.utils.stories.StoryParser;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class GeneralCommand {

    private static final SimpleCommandExceptionType UNKNOWN = new SimpleCommandExceptionType(new TranslationTextComponent("command." + VarsUtils.MOD_ID + ".unknown"));
    private static final SimpleCommandExceptionType STORY_NOT_FOUND = new SimpleCommandExceptionType(new TranslationTextComponent("command." + VarsUtils.MOD_ID + ".st_not_found"));

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("fs").requires(s -> s.hasPermission(1)
        )
        .then(Commands.literal("set_story")
            .then(Commands.argument("player", EntityArgument.player())
            .then(Commands.argument("story", StringArgumentType.string())
            .executes(GeneralCommand::setStory)))
        ).then(Commands.literal("refresh")
            .then(Commands.argument("player", EntityArgument.player())
            .executes(GeneralCommand::refresh))
        )
        );
    }

    private static int setStory(CommandContext<CommandSource> context) throws CommandSyntaxException {
        CommandSource source = context.getSource();
        String story = StringArgumentType.getString(context, "story");
        PlayerEntity player = EntityArgument.getPlayer(context, "player");
        if(StoryParser.hasStory(story)) {
            StoryParser.setCurStory(story, (ServerPlayerEntity) player);
            StoryParser.loadStories((ServerPlayerEntity) player);
            source.sendSuccess(new TranslationTextComponent("command."+VarsUtils.MOD_ID+".success"), false);
            return 0;
        } else {
            throw STORY_NOT_FOUND.create();
        }
    }

    private static int refresh(CommandContext<CommandSource> context) throws CommandSyntaxException {
        CommandSource source = context.getSource();
        PlayerEntity player = EntityArgument.getPlayer(context, "player");
        StoryParser.loadStories((ServerPlayerEntity) player);
        source.sendSuccess(new TranslationTextComponent("command."+VarsUtils.MOD_ID+".st_refresh"), false);
        return 0;
    }

}
