package com.vuzz.forgestory.common.utils.stories;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.vuzz.forgestory.common.utils.js.SceneData;

public class Scene {

    public int currentAction = 0;
    public List<Action> actions = new ArrayList<>();
    public SceneData sceneData;

    public void addAction(Consumer<Scene> callback) {
        actions.add(new Action(this,callback));
    }

    public void playAction() {
        Action action = actions.get(currentAction);
        action.playAction();
        currentAction++;
    }

}
