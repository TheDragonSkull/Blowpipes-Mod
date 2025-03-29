package net.thedragonskull.blowpipemod.potion;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.effect.ModEffects;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(BuiltInRegistries.POTION, BlowPipeMod.MOD_ID);

    public static final Holder<Potion> BLEED_POTION = POTIONS.register("thedragon_bleed_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.BLEED_EFFECT, 200, 0)));

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
