package com.vuzz.forgestory.common.utils.js;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vuzz.forgestory.common.utils.stories.Story;

public class JSStory {
    
    public final Story story;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public JSStory(Story story) {
        this.story = story;
    }

    public void writeData(String key, Object val) {
        File dataFile = new File(story.storyFile,"data");
        dataFile.mkdir();
        File storyDataFile = new File(dataFile,"story.json");
        try {
            storyDataFile.createNewFile();
            StoryData playerData = gson.fromJson(new FileReader(storyDataFile),StoryData.class);
            if(playerData == null) playerData = new StoryData();
            playerData.storyScriptVariables.put(key,val);
            FileWriter fileWriter = new FileWriter(storyDataFile);
            gson.toJson(playerData,fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public Object readData(String key) {
        File dataFile = new File(story.storyFile,"data");
        dataFile.mkdir();
        File playerDataFile = new File(dataFile,"story.json");
        try {
            playerDataFile.createNewFile();
            StoryData playerData = gson.fromJson(new FileReader(playerDataFile),StoryData.class);
            if(playerData == null) playerData = new StoryData();
            FileWriter fileWriter = new FileWriter(playerDataFile);
            gson.toJson(playerData,fileWriter);
            fileWriter.close();
            return playerData.storyScriptVariables.get(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
