package com.vuzz.forgestory.common.utils.js;

import com.vuzz.forgestory.common.utils.VarsUtils;
import com.vuzz.forgestory.common.utils.stories.Story;
import com.vuzz.forgestory.common.utils.stories.StoryScript;

public class JSScriptFunctions {
    
    public final JSStory story;
    public final JSPlayer player;

    public final String version = "0.0";

    public JSScriptFunctions(JSStory story,JSPlayer player) {
        this.story = story;
        this.player = player;
    }

    public void runScript(String id) {
        StoryScript script = story.story.getScript(id);
        if(script != null) {
            script.runScript(player.entity);
            return;
        }
    }

    public void print(Object text) {
        VarsUtils.logs.add(String.valueOf(text));
        System.out.println(text);
    }

}
