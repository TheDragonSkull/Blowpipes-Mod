package net.thedragonskull.blowpipemod.util;

import net.minecraft.world.item.DyeColor;

import java.util.HashMap;
import java.util.Map;

public class DartPouchColors {
    public static final Map<DyeColor, Integer> CUSTOM_POUCH_COLORS = new HashMap<>();

    static {
        CUSTOM_POUCH_COLORS.put(DyeColor.WHITE, 0xFFFFFF);
        CUSTOM_POUCH_COLORS.put(DyeColor.ORANGE, 0xff9b00);
        CUSTOM_POUCH_COLORS.put(DyeColor.MAGENTA, 0xE766CC);
        CUSTOM_POUCH_COLORS.put(DyeColor.LIGHT_BLUE, 0x66C0FF);
        CUSTOM_POUCH_COLORS.put(DyeColor.YELLOW, 0xFFF000);
        CUSTOM_POUCH_COLORS.put(DyeColor.LIME, 0x56ff00);
        CUSTOM_POUCH_COLORS.put(DyeColor.PINK, 0xFFB3E6);
        CUSTOM_POUCH_COLORS.put(DyeColor.GRAY, 0x787878);
        CUSTOM_POUCH_COLORS.put(DyeColor.LIGHT_GRAY, 0xD0D0D0);
        CUSTOM_POUCH_COLORS.put(DyeColor.CYAN, 0x4CFFFF);
        CUSTOM_POUCH_COLORS.put(DyeColor.PURPLE, 0xA455E6);
        CUSTOM_POUCH_COLORS.put(DyeColor.BLUE, 0x6688FF);
        CUSTOM_POUCH_COLORS.put(DyeColor.BROWN, 0xA4703A);
        CUSTOM_POUCH_COLORS.put(DyeColor.GREEN, 0x77DD66);
        CUSTOM_POUCH_COLORS.put(DyeColor.RED, 0xFF5544);
        CUSTOM_POUCH_COLORS.put(DyeColor.BLACK, 0x3A3A3A);

    }

    public static int getCustomColor(DyeColor color) {
        return CUSTOM_POUCH_COLORS.getOrDefault(color, 0xffb742);
    }
}
