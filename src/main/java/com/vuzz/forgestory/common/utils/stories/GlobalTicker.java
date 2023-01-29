package com.vuzz.forgestory.common.utils.stories;

import java.util.ArrayList;
import java.util.List;

public class GlobalTicker {
    
    public static boolean paused = false;
    public static int ticks = 0;

    public static List<Scene> loadedScenes = new ArrayList<>();

    public static void tick() {
        if(paused) return;
        for (Scene scene : loadedScenes) {
            if(scene != null) scene.tick();
        }
        ticks++;
    }

}
