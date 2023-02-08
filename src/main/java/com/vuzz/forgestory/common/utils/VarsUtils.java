package com.vuzz.forgestory.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.vuzz.forgestory.ForgeStory;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.text.TranslationTextComponent;

public class VarsUtils {
    public static boolean IS_EXPERIMENTAL = true;
    public static String MOD_ID = ForgeStory.MOD_ID;
    public static ItemGroup MOD_TAB = ForgeStory.MOD_TAB;

    public static List<String> logs = new ArrayList<>();

    public static int blackScreen = 0;
    public static boolean hideGui = false;

    public static Entity lastKilledEntity = null;

    public static String trans(String key) {
        return new TranslationTextComponent(key).getString();
    }

}
