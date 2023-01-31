package com.vuzz.forgestory.common.utils.stories;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.storage.FolderName;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vuzz.forgestory.common.utils.js.StoryData;
import com.vuzz.forgestory.common.utils.js.WorldStoriesData;

public class StoryParser {

    public static final List<Story> storiesLoaded = new ArrayList<>();

    public static FilenameFilter jsFilter = (dir, name) -> name.endsWith(".js");
    public static FilenameFilter jsonFilter = (dir, name) -> name.endsWith(".json");
    //public static FilenameFilter storiesFolderFilter = (dir, name) -> name.equals("stories") && dir.isDirectory();
    public static FileFilter onlyDir = File::isDirectory;

    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static String currentStory = "";

    public static ServerPlayerEntity player;

    @SuppressWarnings("all")
    public static void loadStories(ServerPlayerEntity playerA) {
        Path folderPath = Objects.requireNonNull(ServerLifecycleHooks.getCurrentServer()).getWorldPath(FolderName.ROOT);
        File folderFile = folderPath.toFile();
        File storiesFolder = new File(folderFile,"stories");
        storiesFolder.mkdir();
        File[] stories = storiesFolder.listFiles(onlyDir);
        storiesLoaded.clear();
        System.out.println("Reloading Stories folder..");
        for(File story:stories) {
            System.out.println("Story found: "+story.getName());
            parseStory(story,playerA);
        }
        currentStory = getCurStory(playerA);
        player = playerA;
    }

    public static void setCurStory(String story, ServerPlayerEntity player) {
        Path folderPath = Objects.requireNonNull(ServerLifecycleHooks.getCurrentServer()).getWorldPath(FolderName.ROOT);
        File folderFile = folderPath.toFile();
        File storiesFolder = new File(folderFile,"stories");
        storiesFolder.mkdir();
        File storiesData = new File(folderFile,"data.json");
        try {
            storiesData.createNewFile();
            WorldStoriesData worldData = gson.fromJson(new FileReader(storiesData),WorldStoriesData.class);
            if(worldData == null) worldData = new WorldStoriesData();
            FileWriter fileWriter = new FileWriter(storiesData);
            worldData.selectedStory = story;
            gson.toJson(worldData,fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public static String getCurStory(ServerPlayerEntity player) {
        Path folderPath = Objects.requireNonNull(ServerLifecycleHooks.getCurrentServer()).getWorldPath(FolderName.ROOT);
        File folderFile = folderPath.toFile();
        File storiesFolder = new File(folderFile,"stories");
        storiesFolder.mkdir();
        File storiesData = new File(folderFile,"data.json");
        String story = "";
        try {
            storiesData.createNewFile();
            WorldStoriesData worldData = gson.fromJson(new FileReader(storiesData),WorldStoriesData.class);
            if(worldData == null) worldData = new WorldStoriesData();
            FileWriter fileWriter = new FileWriter(storiesData);
            story = worldData.selectedStory;
            gson.toJson(worldData,fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return story;
    }

    public static int ticks = 0;

    public static void tick() {
        if(currentStory == "") return;
        Story story = getStory(currentStory);
        if(story == null) return;
        story.tick();
    }

    public static void parseStory(File fileStory, ServerPlayerEntity player) {
        Story story = new Story(fileStory.getName(),fileStory,player);
        storiesLoaded.add(story);
    }

    @Nullable
    public static Story getStory(String id) {
        for(Story story:storiesLoaded) {
            if(story == null) continue;
            if(story.storyId.equals(id)) return story;
        }
        System.out.println(id+" not found!");
        return null;
    }

}
