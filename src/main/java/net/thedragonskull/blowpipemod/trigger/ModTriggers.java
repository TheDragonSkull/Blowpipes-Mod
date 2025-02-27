package net.thedragonskull.blowpipemod.trigger;

import net.minecraft.advancements.CriteriaTriggers;

public class ModTriggers {
    public static final BlowpipeHamelinTrigger BLOWPIPE_HAMELIN = new BlowpipeHamelinTrigger();

    public static void register() {
        CriteriaTriggers.register(BLOWPIPE_HAMELIN);
    }
}
