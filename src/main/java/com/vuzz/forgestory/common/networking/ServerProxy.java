package com.vuzz.forgestory.common.networking;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ServerProxy implements IProxy {

    @Override
    public Minecraft getMinecraft() {
        return null;
    }

    @Override
    public PlayerEntity getPlayer() {
        return null;
    }

    @Override
    public World getWorld() {
        return null;
    }
    
}