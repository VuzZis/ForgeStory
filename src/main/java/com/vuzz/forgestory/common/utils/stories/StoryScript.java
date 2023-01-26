package com.vuzz.forgestory.common.utils.stories;

import java.io.File;
import java.io.FileReader;

import org.mozilla.javascript.ClassShutter;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptableObject;

import com.vuzz.forgestory.common.utils.js.JSPlayer;
import com.vuzz.forgestory.common.utils.js.JSStory;

import net.minecraft.entity.player.ServerPlayerEntity;

public class StoryScript {

    public String scriptId = "";
    public File scriptCode;
    public Story story;


    public StoryScript(String id,File code,Story storyC) {
        scriptId = id;
        scriptCode = code;
        story = storyC;
    }

    public void runScript(ServerPlayerEntity player) {
        Context ctx = Context.enter();
        ScriptableObject scope = ctx.initStandardObjects();

        JSStory storyJS = new JSStory(story);
        
        ScriptableObject.putConstProperty(scope, "out",System.out);
        ScriptableObject.putConstProperty(scope, "story",storyJS);
        ScriptableObject.putConstProperty(scope, "player",new JSPlayer(player,storyJS));
        System.out.println("Running script: "+scriptId);
        try {
            ctx.evaluateReader(scope, new FileReader(scriptCode), null,1,null);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
