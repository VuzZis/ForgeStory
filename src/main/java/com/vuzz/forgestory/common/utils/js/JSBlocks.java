package com.vuzz.forgestory.common.utils.js;

import java.util.UUID;

import com.vuzz.forgestory.common.utils.stories.GlobalTicker;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;

public class JSBlocks implements JSElement {
    
    public ServerPlayerEntity player;

    public JSBlocks(ServerPlayerEntity player) {
        this.player = player;
    }

    public void monologue(String author, String text) {
        player.sendMessage(new StringTextComponent(
            "["+author+"] "+text
        ), Util.NIL_UUID);
    }

    public void queueScene(JSStory story, String sceneId, int delay) {
        story.story.queueScene(sceneId, delay);
    }

}
