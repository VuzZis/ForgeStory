package com.vuzz.forgestory.common.utils.js;

import java.util.Iterator;
import java.util.function.Consumer;

import com.vuzz.forgestory.common.utils.stories.GlobalTicker;
import com.vuzz.forgestory.common.utils.stories.Scene;

import net.minecraft.world.World;

public class JSScene implements JSElement {
    public final Scene scene;
    public JSScene(Scene scene) {
        this.scene = scene;
    }

    public void lockCamera(float pitch, float yaw, double[] pos) {
        scene.playerLocked = true;
        scene.setPlayerRotation(pitch, yaw);
        scene.lastPlayerPos = pos;
    }

    public void unlockCamera() {
        scene.playerLocked = false;
    }

    public void tickLoad() {
        GlobalTicker.loadedScenes.add(scene);
    }

    public void tickUnload() {
        int size = GlobalTicker.loadedScenes.size();
        for (int i = size - 1; i >= 0; i--) {
            GlobalTicker.loadedScenes.set(i, null);
        }
    }

    public void addAction(Consumer<Scene> callback,boolean breakA) {
        scene.addAction(callback,breakA);
    }

    public void createNpc(World world, String id, String name, String texture, double[] pos, float scale,String model,String anim) {
        scene.createNpc(world, id, name, texture, pos,scale,model,anim);
    }

    public void createNpc(World world, String id, String name, String texture, double[] pos, float scale) {
        scene.createNpc(world, id, name, texture, pos,scale,"forgestory:geo/npc.geo","forgestory:animations/npc.animation");
    }

    public void createNpc(World world, String id, String name, String texture, double[] pos) {
        scene.createNpc(world, id, name, texture, pos,1,"forgestory:geo/npc.geo","forgestory:animations/npc.animation");
    }

    public JSNpc getNpc(String id) {
        return scene.getNpc(id);
    }

    public void destroyNpc(String id) {
        scene.destroyNpc(id);
    }

}
