package com.vuzz.forgestory.common.utils.stories;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Action {
    
    public Consumer<Scene> action;
    public final Scene scene;
    public boolean breakAfter = true;

    public Action(Scene scene, Consumer<Scene> callback, boolean breakA) {
        this.scene = scene;
        action = callback;
        breakAfter = breakA;
    }

    public void playAction() {
        action.accept(scene);
    }

}
