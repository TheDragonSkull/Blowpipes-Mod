package net.thedragonskull.blowpipemod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thedragonskull.blowpipemod.BlowPipeMod;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, BlowPipeMod.MOD_ID);

    public static final RegistryObject<MobEffect> BLEED_EFFECT = MOB_EFFECTS.register("thedragon_bleed",
            () -> new BleedEffect(MobEffectCategory.HARMFUL, 0x911d1d));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
