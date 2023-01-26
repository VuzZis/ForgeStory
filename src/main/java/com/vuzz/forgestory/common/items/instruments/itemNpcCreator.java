package com.vuzz.forgestory.common.items.instruments;

import com.vuzz.forgestory.common.items.CustomItem;
import com.vuzz.forgestory.common.utils.VarsUtils;
import com.vuzz.forgestory.common.utils.stories.Story;
import com.vuzz.forgestory.common.utils.stories.StoryParser;
import com.vuzz.forgestory.common.utils.stories.StoryScript;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResultType;

public class itemNpcCreator extends CustomItem {

    public itemNpcCreator() {
        super(
                new Item.Properties()
                        .fireResistant()
                        .rarity(Rarity.EPIC)
                        .stacksTo(1)
                        .tab(VarsUtils.MOD_TAB)
        );
    }

    public ActionResultType onItemUseFirstServer(ItemStack stack, ItemUseContext context) {
        StoryParser.loadStories((ServerPlayerEntity) context.getPlayer());
        Story story = StoryParser.getStory("test");
        if(story != null) {
            StoryScript script = story.getScript("arbuz.js");
            if(script != null) {
                script.runScript((ServerPlayerEntity) context.getPlayer());
            }
        }
        return ActionResultType.PASS;
    }

    public ActionResultType onItemUseFirstClient(ItemStack stack, ItemUseContext context) {
        return ActionResultType.PASS;
    }
}
