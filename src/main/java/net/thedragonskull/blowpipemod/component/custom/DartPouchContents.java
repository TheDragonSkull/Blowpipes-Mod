package net.thedragonskull.blowpipemod.component.custom;


import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.thedragonskull.blowpipemod.component.ModDataComponents;
import org.apache.commons.lang3.math.Fraction;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class DartPouchContents {
/*
    public static final DartPouchContents EMPTY = new DartPouchContents(List.of());
    public static final Codec<DartPouchContents> CODEC = ItemStack.CODEC.listOf().xmap(DartPouchContents::new, (p_331551_) -> p_331551_.items);
    public static final StreamCodec<RegistryFriendlyByteBuf, DartPouchContents> STREAM_CODEC = ItemStack.STREAM_CODEC
            .apply(ByteBufCodecs.list()).map(DartPouchContents::new, (p_331649_) -> p_331649_.items);
    public final List<ItemStack> items;
    final Fraction weight;
    private static final Fraction BUNDLE_IN_BUNDLE_WEIGHT = Fraction.getFraction(1, 16);
    private static final int NO_STACK_INDEX = -1;

    DartPouchContents(List<ItemStack> items, Fraction weight) {
        this.items = items;
        this.weight = weight;
    }

    public DartPouchContents(List<ItemStack> items) {
        this(items, computeContentWeight(items));
    }

    private static Fraction computeContentWeight(List<ItemStack> content) {
        Fraction fraction = Fraction.ZERO;

        ItemStack itemstack;
        for(Iterator var2 = content.iterator(); var2.hasNext(); fraction = fraction.add(getWeight(itemstack).multiplyBy(Fraction.getFraction(itemstack.getCount(), 1)))) {
            itemstack = (ItemStack)var2.next();
        }

        return fraction;
    }

    static Fraction getWeight(ItemStack stack) {
        DartPouchContents dartPouchContents = stack.get(ModDataComponents.DART_POUCH_CONTENTS);
        if (dartPouchContents != null) {
            return BUNDLE_IN_BUNDLE_WEIGHT.add(dartPouchContents.weight());
        } else {
            List list = stack.getOrDefault(DataComponents.BEES, List.of());
            return !list.isEmpty() ? Fraction.ONE : Fraction.getFraction(1, stack.getMaxStackSize());
        }
    }

    public Iterable<ItemStack> items() {
        return this.items;
    }

    public Iterable<ItemStack> itemsCopy() {
        return Lists.transform(this.items, ItemStack::copy);
    }

    public int size() {
        return this.items.size();
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public Fraction weight() {
        return this.weight;
    }

    @Override
    public String toString() {
        return "DartPouchContentsContents" + this.items;
    }


    public static class Mutable {
        private final List<ItemStack> items;
        private Fraction weight;

        public Mutable(DartPouchContents contents) {
            this.items = new ArrayList(contents.items);
        }

        public DartPouchContents.Mutable clearItems() {
            this.items.clear();
            this.weight = Fraction.ZERO;
            return this;
        }

        private int findStackIndex(ItemStack stack) {
            if (!stack.isStackable()) {
                return -1;
            } else {
                for(int i = 0; i < this.items.size(); ++i) {
                    if (ItemStack.isSameItemSameComponents(this.items.get(i), stack)) {
                        return i;
                    }
                }

                return -1;
            }
        }

        private int getMaxAmountToAdd(ItemStack stack) {
            Fraction fraction = Fraction.ONE.subtract(this.weight);
            return Math.max(fraction.divideBy(DartPouchContents.getWeight(stack)).intValue(), 0);
        }

        public int tryInsert(ItemStack stack) {
            if (!stack.isEmpty() && stack.getItem().canFitInsideContainerItems()) {
                int i = Math.min(stack.getCount(), this.getMaxAmountToAdd(stack));
                if (i == 0) {
                    return 0;
                } else {
                    this.weight = this.weight.add(DartPouchContents.getWeight(stack).multiplyBy(Fraction.getFraction(i, 1)));
                    int j = this.findStackIndex(stack);
                    if (j != -1) {
                        ItemStack itemstack = this.items.remove(j);
                        ItemStack itemstack1 = itemstack.copyWithCount(itemstack.getCount() + i);
                        stack.shrink(i);
                        this.items.add(0, itemstack1);
                    } else {
                        this.items.add(0, stack.split(i));
                    }

                    return i;
                }
            } else {
                return 0;
            }
        }

        public int tryTransfer(Slot slot, Player player) {
            ItemStack itemstack = slot.getItem();
            int i = this.getMaxAmountToAdd(itemstack);
            return this.tryInsert(slot.safeTake(itemstack.getCount(), i, player));
        }

        @Nullable
        public ItemStack removeOne() {
            if (this.items.isEmpty()) {
                return null;
            } else {
                ItemStack itemstack = ((ItemStack)this.items.remove(0)).copy();
                this.weight = this.weight.subtract(DartPouchContents.getWeight(itemstack).multiplyBy(Fraction.getFraction(itemstack.getCount(), 1)));
                return itemstack;
            }
        }

        public Fraction weight() {
            return this.weight;
        }

        public DartPouchContents toImmutable() {
            return new DartPouchContents(List.copyOf(this.items), this.weight);
        }
    }
*/

}
