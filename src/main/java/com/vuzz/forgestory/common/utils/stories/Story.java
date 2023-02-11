package com.vuzz.forgestory.common.utils.stories;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.vuzz.forgestory.common.utils.js.JSLibrary;
import com.vuzz.forgestory.common.utils.js.StoryData;

import net.minecraft.entity.player.ServerPlayerEntity;

public class Story {

    public String storyId = "";
    public File storyFile;
    public List<StoryScript> storyScripts = new ArrayList<>();
    public List<JSLibrary> storyLibs = new ArrayList<>();
    public List<Scene> storyScenes = new ArrayList<>();

    public List<Scene> activeScenes = new ArrayList<>();

    public int sceneTimer = 1;
    public String sceneInQueue = "starter";

    public ServerPlayerEntity player;

    private Gson gson = StoryParser.gson;

    public Story(String id, File file, ServerPlayerEntity player) {
        storyId = id;
        storyFile = file;
        this.player = player;
        reloadLibraries();
        reloadScripts();
        reloadScenes();
        sceneTimer = readTimer();
        sceneInQueue = readNextScene();
        queueScene(sceneInQueue, sceneTimer/20);
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

    public void reloadLibraries() {
        System.out.println("Loading libraries..");
        storyLibs.clear();
        File libsFolder = new File(storyFile,"libs");
        libsFolder.mkdir();
        File[] libs = libsFolder.listFiles(StoryParser.jsFilter);
        for(File lib: libs) {
            System.out.println("Found Lib: "+lib.getName());
            parseLib(lib);
        }
    }

    public void tick() {
        if(sceneTimer > 0) sceneTimer--;
        if(sceneTimer % 10 == 0 && sceneTimer != 0) saveTimer();
        if(sceneTimer == 0) {
            Scene scene = getScene(sceneInQueue);
            if(scene != null) {
                scene.start(player);
                sceneTimer = -1;
            }
        }
    }

    public void saveTimer() {
        setTimer(sceneTimer);
    }

    public void setTimer(int ticks) {
        File dataFile = new File(storyFile,"data");
        dataFile.mkdir();
        File storyDataFile = new File(dataFile,"story.json");
        try {
            storyDataFile.createNewFile();
            StoryData playerData = gson.fromJson(new FileReader(storyDataFile),StoryData.class);
            if(playerData == null) playerData = new StoryData();
            playerData.timerVal = ticks;
            FileWriter fileWriter = new FileWriter(storyDataFile);
            gson.toJson(playerData,fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void setNextScene(String scene) {
        File dataFile = new File(storyFile,"data");
        dataFile.mkdir();
        File storyDataFile = new File(dataFile,"story.json");
        try {
            storyDataFile.createNewFile();
            StoryData playerData = gson.fromJson(new FileReader(storyDataFile),StoryData.class);
            if(playerData == null) playerData = new StoryData();
            playerData.queuedScene = scene;
            FileWriter fileWriter = new FileWriter(storyDataFile);
            gson.toJson(playerData,fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public int readTimer() {
        File dataFile = new File(storyFile,"data");
        dataFile.mkdir();
        File playerDataFile = new File(dataFile,"story.json");
        try {
            playerDataFile.createNewFile();
            StoryData playerData = gson.fromJson(new FileReader(playerDataFile),StoryData.class);
            if(playerData == null) playerData = new StoryData();
            FileWriter fileWriter = new FileWriter(playerDataFile);
            gson.toJson(playerData,fileWriter);
            fileWriter.close();
            return playerData.timerVal;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String readNextScene() {
        File dataFile = new File(storyFile,"data");
        dataFile.mkdir();
        File playerDataFile = new File(dataFile,"story.json");
        try {
            playerDataFile.createNewFile();
            StoryData playerData = gson.fromJson(new FileReader(playerDataFile),StoryData.class);
            if(playerData == null) playerData = new StoryData();
            FileWriter fileWriter = new FileWriter(playerDataFile);
            gson.toJson(playerData,fileWriter);
            fileWriter.close();
            return playerData.queuedScene;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "starter";
    }

    public void queueScene(String scene, double timeInSecs) {
        sceneInQueue = scene;
        sceneTimer = (int) Math.round((timeInSecs*20));
        setNextScene(scene);
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

    public void parseLib(File script) {
        JSLibrary lib = new JSLibrary(script);
        storyLibs.add(lib);
    }

    public void parseScene(File scene) {
        Scene storyScene = new Scene(scene, this);
        storyScene.construct();
        storyScenes.add(storyScene);
    }

    @Nullable
    public StoryScript getScript(String id) {
        for(StoryScript script:storyScripts) {
            if(script == null) continue;
            if(script.scriptId.equals(id)) return script;
        }
        return null;
    }

    @Nullable
    public Scene getScene(String id) {
        for(Scene scene:storyScenes) {
            if(scene == null) continue;
            if(scene.sceneData.sceneId.equals(id)) return scene;
        }
        System.out.println("SC:"+id+" not found!");
        return null;
    }

}
