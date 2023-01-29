package com.vuzz.forgestory.common.items;

import com.vuzz.forgestory.common.items.instruments.*;
import com.vuzz.forgestory.common.utils.VarsUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class StoryItems {

    private static String MOD_ID = VarsUtils.MOD_ID;
    private static ItemGroup MOD_TAB = VarsUtils.MOD_TAB;
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,MOD_ID);

    public static RegistryObject<Item> NPC_CREATOR = localSetup("npc_creator", itemNpcCreator::new,true);
    public static RegistryObject<Item> STORY_REFRESHER = localSetup("story_refresher", itemStoryRefresher::new,true);

    private static RegistryObject<Item> localSetup(String id, Supplier<? extends Item> supp, boolean isExperimental) {
        if(!VarsUtils.IS_EXPERIMENTAL && isExperimental)
            return null;
        else
            return ITEMS.register(id,supp);
    }

    private static RegistryObject<Item> localSetup(String id, Supplier<? extends Item> supp) { return localSetup(id,supp,false); }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
