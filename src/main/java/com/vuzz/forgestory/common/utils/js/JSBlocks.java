package com.vuzz.forgestory.common.utils.js;

import java.util.UUID;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;

public class JSBlocks {
    
    public ServerPlayerEntity player;

    public JSBlocks(ServerPlayerEntity player) {
        this.player = player;
    }

    public void monologue(String author, String text) {
        player.sendMessage(new StringTextComponent(
            "["+author+"] "+text
        ), Util.NIL_UUID);
    }

}
