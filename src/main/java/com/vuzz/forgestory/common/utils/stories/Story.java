package com.vuzz.forgestory.common.utils.stories;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class Story {

    public String storyId = "";
    public File storyFile;
    public List<StoryScript> storyScripts = new ArrayList<>();
    public Story(String id, File file) {
        storyId = id;
        storyFile = file;
        reloadScripts();
    }

    public void reloadScripts() {
        System.out.println("Loading scripts..");
        storyScripts.clear();
        File scriptsFolder = new File(storyFile,"scripts");
        scriptsFolder.mkdir();
        File[] scripts = scriptsFolder.listFiles(StoryParser.jsFilter);
        for(File script: scripts) {
            parseScript(script);
            System.out.println("Found Script: "+script.getName());
        }
    }

    public void parseScript(File script) {
        StoryScript storyScript = new StoryScript(script.getName(),script,this);
        storyScripts.add(storyScript);
    }

    @Nullable
    public StoryScript getScript(String id) {
        for(StoryScript script:storyScripts) {
            if(script.scriptId.equals(id)) return script;
        }
        return null;
    }

}
