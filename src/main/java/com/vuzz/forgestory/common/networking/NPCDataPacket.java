package com.vuzz.forgestory.common.networking;

import java.util.function.Supplier;

import com.vuzz.forgestory.ForgeStory;
import com.vuzz.forgestory.common.entities.NPCEntity;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

@SuppressWarnings("all")
public class NPCDataPacket {
    public int face;
    public String texture;
    public int entityUuid;
    public float scale;
    public String model;
    public String anim;
    public String aPlay;
    public String aHold;
    public String aLastFrame;

    @SuppressWarnings("unused")
    public NPCDataPacket(int face, String texture, int entityUuid, float scale, String model, String anim,
        String aPlay, String aHold, String aLastFrame
    ) {
        this.face = face;
        this.texture = texture;
        this.entityUuid = entityUuid;
        this.scale = scale;
        this.model = model;
        this.anim = anim;
        this.aPlay = aPlay;
        this.aHold = aHold;
        this.aLastFrame = aLastFrame;
    }

    public NPCDataPacket(PacketBuffer buffer) {
        face = buffer.readInt();
        entityUuid = buffer.readInt();
        texture = buffer.readUtf();
        scale = buffer.readFloat();
        model = buffer.readUtf();
        anim = buffer.readUtf();
        aPlay = buffer.readUtf();
        aHold = buffer.readUtf();
        aLastFrame = buffer.readUtf();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(face);
        buffer.writeInt(entityUuid);
        buffer.writeUtf(texture);
        buffer.writeFloat(scale);
        buffer.writeUtf(model);
        buffer.writeUtf(anim);
        buffer.writeUtf(aPlay);
        buffer.writeUtf(aHold);
        buffer.writeUtf(aLastFrame);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            NPCEntity entity = (NPCEntity) ForgeStory.PROXY.getMinecraft().level.getEntity(entityUuid);
            if(entity == null) return; 
            entity.getPersistentData().putString("texturePath", texture);;
            entity.getPersistentData().putInt("face", face);;
            entity.getPersistentData().putFloat("scale", scale);;
            entity.getPersistentData().putString("model",model);
            entity.getPersistentData().putString("anim", anim);
            entity.getPersistentData().putString("a_play", aPlay);
            entity.getPersistentData().putString("a_hold", aLastFrame);
            entity.getPersistentData().putString("a_loop", aHold);
            context.get().setPacketHandled(true);
        });
    }
}
