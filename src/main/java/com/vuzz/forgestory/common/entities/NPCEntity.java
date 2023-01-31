package com.vuzz.forgestory.common.entities;

import java.util.List;

import net.minecraft.command.arguments.EntityAnchorArgument.Type;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class NPCEntity extends MobEntity implements IAnimatable {
    private AnimationFactory anFactory = new AnimationFactory(this);

    private final NonNullList<ItemStack> armorInv = NonNullList.withSize(4, ItemStack.EMPTY);

    private final String[] facesAnims = new String[] {
        "animation.npc.default",
        "animation.npc.sad",
        "animation.npc.happy",
        "animation.npc.wow",
        "animation.npc.terrified",
        "animation.npc.angry",
        "animation.npc.sus",
        "animation.npc.smug",
        "animation.npc.eyebrow_raise"
    };

    public enum Faces {

        DEFAULT(0),
        SAD(1),
        HAPPY(2),
        WOW(3),
        TERRIFIED(4),
        ANGRY(5),
        SUSPICIOUS(6),
        SMUG(7),
        EYEBROW_RAISE(8);

        private int index = 0;
        private Faces(int ind) {
            this.index = ind;
        }
        public int ind() {
            return this.index;
        }

    }

    public Faces curFace = Faces.DEFAULT;

    public int ticks = 0;
    public boolean followPl = true;
    public boolean immortal = true;

    public NPCEntity(EntityType<? extends MobEntity> type, World world) {
        super(type,world);
    }

    public static AttributeModifierMap.MutableAttribute genAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH,20.0D)
                .add(Attributes.ARMOR,4D)
                .add(Attributes.ARMOR_TOUGHNESS,4D)
                .add(Attributes.MOVEMENT_SPEED,0.3);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this,"controller",5,this::predicateMove));
        data.addAnimationController(new AnimationController(this,"faces",5,this::predicateFace));
    }

    @Override
    public void tick() {
        if(hasPlayerAround(15)) {
            PlayerEntity player = getPlayerAround(15);
            if (ticks % 20 == 0) lookAt(Type.EYES,new Vector3d(player.getX(),player.getY(),player.getZ()));
        }
        if(followPl) followPlayer();
        if(immortal) setHealth(20);
        super.tick();
        ticks++;
    }

    public void followPlayer() {
        if(!hasPlayerAround(30) || hasPlayerAround(3)) getNavigation().stop();
        else {
            PlayerEntity player = getPlayerAround(30);
            getNavigation().moveTo(player, 1.0D);
    }   }

    private <E extends IAnimatable> PlayState predicateMove(AnimationEvent<E> event) {
        event.getController().transitionLengthTicks = 5;
        if (event.isMoving()) {
            AnimationBuilder face = new AnimationBuilder()
                .loop("animation.npc.walking");
            event.getController().setAnimation(face);
            return PlayState.CONTINUE;
        }
        AnimationBuilder face = new AnimationBuilder()
            .loop("animation.npc.idle");
        event.getController().setAnimation(face);
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState predicateFace(AnimationEvent<E> event) {
        event.getController().transitionLengthTicks = 5;
        AnimationBuilder face = new AnimationBuilder()
            .loop(facesAnims[curFace.ind()]);
        event.getController().setAnimation(face);
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.anFactory;
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return armorInv;
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlotType arg0) {
        return ItemStack.EMPTY;
    }

    @Override
    public HandSide getMainArm() {
        return HandSide.RIGHT;
    }

    @Override
    public void setItemSlot(EquipmentSlotType arg0, ItemStack arg1) {

    }

    public PlayerEntity getPlayerAround(int radius) {
        List<Entity> entitiesClose = ((World) level).getEntities(this,getBoundingBox().inflate(radius));
        PlayerEntity player = null;
        for(int i = 0; i < entitiesClose.size(); i++) {
            if(!(entitiesClose.get(i) instanceof LivingEntity)) continue;
            LivingEntity entityToBeat = (LivingEntity) entitiesClose.get(i);
            if(entityToBeat instanceof PlayerEntity) {
                player = (PlayerEntity) entityToBeat;
            }
        }
        return player;
    }

    public boolean hasPlayerAround(int radius) {
        List<Entity> entitiesClose = ((World) level).getEntities(this,getBoundingBox().inflate(radius));
        boolean bool = false;
        for(int i = 0; i < entitiesClose.size(); i++) {
            if(!(entitiesClose.get(i) instanceof LivingEntity)) continue;
            LivingEntity entityToBeat = (LivingEntity) entitiesClose.get(i);
            if(entityToBeat instanceof PlayerEntity) {
                bool = true;
            }
        }
        return bool;
    }
    
}
