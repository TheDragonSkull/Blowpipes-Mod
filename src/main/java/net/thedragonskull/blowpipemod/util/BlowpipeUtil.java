package net.thedragonskull.blowpipemod.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.thedragonskull.blowpipemod.item.ModItems;

public class BlowpipeUtil {
    private static final float DART_TYPE_BASE = 1.0F;
    private static final float DART_TYPE_POISON = 2.0F;
    private static final float DART_TYPE_POWDER = 3.0F;
    private static final float DART_TYPE_LURE = 4.0F;
    private static final float DART_TYPE_IRON_HEAD = 5.0F;

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
        }
        return 0.0F;
    }

    /**
     * Verifies if the blowpipe is loaded
     */
    public static boolean isLoaded(ItemStack stack) {
        CompoundTag compoundtag = stack.getTag();
        return compoundtag != null && compoundtag.getBoolean("loaded");
    }

    /**
     * Sets the state of the blowpipe as loaded with a dart type
     */
    public static void setLoaded(ItemStack stack, boolean loaded, ItemStack dart) {
        CompoundTag compoundtag = stack.getOrCreateTag();
        compoundtag.putBoolean("loaded", loaded);

        if (loaded && dart != null && !dart.isEmpty()) {
            CompoundTag dartTag = new CompoundTag();
            dart.save(dartTag);
            compoundtag.put("Dart", dartTag);

            float dartType = getDartType(dart);
            compoundtag.putFloat("dart_type", dartType);
        } else {
            compoundtag.remove("Dart");
            compoundtag.remove("dart_type");
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
        dart.shrink(1);

        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.CROSSBOW_LOADING_END, SoundSource.PLAYERS, 1.0F, 1.0F);
    }
}
