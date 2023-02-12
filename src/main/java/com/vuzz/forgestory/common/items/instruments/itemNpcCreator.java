package com.vuzz.forgestory.common.items.instruments;

import com.vuzz.forgestory.common.items.CustomItem;
import com.vuzz.forgestory.common.utils.VarsUtils;
import com.vuzz.forgestory.common.utils.stories.Scene;
import com.vuzz.forgestory.common.utils.stories.Story;
import com.vuzz.forgestory.common.utils.stories.StoryParser;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResultType;

public class itemNpcCreator extends CustomItem {

    public itemNpcCreator() {
        super(
                new Item.Properties()
                        .fireResistant()
                        .rarity(Rarity.EPIC)
                        .stacksTo(1)
                        .tab(VarsUtils.MOD_TAB)
        );
    }

    public ActionResultType onItemUseFirstServer(ItemStack stack, ItemUseContext context) {
        Story story = StoryParser.getStory("template");
        if(story != null) {
            Scene scene = story.getScene("starter");
            if(scene != null) {
                scene.invalidateScripts();
                scene.start((ServerPlayerEntity) context.getPlayer());
            }
        }
        return ActionResultType.PASS;
    }

    public ActionResultType onItemUseFirstClient(ItemStack stack, ItemUseContext context) {
        return ActionResultType.PASS;
    }
}
