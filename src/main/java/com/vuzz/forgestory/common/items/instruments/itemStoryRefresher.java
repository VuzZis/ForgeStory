package com.vuzz.forgestory.common.items.instruments;

import com.vuzz.forgestory.common.items.CustomItem;
import com.vuzz.forgestory.common.utils.VarsUtils;
import com.vuzz.forgestory.common.utils.stories.StoryParser;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResultType;

public class itemStoryRefresher extends CustomItem {

    public itemStoryRefresher() {
        super(
                new Item.Properties()
                        .fireResistant()
                        .rarity(Rarity.EPIC)
                        .stacksTo(1)
                        .tab(VarsUtils.MOD_TAB)
        );
    }

    @Override
    protected ActionResultType onItemUseFirstServer(ItemStack stack, ItemUseContext context) {
        StoryParser.loadStories((ServerPlayerEntity) context.getPlayer());
        return ActionResultType.PASS;
    }

    @Override
    protected ActionResultType onItemUseFirstClient(ItemStack stack, ItemUseContext context) {
        return ActionResultType.PASS;
    }
    
}
