package com.vuzz.forgestory.common.networking;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface IProxy {
    Minecraft getMinecraft();
    PlayerEntity getPlayer();
    World getWorld();
}
