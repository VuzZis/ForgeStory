package com.vuzz.forgestory.common.networking;

import java.util.function.Supplier;

import com.vuzz.forgestory.ForgeStory;
import com.vuzz.forgestory.common.entities.NPCEntity;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class NPCDataPacket {
    public int face;
    public String texture;
    public int entityUuid;

    @SuppressWarnings("unused")
    public NPCDataPacket(int face, String texture, int entityUuid) {
        this.face = face;
        this.texture = texture;
        this.entityUuid = entityUuid;
    }

    public NPCDataPacket(PacketBuffer buffer) {
        face = buffer.readInt();
        entityUuid = buffer.readInt();
        texture = buffer.readUtf();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(face);
        buffer.writeInt(entityUuid);
        buffer.writeUtf(texture);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            NPCEntity entity = (NPCEntity) ForgeStory.PROXY.getMinecraft().level.getEntity(entityUuid);
            if(entity == null) return; 
            entity.getPersistentData().putString("texturePath", texture);;
            entity.getPersistentData().putInt("face", face);;
            context.get().setPacketHandled(true);
        });
    }
}
