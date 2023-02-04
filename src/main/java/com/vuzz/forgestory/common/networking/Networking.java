package com.vuzz.forgestory.common.networking;

import com.vuzz.forgestory.common.utils.VarsUtils;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Networking {
    public static SimpleChannel CHANNEL;
    public static int ID;

    public static int nextID(){return ID++;}

    public static void register() {
        CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(VarsUtils.MOD_ID, "network"), () -> "1.0", s -> true, s -> true);
        CHANNEL.registerMessage(nextID(), NPCDataPacket.class, NPCDataPacket::encode, NPCDataPacket::new, NPCDataPacket::handle);
    }

}