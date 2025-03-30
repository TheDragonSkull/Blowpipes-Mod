package net.thedragonskull.blowpipemod.util;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.thedragonskull.blowpipemod.component.ModDataComponents;
import net.thedragonskull.blowpipemod.item.ModItems;

public class BlowpipeUtil {
    private static final float DART_TYPE_BASE = 1.0F;
    private static final float DART_TYPE_POISON = 2.0F;
    private static final float DART_TYPE_POWDER = 3.0F;
    private static final float DART_TYPE_LURE = 4.0F;
    private static final float DART_TYPE_IRON_HEAD = 5.0F;
    private static final float DART_TYPE_RAZOR = 6.0F;
    private static final float DART_TYPE_ANNIHILATION = 7.0F;
    private static final float DART_TYPE_OBLIVION = 8.0F;

    public static float getDartType(ItemStack stack) {
        if (stack.is(ModItems.DART_BASE.get())) {
            return DART_TYPE_BASE;
        } else if (stack.is(ModItems.POISON_DART.get())) {
            return DART_TYPE_POISON;
        } else if (stack.is(ModItems.POWDER_DART.get())) {
            return DART_TYPE_POWDER;
        } else if (stack.is(ModItems.LURE_DART.get())) {
            return DART_TYPE_LURE;
        } else if (stack.is(ModItems.IRON_HEAD_DART.get())) {
            return DART_TYPE_IRON_HEAD;
        } else if (stack.is(ModItems.RAZOR_DART.get())) {
            return DART_TYPE_RAZOR;
        } else if (stack.is(ModItems.ANNIHILATION_DART.get())) {
            return DART_TYPE_ANNIHILATION;
        } else if (stack.is(ModItems.OBLIVION_DART.get())) {
            return DART_TYPE_OBLIVION;
        }
        return 0.0F;
    }

    /**
     * Gets the currently loaded dart in the blowpipe
     */
    public static ItemStack getLoadedDart(ItemStack stack) {
        var dartDataComponent = stack.get(ModDataComponents.DART.get());
        if (dartDataComponent != null) {
            return dartDataComponent;
        }
        return ItemStack.EMPTY;
    }

    /**
     * Verifies if the blowpipe is loaded
     */
    public static boolean isLoaded(ItemStack stack) {
        var loadedDataComponent = stack.get(ModDataComponents.LOADED.get());
        return loadedDataComponent != null && loadedDataComponent;
    }

    /**
     * Sets the state of the blowpipe as loaded with a dart type
     */
    public static void setLoaded(ItemStack stack, boolean loaded, ItemStack dart) {
        stack.set(ModDataComponents.LOADED.get(), true);

        if (loaded && dart != null && !dart.isEmpty()) {
            stack.set(ModDataComponents.DART.get(), dart);

            float dartType = getDartType(dart);
            stack.set(ModDataComponents.DART_TYPE.get(), dartType);
        } else {
            stack.remove(ModDataComponents.DART.get());
            stack.remove(ModDataComponents.DART_TYPE.get());
        }
    }

    /**
     * Load the blowpipe with a dart
     */
    public static void loadBlowpipe(ItemStack blowpipe, ItemStack dart, Player player) {
        if (BlowpipeUtil.isLoaded(blowpipe)) {
            return;
        }

        BlowpipeUtil.setLoaded(blowpipe, true, dart);

        if (!player.isCreative())
            dart.shrink(1);

        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.CROSSBOW_LOADING_END, SoundSource.PLAYERS, 1.0F, 1.0F);
    }
}
