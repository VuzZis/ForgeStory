package com.vuzz.forgestory.common.entities;

import com.vuzz.forgestory.common.utils.VarsUtils;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class StoryEntities {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES
        = DeferredRegister.create(ForgeRegistries.ENTITIES, VarsUtils.MOD_ID);
    
    public static final RegistryObject<EntityType<NPCEntity>> NPC =
        ENTITY_TYPES.register("npc",
            () -> EntityType.Builder.of(NPCEntity::new,
                    EntityClassification.AMBIENT).sized(0.8f,1.85f)
                .build(new ResourceLocation(VarsUtils.MOD_ID, "npc").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}