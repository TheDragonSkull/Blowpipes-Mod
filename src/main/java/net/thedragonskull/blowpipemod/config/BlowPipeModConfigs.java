package net.thedragonskull.blowpipemod.config;


import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class BlowPipeModConfigs {
    public static final BlowPipeModConfigs CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    public final ModConfigSpec.BooleanValue toggle_display_messages;

    private BlowPipeModConfigs(ModConfigSpec.Builder builder) {
        toggle_display_messages = builder
                .comment("Toggle display messages for Blowpipe Mod")
                .define("toggle_display_messages", true);
    }

    static {
        Pair<BlowPipeModConfigs, ModConfigSpec> pair =
                new ModConfigSpec.Builder().configure(BlowPipeModConfigs::new);

        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
}
