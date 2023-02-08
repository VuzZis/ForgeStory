package com.vuzz.forgestory.common.utils.js;

import com.vuzz.forgestory.common.utils.VarsUtils;
import com.vuzz.forgestory.common.utils.stories.Scene;
import com.vuzz.forgestory.common.utils.stories.Story;
import com.vuzz.forgestory.common.utils.stories.StoryScript;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.registries.ForgeRegistries;

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
            try {
                script.runScript(player.entity,scene);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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

    public void blackScreen(int isBlack) { VarsUtils.blackScreen = isBlack; }
    public void hideGui(boolean isHidden) { VarsUtils.hideGui = isHidden; }

    public void teleport(double[] coords) {
        command("tp "+player.entity.getDisplayName().getString()+" "+coords[0]+" "+coords[1]+" "+coords[2]);
    }

    public void command(String command) {
        MinecraftServer server = player.entity.level.getServer();
        server.getCommands().performCommand(server.createCommandSourceStack(), command);
    }

    public Item getItem(String itemId) {
        String id = itemId;
        String modId = "minecraft";
        String name = "";
        if(id.indexOf(":") != -1) {
            modId = id.substring(0,id.indexOf(":"));
        }
        name = id.substring(id.indexOf(":")+1);
        Item itemReg = ForgeRegistries.ITEMS.getValue(new ResourceLocation(modId, name));
        return itemReg;
    }

    public Block getBlock(String blockId) {
        String id = blockId;
        String modId = "minecraft";
        String name = "";
        if(id.indexOf(":") != -1) {
            modId = id.substring(0,id.indexOf(":"));
        }
        name = id.substring(id.indexOf(":")+1);
        Block itemReg = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(modId, name));
        return itemReg;
    }

    public ItemStack createStack(Item item, int count) {
        ItemStack stack = new ItemStack(item);
        stack.setCount(count);
        return stack;
    }

    public BlockState createBlockState(Block block) {
        BlockState blockState = block.defaultBlockState();
        return blockState;
    }

    public ItemStack createStack(String itemId, int count) {
        return createStack(getItem(itemId), count);
    }

    public BlockState createBlockState(String blockId) {
        return createBlockState(getBlock(blockId));
    }

    public void placeBlock(BlockState blockState, BlockPos pos) {
        player.entity.level.setBlock(pos,blockState,Constants.BlockFlags.DEFAULT);
    }

    public void placeBlock(Block block, BlockPos pos) {
        placeBlock(createBlockState(block),pos);
    }

    public void placeBlock(String blockId, BlockPos pos) {
        placeBlock(createBlockState(blockId),pos);
    }

    public void placeBlock(BlockState blockState, double[] pos) {
        placeBlock(blockState,createBlockPos(pos));
    }

    public void placeBlock(Block block, double[] pos) {
        placeBlock(createBlockState(block),pos);
    }

    public void placeBlock(String blockId, double[] pos) {
        placeBlock(createBlockState(blockId),pos);
    }

    public BlockPos createBlockPos(double[] pos) {
        return new BlockPos(pos[0],pos[1],pos[2]);
    }

}
