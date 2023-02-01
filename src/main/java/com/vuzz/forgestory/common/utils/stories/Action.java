package com.vuzz.forgestory.common.utils.stories;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.vuzz.forgestory.common.utils.js.JSPlayer;
import com.vuzz.forgestory.common.utils.js.JSScriptFunctions;
import com.vuzz.forgestory.common.utils.js.JSStory;

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
        try {
            action.accept(scene);
        } catch(Exception e) {
            e.printStackTrace();
            new JSScriptFunctions(new JSStory(scene.story),new JSPlayer(scene.story.player,new JSStory(scene.story))).error(e.getMessage()+" :other");
        }
    }

}
