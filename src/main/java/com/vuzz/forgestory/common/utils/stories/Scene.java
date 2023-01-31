package com.vuzz.forgestory.common.utils.stories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.vuzz.forgestory.common.utils.js.JSBlocks;
import com.vuzz.forgestory.common.utils.js.JSPlayer;
import com.vuzz.forgestory.common.utils.js.JSScriptFunctions;
import com.vuzz.forgestory.common.utils.js.JSStory;
import com.vuzz.forgestory.common.utils.js.SceneData;

import net.minecraft.entity.player.ServerPlayerEntity;

public class Scene {

    public int currentAction = 0;
    public List<Action> actions = new ArrayList<>();
    public SceneData sceneData;
    public File sceneFile;
    public Story story;

    public boolean locked = false;

    public JSBlocks blocks;
    public JSScriptFunctions func;

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final List<StoryScript> loadedScripts = new ArrayList<>();

    public Scene(File file, Story story) {
        sceneFile = file;
        this.story = story;
    }

    public void construct() {
        try {
            BufferedReader reader = Files.newBufferedReader(sceneFile.toPath(), StandardCharsets.UTF_8);
            SceneData data = gson.fromJson(reader,SceneData.class);
            if(data == null) data = new SceneData();
            BufferedWriter writer = Files.newBufferedWriter(sceneFile.toPath(), StandardCharsets.UTF_8);
            gson.toJson(data,writer);
            writer.close();
            sceneData = data;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void invalidateScripts() {
        int size = loadedScripts.size();
        for (int i = size - 1; i >= 0; i--) {
            loadedScripts.set(i, null);
        }
    }

    public void queueScene(String scene, int timeInSecs) {
        
    }

    public void loadScript(String script,ServerPlayerEntity player) {
        StoryScript sscript = story.getScript(script);
        if(sscript != null) {
            sscript.ticks = 0;
            sscript.runScript(player, this);
            loadedScripts.add(sscript);
        }
    }

    public void addAction(Consumer<Scene> callback,boolean breakA) {
        actions.add(new Action(this,callback,breakA));
    }

    int index = 0;

    public void start(ServerPlayerEntity player) {
        blocks = new JSBlocks(player);
        JSStory stor = new JSStory(story);
        JSPlayer pla = new JSPlayer(player, stor);
        func = new JSScriptFunctions(stor,pla);
        story.activeScenes.add(this);
        index = story.activeScenes.size()-1;
        switch(sceneData.type) {

            case "js":
                loadScript(sceneData.scriptId, player);
                playAction();
            break;

            case "ext":
            case "bit-ext":
                loadScript(sceneData.scriptId, player);
                assetActions();
                playAction();
            break;

            case "json":
                assetActions();
                playAction();
            break;
        }

    }

    public void end() {
        story.activeScenes.set(index,null);
    }

    public void assetActions() {
        sceneData.actions.forEach((e) -> {
            switch(sceneData.type) {
                case "ext":
                case "json":
                    defaultAction(e);
                break;
                case "bit":
                break;
            }
        });
    }

    public void defaultAction(HashMap<String,Object> data) {
        String id = (String) data.get("id");
        if(id == null) return;
        boolean breakA = (boolean) data.get("break");
        switch(id) {
            case "monologue":
                String author = (String) data.get("author");
                String text = (String) data.get("text");
                addAction((e) -> blocks.monologue(author,text),breakA);
            break;
            case "command":
                String command = (String) data.get("command");
                addAction((e) -> func.command(command),breakA);
            break;
            case "placeholder":
                addAction((e) -> {},breakA);
            break;
            case "delay_scene":
                double delayedBy = (double) data.get("delay");
                String sceneId = (String) data.get("sceneId");
                addAction((e) -> story.queueScene(sceneId, delayedBy), breakA);
            break;
            case "end":
                addAction((e) -> this.end(),breakA);
            break;
        }
    }

    public void playAction() {
        if(locked) playAction();
        if(currentAction >= actions.size()) return;
        Action action = actions.get(currentAction);
        action.playAction();
        currentAction++;
        if(!action.breakAfter) {
            playAction();
        }
    }

    public void tick() {
        for (StoryScript storyScript : loadedScripts) {
            if(storyScript != null) storyScript.tick();
        }
    }

    public void pressedAction() {
        playAction();
    }

}
