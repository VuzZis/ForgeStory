package com.vuzz.forgestory.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;

public abstract class CustomItem extends Item {
    public CustomItem(Properties props) {
        super(props);
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        World level = context.getLevel();
        if(level.isClientSide) return onItemUseFirstClient(stack,context);
        else return onItemUseFirstServer(stack,context);
    }

    protected abstract ActionResultType onItemUseFirstServer(ItemStack stack, ItemUseContext context);
    protected abstract ActionResultType onItemUseFirstClient(ItemStack stack, ItemUseContext context);
}
