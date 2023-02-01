package com.vuzz.forgestory.common.utils.js;

import com.vuzz.forgestory.common.entities.NPCEntity;

import net.minecraft.util.math.BlockPos;

public class JSNpc {

    public NPCEntity entity;
    
    public JSNpc(NPCEntity npc) {
        entity = npc;
    }

    public void moveTo(double[] coords, double speed) {
        entity.setGoTo(new BlockPos(coords[0],coords[1],coords[2]));
        entity.setNSpeed((float) speed);
    }

    public void teleportTo(double[] coords) {
        entity.setPos(coords[0],coords[1],coords[2]);
    }

    public void face(int face) {
        entity.setFace(face);
    }

    


}
