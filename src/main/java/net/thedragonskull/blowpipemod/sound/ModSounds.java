package net.thedragonskull.blowpipemod.sound;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thedragonskull.blowpipemod.BlowPipeMod;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, BlowPipeMod.MOD_ID);


    public static final Supplier<SoundEvent> BLOWPIPE_EMPTY =
            registerSoundEvents("blowpipe_empty");

    public static final Supplier<SoundEvent> BLOWPIPE_SHOT =
            registerSoundEvents("blowpipe_shot");

    public static final Supplier<SoundEvent> DART_HIT =
            registerSoundEvents("dart_hit");

    public static final Supplier<SoundEvent> BLOWPIPE_RAT =
            registerSoundEvents("blowpipe_rat");

    public static final Supplier<SoundEvent> NIGHT_VISION =
            registerSoundEvents("night_vision");

    public static final Supplier<SoundEvent> SWITCH =
            registerSoundEvents("switch");

    public static final Supplier<SoundEvent> BOOM =
            registerSoundEvents("boom");

    private static Supplier<SoundEvent> registerSoundEvents(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(BlowPipeMod.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
