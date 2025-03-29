package net.thedragonskull.blowpipemod.effect;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thedragonskull.blowpipemod.BlowPipeMod;

import java.util.function.Supplier;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, BlowPipeMod.MOD_ID);

    public static final Holder<MobEffect> BLEED_EFFECT = MOB_EFFECTS.register("thedragon_bleed",
            () -> new BleedEffect(MobEffectCategory.HARMFUL, 0x911d1d));

    public static final Holder<MobEffect> CHARMING_AURA_EFFECT = MOB_EFFECTS.register("thedragon_charming_aura",
            () -> new CharmingAuraEffect(MobEffectCategory.NEUTRAL, 0xFFFFFF));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
