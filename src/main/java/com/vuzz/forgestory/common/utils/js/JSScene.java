package com.vuzz.forgestory.common.utils.js;

import java.util.Iterator;
import java.util.function.Consumer;

import com.vuzz.forgestory.common.utils.stories.GlobalTicker;
import com.vuzz.forgestory.common.utils.stories.Scene;

public class JSScene {
    public final Scene scene;
    public JSScene(Scene scene) {
        this.scene = scene;
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

}
