package net.thedragonskull.blowpipemod.screen.custom;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.thedragonskull.blowpipemod.component.ModDataComponents;

public class TestInventory extends SimpleContainer {
    private final ItemStack testStack;

    public TestInventory(ItemStack testStack) {
        super(6);
        this.testStack = testStack;
    }

    public ItemStack getStack() {
        return testStack;
    }


}
