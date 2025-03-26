package net.thedragonskull.blowpipemod.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.common.brewing.IBrewingRecipe;

import javax.annotation.Nullable;

// BetterBrewingRecipe Class by CAS-ual-TY from https://github.com/CAS-ual-TY/Extra-Potions (GPL-3.0 License)
// https://github.com/CAS-ual-TY/Extra-Potions/blob/main/LICENSE
public class BrewingRecipeUtil implements IBrewingRecipe {
    private final Potion inputPotion;
    private final @Nullable Item inputItem;
    private final Item ingredient;
    private final Potion outputPotion;
    private final @Nullable Item outputItem;


    // Normal potions
    public BrewingRecipeUtil(Potion input, Item ingredient, Potion outputPotion) {
        this.inputPotion = input;
        this.inputItem = null;
        this.ingredient = ingredient;
        this.outputPotion = outputPotion;
        this.outputItem = null;
    }

    // Custom normal potions
    public BrewingRecipeUtil(Potion input, Item ingredient, Item outputItem) {
        this.inputPotion = input;
        this.inputItem = null;
        this.ingredient = ingredient;
        this.outputPotion = null;
        this.outputItem = outputItem;
    }

    public BrewingRecipeUtil(Item input, Item ingredient, Item outputItem) {
        this.inputPotion = null;
        this.inputItem = input;
        this.ingredient = ingredient;
        this.outputPotion = null;
        this.outputItem = outputItem;
    }

    @Override
    public boolean isInput(ItemStack input) {
        if (this.inputPotion != null) {
            return PotionUtils.getPotion(input) == this.inputPotion;
        } else {
            return input.getItem() == this.inputItem;
        }
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return ingredient.getItem() == this.ingredient;
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        if (!this.isInput(input) || !this.isIngredient(ingredient)) {
            return ItemStack.EMPTY;
        }

        if (this.outputPotion != null) {
            ItemStack itemStack = new ItemStack(Items.POTION);
            itemStack.setTag(new CompoundTag());
            PotionUtils.setPotion(itemStack, this.outputPotion);
            return itemStack;
        }

        if (this.outputItem != null) {
            return new ItemStack(this.outputItem);
        }

        return ItemStack.EMPTY;
    }
}