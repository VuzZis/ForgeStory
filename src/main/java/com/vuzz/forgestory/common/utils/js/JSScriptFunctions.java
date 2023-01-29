package com.vuzz.forgestory.common.utils.js;

import com.vuzz.forgestory.common.utils.VarsUtils;
import com.vuzz.forgestory.common.utils.stories.Scene;
import com.vuzz.forgestory.common.utils.stories.Story;
import com.vuzz.forgestory.common.utils.stories.StoryScript;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;

public class JSScriptFunctions {
    
    public final JSStory story;
    public final JSPlayer player;

    public final String version = "0.0";

    public JSScriptFunctions(JSStory story,JSPlayer player) {
        this.story = story;
        this.player = player;
    }

    public void runScript(String id, Scene scene) {
        StoryScript script = story.story.getScript(id);
        if(script != null) {
            script.runScript(player.entity,scene);
            return;
        }
    }

    public void print(Object text) {
        VarsUtils.logs.add(String.valueOf(text));
        System.out.println(text);
    }

    public void error(Object text) {
        VarsUtils.logs.add(String.valueOf(text));
        System.out.println(text);
        player.entity.sendMessage(new StringTextComponent(
            VarsUtils.trans("error")+String.valueOf(text)
        ), Util.NIL_UUID);
    }

    public void teleport(double[] coords) {
        command("tp "+player.entity.getDisplayName().getString()+" "+coords[0]+" "+coords[1]+" "+coords[2]);
    }

    public void command(String command) {
        MinecraftServer server = player.entity.level.getServer();
        server.getCommands().performCommand(server.createCommandSourceStack(), command);
    }

}
