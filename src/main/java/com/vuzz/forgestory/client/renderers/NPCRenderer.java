package com.vuzz.forgestory.client.renderers;

import com.vuzz.forgestory.client.models.NPCModel;
import com.vuzz.forgestory.common.entities.NPCEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class NPCRenderer extends GeoEntityRenderer<NPCEntity> {

    public NPCRenderer(EntityRendererManager renderManager) {
        super(renderManager,new NPCModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public ResourceLocation getTextureLocation(NPCEntity instance) {
        return new ResourceLocation("forgestory","textures/entity/npc.png");
    }
    
}
