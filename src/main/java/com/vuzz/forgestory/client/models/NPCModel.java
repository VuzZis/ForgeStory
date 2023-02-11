package com.vuzz.forgestory.client.models;

import com.vuzz.forgestory.common.entities.NPCEntity;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NPCModel extends AnimatedGeoModel<NPCEntity> {

    @Override
    public ResourceLocation getAnimationFileLocation(NPCEntity animatable) {
        return parsePath(animatable.getAnim());
    }

    @Override
    public ResourceLocation getModelLocation(NPCEntity object)  {
        return parsePath(object.getModel());
    }

    @Override
    public ResourceLocation getTextureLocation(NPCEntity object) {
        return new ResourceLocation("forgestory","textures/entity/npc.png");
    }

    public ResourceLocation parsePath(String path) {
        String id = path;
        String modId = "forgestory";
        String name = "animations/npc.animation.json";
        if(id.indexOf(":") != -1) {
            modId = id.substring(0,id.indexOf(":"));
        }
        name = id.substring(id.indexOf(":")+1);
        return new ResourceLocation(modId,name+(name.endsWith(".json") ? "" : ".json"));
    }
    
}
