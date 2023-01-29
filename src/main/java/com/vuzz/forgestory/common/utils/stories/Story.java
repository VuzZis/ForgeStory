package com.vuzz.forgestory.common.utils.stories;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.entity.player.ServerPlayerEntity;

public class Story {

    public String storyId = "";
    public File storyFile;
    public List<StoryScript> storyScripts = new ArrayList<>();
    public List<Scene> storyScenes = new ArrayList<>();

    public List<Scene> activeScenes = new ArrayList<>();

    public int sceneTimer = 1;
    public String sceneInQueue = "starter";

    public ServerPlayerEntity player;

    public Story(String id, File file, ServerPlayerEntity player) {
        storyId = id;
        storyFile = file;
        this.player = player;
        reloadScripts();
        reloadScenes();
    }

    public void reloadScripts() {
        System.out.println("Loading scripts..");
        storyScripts.clear();
        File scriptsFolder = new File(storyFile,"scripts");
        scriptsFolder.mkdir();
        File[] scripts = scriptsFolder.listFiles(StoryParser.jsFilter);
        for(File script: scripts) {
            System.out.println("Found Script: "+script.getName());
            parseScript(script);
        }
    }

    public void tick() {
        if(sceneTimer > 0) sceneTimer--;
        if(sceneTimer == 0) {
            Scene scene = getScene(sceneInQueue);
            if(scene != null) {
                scene.start(player);
                sceneTimer = -1;
            }
        }
    }

    public void reloadScenes() {
        int i = 0;
        for(Scene scene: activeScenes) {
            activeScenes.set(i,null);
            i++;
        }
        System.out.println("Loading scenes..");
        storyScenes.clear();
        File scenesFolder = new File(storyFile,"scenes");
        scenesFolder.mkdir();
        File[] scenes = scenesFolder.listFiles(StoryParser.jsonFilter);
        for(File scene: scenes) {
            System.out.println("Found Scene: "+scene.getName());
            parseScene(scene);
        }
    }

    public void parseScript(File script) {
        StoryScript storyScript = new StoryScript(script.getName(),script,this);
        storyScripts.add(storyScript);
    }

    public void parseScene(File scene) {
        Scene storyScene = new Scene(scene, this);
        storyScene.construct();
        storyScenes.add(storyScene);
    }

    @Nullable
    public StoryScript getScript(String id) {
        for(StoryScript script:storyScripts) {
            if(script.scriptId.equals(id)) return script;
        }
        return null;
    }

    @Nullable
    public Scene getScene(String id) {
        System.out.println("Finding SC:"+id+"..");
        for(Scene scene:storyScenes) {
            if(scene.sceneData.sceneId.equals(id)) return scene;
        }
        System.out.println("SC:"+id+" not found!");
        return null;
    }

}
