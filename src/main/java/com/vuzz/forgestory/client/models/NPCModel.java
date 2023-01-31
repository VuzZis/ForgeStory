package com.vuzz.forgestory.client.models;

import com.vuzz.forgestory.common.entities.NPCEntity;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NPCModel extends AnimatedGeoModel<NPCEntity> {

    @Override
    public ResourceLocation getAnimationFileLocation(NPCEntity animatable) {
        return new ResourceLocation("forgestory","animations/npc.animation.json");
    }

    @Override
    public ResourceLocation getModelLocation(NPCEntity object)  {
        return new ResourceLocation("forgestory","geo/npc.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(NPCEntity object) {
        return new ResourceLocation("forgestory","textures/entity/npc.png");
    }
    
}
