package net.thedragonskull.blowpipemod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class BlowPipeModCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> TOGGLE_DISPLAY_MESSAGES;

    static {
        BUILDER.push("Configs for TheDragon's Blowpipes Mod");

        TOGGLE_DISPLAY_MESSAGES = BUILDER.define("Toggle display messages", true);


        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
