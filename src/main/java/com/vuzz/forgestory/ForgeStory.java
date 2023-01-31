package com.vuzz.forgestory;

import net.minecraft.client.gui.widget.list.KeyBindingList;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

import com.vuzz.forgestory.common.entities.StoryEntities;
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

        GeckoLib.initialize();

        eventBus.addListener(this::doClientStuff);
    }

    public void register(IEventBus eventBus) {
        StoryItems.register(eventBus);
        StoryEntities.register(eventBus);
    }

    public static KeyBinding keyStory = new KeyBinding("Play Action", InputMappings.Type.KEYSYM, 72, MOD_ID);

    private void doClientStuff(final FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(keyStory);
        event.enqueueWork(() -> {
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {

    }


}