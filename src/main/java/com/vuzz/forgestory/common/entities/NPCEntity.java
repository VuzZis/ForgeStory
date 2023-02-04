package com.vuzz.forgestory.common.entities;

import java.util.List;

import com.vuzz.forgestory.common.networking.NPCDataPacket;
import com.vuzz.forgestory.common.networking.Networking;

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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.HandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
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
        "animation.npcsteve.blinking",
        "animation.npcsteve.happy",
        "animation.npcsteve.angry",
        "animation.npcsteve.sad",
        "animation.npcsteve.terrified",
        "animation.npcsteve.smug"
    };

    public final String texturePath = "forgestory:textures/entity/npc.png";
    public final String name = "NPC";

    public final double[] goTo = new double[] {
        0,0,0
    };

    public final double speed = 1D;

    public final int curFace = 0;

    public int ticks = 0;
    public boolean followPl = false;
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
        super.tick();
        if(level.isClientSide) return;
        if(hasPlayerAround(15)) {
            PlayerEntity player = getPlayerAround(15);
            if (ticks % 20 == 0) lookAt(Type.EYES,new Vector3d(player.getX(),player.getY(),player.getZ()));
        }
        setCustomName(new StringTextComponent(getNName()));
        setCustomNameVisible(false);
        if(followPl) followPlayer();
        else {
            getNavigation().moveTo(
                getGoTo().getX(),
                getGoTo().getY(),
                getGoTo().getZ(),
                getNSpeed()
            );
        }
        if(ticks % 10 == 0) Networking.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), new NPCDataPacket(getFace(),getTexture(),getId()));
        if(immortal) setHealth(20);
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
                .loop("animation.npcsteve.walk");
            event.getController().setAnimation(face);
            return PlayState.CONTINUE;
        }
        AnimationBuilder face = new AnimationBuilder()
            .loop("animation.npcsteve.idle");
        event.getController().setAnimation(face);
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState predicateFace(AnimationEvent<E> event) {
        event.getController().transitionLengthTicks = 3;
        AnimationBuilder face = new AnimationBuilder()
            .loop(facesAnims[getFace()]);
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

    public String getTexture() { 
        return 
        getPersistentData().getString("texturePath") == "" 
        ? texturePath 
        : getPersistentData().getString("texturePath"); 
    }
    public void setTexture(String text) { 
        getPersistentData().putString("texturePath",text);
    }

    public String getNName() { 
        return 
        getPersistentData().getString("nname") == "" 
        ? name
        : getPersistentData().getString("nname"); 
    }
    public void setNName(String text) { getPersistentData().putString("nname", text);;}

    public float getNSpeed() {
        return 
        getPersistentData().getFloat("speed") == 0F
        ? (float) speed
        : getPersistentData().getFloat("speed"); 
    }
    public void setNSpeed(float speed) { getPersistentData().putFloat("speed", speed);;}

    public int getFace() {return getPersistentData().getInt("face");}
    public void setFace(int face) { 
        getPersistentData().putInt("face", face);;
    }

    public BlockPos getGoTo() { 
        return new BlockPos(
            getPersistentData().getDouble("gX"),
            getPersistentData().getDouble("gY"),
            getPersistentData().getDouble("gZ")); 
        }
    public void setGoTo(BlockPos pos) { 
        getPersistentData().putDouble("gX",pos.getX());
        getPersistentData().putDouble("gY",pos.getY());
        getPersistentData().putDouble("gZ",pos.getZ());
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
