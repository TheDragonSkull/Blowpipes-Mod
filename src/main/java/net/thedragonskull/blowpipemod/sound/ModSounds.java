package net.thedragonskull.blowpipemod.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thedragonskull.blowpipemod.BlowPipeMod;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BlowPipeMod.MOD_ID);

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(BlowPipeMod.MOD_ID, name)));
    }

    public static final RegistryObject<SoundEvent> BLOWPIPE_EMPTY =
            registerSoundEvents("blowpipe_empty");

    public static final RegistryObject<SoundEvent> BLOWPIPE_SHOT =
            registerSoundEvents("blowpipe_shot");

    public static final RegistryObject<SoundEvent> DART_HIT =
            registerSoundEvents("dart_hit");

    public static final RegistryObject<SoundEvent> BOMB_FUSE =
            registerSoundEvents("bomb_fuse");

    public static final RegistryObject<SoundEvent> BLOWPIPE_RAT =
            registerSoundEvents("blowpipe_rat");

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
