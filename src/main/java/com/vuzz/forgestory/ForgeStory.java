package com.vuzz.forgestory;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import com.vuzz.forgestory.common.items.StoryItems;

@Mod(ForgeStory.MOD_ID)
public class ForgeStory {
    public static final String MOD_ID = "forgestory";

    public static final ItemGroup MOD_TAB = new ItemGroup("forgestory") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(StoryItems.NPC_CREATOR.get());
        }
    };

    public ForgeStory() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        register(eventBus);

        eventBus.addListener(this::doClientStuff);
    }

    public void register(IEventBus eventBus) {
        StoryItems.register(eventBus);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
        
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {

    }


}