package com.vuzz.forgestory;

import net.minecraft.client.gui.widget.list.KeyBindingList;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vuzz.forgestory.client.renderers.NPCRenderer;
import com.vuzz.forgestory.common.entities.StoryEntities;
import com.vuzz.forgestory.common.items.StoryItems;
import com.vuzz.forgestory.common.networking.ClientProxy;
import com.vuzz.forgestory.common.networking.IProxy;
import com.vuzz.forgestory.common.networking.Networking;
import com.vuzz.forgestory.common.networking.ServerProxy;

@Mod(ForgeStory.MOD_ID)
public class ForgeStory {
    public static final String MOD_ID = "forgestory";
    public static final IProxy PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);;

    public static final List<String> whitelist = Arrays.asList(
        "_BENDY659_",
        "Dev",
        "dravesantigrifer",
        "Me4o0n",
        "_Merfu_"
    );
    public static final boolean hasWhitelist = true;

    public static final ItemGroup MOD_TAB = new ItemGroup("forgestory") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(StoryItems.NPC_CREATOR.get());
        }
    };

    public ForgeStory() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        register(eventBus);
        Networking.register();
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
        RenderingRegistry.registerEntityRenderingHandler(StoryEntities.NPC.get(), NPCRenderer::new);
        event.enqueueWork(() -> {
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {

    }


}