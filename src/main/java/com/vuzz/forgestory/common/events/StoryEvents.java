package com.vuzz.forgestory.common.events;

import com.vuzz.forgestory.ForgeStory;
import com.vuzz.forgestory.common.utils.VarsUtils;
import com.vuzz.forgestory.common.utils.stories.GlobalTicker;
import com.vuzz.forgestory.common.utils.stories.Scene;
import com.vuzz.forgestory.common.utils.stories.Story;
import com.vuzz.forgestory.common.utils.stories.StoryParser;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

@Mod.EventBusSubscriber(modid = ForgeStory.MOD_ID)
public class StoryEvents {
    
    @SubscribeEvent
    public static void onTickEvent(WorldTickEvent event) {
        if(event.world.isClientSide) return;
        GlobalTicker.tick();
    }

    @SubscribeEvent
    public static void clientTick(ClientTickEvent event) {
        if(event.phase == Phase.START && ForgeStory.keyStory.consumeClick()) {
            for(Story story : StoryParser.storiesLoaded) {
                for(Scene scene : story.activeScenes) {
                    if(scene != null) {
                        scene.playAction();
                    }
                }
            }

        }
    }

    @SubscribeEvent
    public static void playerJoined(PlayerLoggedInEvent event) {
        playerDelayedJoin(event.getPlayer());
    }
    
    public static void playerDelayedJoin(PlayerEntity player) {
        StoryParser.loadStories((ServerPlayerEntity) player);
        String selectedStory = StoryParser.currentStory;
        if(selectedStory == "") {
            player.sendMessage(new StringTextComponent(VarsUtils.trans("message.forgestory.storyunselected")), Util.NIL_UUID);
        }
        if(StoryParser.storiesLoaded.size() == 0) {
            player.sendMessage(new StringTextComponent(VarsUtils.trans("message.forgestory.noavailablestories")), Util.NIL_UUID);
        }
    }



}
