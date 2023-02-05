package com.vuzz.forgestory.common.utils.stories;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.mozilla.javascript.ClassShutter;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.LambdaFunction;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptableObject;

import com.vuzz.forgestory.common.entities.NPCEntity;
import com.vuzz.forgestory.common.utils.js.JSBlocks;
import com.vuzz.forgestory.common.utils.js.JSPlayer;
import com.vuzz.forgestory.common.utils.js.JSScene;
import com.vuzz.forgestory.common.utils.js.JSScriptFunctions;
import com.vuzz.forgestory.common.utils.js.JSStory;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.ForgeConfig.Client;

public class StoryScript {

    public String scriptId = "";
    public File scriptCode;
    public Story story;

    public Context ctx;
    public ScriptableObject scope;

    public int ticks;

    public StoryScript(String id,File code,Story storyC) {
        scriptId = id;
        scriptCode = code;
        story = storyC;
    }

    private ServerPlayerEntity player;
    private boolean tickBroken = false;
    private boolean defaultBroken = false;

    public void runScript(ServerPlayerEntity player,Scene scene) {
        if(defaultBroken) return;
        ctx = Context.enter();
        this.player = player;
        scope = ctx.initStandardObjects();
        ticks = 0;
        JSStory storyJS = new JSStory(story);
        JSPlayer playerJS = new JSPlayer(player,storyJS);

        ScriptableObject.putConstProperty(scope, "story",storyJS);
        ScriptableObject.putConstProperty(scope, "player",playerJS);
        ScriptableObject.putConstProperty(scope, "world",player.level);
        ScriptableObject.putConstProperty(scope, "blocks", new JSBlocks(player));
        ScriptableObject.putConstProperty(scope, "api", new JSScriptFunctions(storyJS, playerJS));
        ScriptableObject.putConstProperty(scope, "mc", Minecraft.getInstance());
        ScriptableObject.putConstProperty(scope, "server",playerJS.entity.level.getServer());
        ScriptableObject.putConstProperty(scope, "scene", new JSScene(scene));
        ScriptableObject.putConstProperty(scope, "ticker_ticks", GlobalTicker.ticks);
        ScriptableObject.putConstProperty(scope, "ticker_paused", GlobalTicker.paused);
        ScriptableObject.putConstProperty(scope, "face_def", 0);
        ScriptableObject.putConstProperty(scope, "face_happy", 1);
        ScriptableObject.putConstProperty(scope, "face_angry", 2);
        ScriptableObject.putConstProperty(scope, "face_sad", 3);
        ScriptableObject.putConstProperty(scope, "face_terrified", 4);
        ScriptableObject.putConstProperty(scope, "face_smug", 5);
        ScriptableObject.putConstProperty(scope, "face_eyeraise", 6);
        ScriptableObject.putConstProperty(scope, "face_gasp", 7);

        System.out.println("Running script: "+scriptId);
        try {
            BufferedReader reader = Files.newBufferedReader(scriptCode.toPath(), StandardCharsets.UTF_8);
            ctx.evaluateReader(scope, reader, "a",1,null);
        
        } catch(Exception e) {
            e.printStackTrace();
            defaultBroken = true;
            new JSScriptFunctions(storyJS,playerJS).error(e.getMessage()+" :other");
        }
    }

    public void tick() {
        Function tick  = (Function) scope.get("tick",scope);
        if(tick == null) return;
        if(tickBroken) return;
        try {
            tick.call(ctx, scope,scope, new Object[] {ticks});
            ticks++;
        } catch(Exception e) {
            e.printStackTrace();
            JSStory storyJS = new JSStory(story);
            JSPlayer playerJS = new JSPlayer(player,storyJS);
            new JSScriptFunctions(storyJS,playerJS).error(e.getMessage()+" :tick()");
            tickBroken = true;
        }
    }


}
