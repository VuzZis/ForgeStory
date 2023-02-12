package com.vuzz.forgestory.common.events;

import com.vuzz.forgestory.ForgeStory;
import com.vuzz.forgestory.client.renderers.RenderTest;
import com.vuzz.forgestory.common.utils.VarsUtils;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = ForgeStory.MOD_ID, bus = Bus.FORGE)
@SuppressWarnings("unused")
public class ForgeBusEvents {

    @SubscribeEvent
    public static void renderGameOverlay(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL && VarsUtils.blackScreen != 0) {
        	RenderTest render = new RenderTest(Minecraft.getInstance());
        	render.renderTestPumpkin(Minecraft.getInstance());
            VarsUtils.blackScreen -= 1;
        }
        if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            RenderTest render = new RenderTest(Minecraft.getInstance());
        }
    }


    @SubscribeEvent
    public static void renderGameOverlayPost(RenderGameOverlayEvent.Post event) {
        if(event.getType() != RenderGameOverlayEvent.ElementType.HEALTH
        && event.getType() != RenderGameOverlayEvent.ElementType.FOOD
        && event.getType() != RenderGameOverlayEvent.ElementType.HEALTHMOUNT
        && event.getType() != RenderGameOverlayEvent.ElementType.ARMOR
        && event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR
        && VarsUtils.hideGui
        ) {
            RenderTest render = new RenderTest(Minecraft.getInstance());
        }
        
    }

}
