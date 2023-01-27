package com.vuzz.forgestory.common.utils.stories;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.storage.FolderName;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

public class StoryParser {

    public static final List<Story> storiesLoaded = new ArrayList<>();

    public static FilenameFilter jsFilter = (dir, name) -> name.endsWith(".js");
    //public static FilenameFilter storiesFolderFilter = (dir, name) -> name.equals("stories") && dir.isDirectory();
    public static FileFilter onlyDir = File::isDirectory;

    public static String currentStory = "";

    @SuppressWarnings("all")
    public static void loadStories(ServerPlayerEntity player) {
        Path folderPath = Objects.requireNonNull(player.level.getServer()).getWorldPath(FolderName.ROOT);
        File folderFile = folderPath.toFile();
        File storiesFolder = new File(folderFile,"stories");
        storiesFolder.mkdir();
        File[] stories = storiesFolder.listFiles(onlyDir);
        storiesLoaded.clear();
        System.out.println("Reloading Stories folder..");
        for(File story:stories) {
            parseStory(story);
            System.out.println("Story found: "+story.getName());
        }
    }

    public static void parseStory(File fileStory) {
        Story story = new Story(fileStory.getName(),fileStory);
        storiesLoaded.add(story);
    }

    @Nullable
    public static Story getStory(String id) {
        System.out.println("Finding S:"+id);
        for(Story story:storiesLoaded) {
            if(story.storyId.equals(id)) return story;
        }
        System.out.println(id+" not found!");
        return null;
    }

}
