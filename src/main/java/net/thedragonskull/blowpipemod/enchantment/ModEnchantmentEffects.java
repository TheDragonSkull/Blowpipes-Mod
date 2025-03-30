package net.thedragonskull.blowpipemod.enchantment;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thedragonskull.blowpipemod.BlowPipeMod;

public class ModEnchantmentEffects {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(Registries.ENCHANTMENT, BlowPipeMod.MOD_ID);


/*    public static final Holder<Enchantment> BLOW_POWER =
            ENCHANTMENTS.register("blow_power", () -> new BlowPowerEnchantmentEffect());

    public static Supplier<MapCodec<? extends EnchantmentEntityEffect>> BLOW_POWER =
            ENTITY_ENCHANTMENT_EFFECTS.register("blow_power", () -> BlowPowerEnchantmentEffect.CODEC);

    public static Supplier<MapCodec<? extends EnchantmentEntityEffect>> FOCUS =
            ENTITY_ENCHANTMENT_EFFECTS.register("focus", () -> FocusEnchantmentEffect.CODEC);*/

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
