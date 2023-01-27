package com.vuzz.forgestory.common.utils.stories;

import java.io.File;
import java.io.FileReader;

import org.mozilla.javascript.ClassShutter;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptableObject;

import com.vuzz.forgestory.common.utils.js.JSBlocks;
import com.vuzz.forgestory.common.utils.js.JSPlayer;
import com.vuzz.forgestory.common.utils.js.JSScriptFunctions;
import com.vuzz.forgestory.common.utils.js.JSStory;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.ForgeConfig.Client;

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
        JSPlayer playerJS = new JSPlayer(player,storyJS);

        ScriptableObject.putConstProperty(scope, "story",storyJS);
        ScriptableObject.putConstProperty(scope, "player",playerJS);
        ScriptableObject.putConstProperty(scope, "blocks", new JSBlocks(player));
        ScriptableObject.putConstProperty(scope, "api", new JSScriptFunctions(storyJS, playerJS));
        ScriptableObject.putConstProperty(scope, "mc", Minecraft.getInstance());

        System.out.println("Running script: "+scriptId);
        try {
            ctx.evaluateReader(scope, new FileReader(scriptCode), null,1,null);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
