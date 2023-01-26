package com.vuzz.forgestory.common.utils.js;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.minecraft.entity.player.ServerPlayerEntity;

public class JSPlayer {
    
    public final ServerPlayerEntity entity;
    public final JSStory story;
    private static final Gson gson = new Gson();

    public JSPlayer(ServerPlayerEntity player, JSStory story) {
        entity = player;
        this.story = story;
    }

    public void writeData(String key, Object val) {
        File dataFile = new File(story.story.storyFile,"data");
        dataFile.mkdir();
        File playersDFile = new File(dataFile,"players");
        playersDFile.mkdir();
        File playerDataFile = new File(playersDFile,entity.getDisplayName().getString()+".json");
        try {
            playerDataFile.createNewFile();
            PlayerData playerData = gson.fromJson(new FileReader(playerDataFile),PlayerData.class);
            if(playerData == null) playerData = new PlayerData();
            playerData.playerScriptVariables.put(key,val);
            FileWriter fileWriter = new FileWriter(playerDataFile);
            gson.toJson(playerData,fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public Object readData(String key) {
        File dataFile = new File(story.story.storyFile,"data");
        dataFile.mkdir();
        File playersDFile = new File(dataFile,"players");
        playersDFile.mkdir();
        File playerDataFile = new File(playersDFile,entity.getDisplayName().getString()+".json");
        try {
            playerDataFile.createNewFile();
            PlayerData playerData = gson.fromJson(new FileReader(playerDataFile),PlayerData.class);
            if(playerData == null) playerData = new PlayerData();
            FileWriter fileWriter = new FileWriter(playerDataFile);
            gson.toJson(playerData,fileWriter);
            fileWriter.close();
            return playerData.playerScriptVariables.get(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
