package net.thedragonskull.blowpipemod.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.thedragonskull.blowpipemod.BlowPipeMod;

public class Keybindings {
    public static final Keybindings INSTANCE = new Keybindings();

    private Keybindings() {
    }

    private static final String BLOWPIPE_MOD = "key.categories." + BlowPipeMod.MOD_ID;

    public final KeyMapping reloadKey = new KeyMapping(
            "key." + BlowPipeMod.MOD_ID + ".reload_key",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_R,-1),
            BLOWPIPE_MOD
    );

    public final KeyMapping openDartPouchKey = new KeyMapping(
            "key." + BlowPipeMod.MOD_ID + ".open_dart_pouch_key",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_O,-1),
            BLOWPIPE_MOD
    );

    public final KeyMapping nextDart = new KeyMapping(
            "key." + BlowPipeMod.MOD_ID + ".next_dart_key",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_RIGHT,-1),
            BLOWPIPE_MOD
    );

    public final KeyMapping prevDart = new KeyMapping(
            "key." + BlowPipeMod.MOD_ID + ".prev_dart_key",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_LEFT,-1),
            BLOWPIPE_MOD
    );
}
