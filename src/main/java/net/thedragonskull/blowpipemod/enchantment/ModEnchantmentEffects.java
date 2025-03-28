package net.thedragonskull.blowpipemod.enchantment;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.enchantment.custom.BlowPowerEnchantmentEffect;
import net.thedragonskull.blowpipemod.enchantment.custom.FocusEnchantment;

import java.util.function.Supplier;

public class ModEnchantmentEffects {
    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_ENCHANTMENT_EFFECTS =
            DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, BlowPipeMod.MOD_ID);

    public static Supplier<MapCodec<? extends EnchantmentEntityEffect>> BLOW_POWER =
            ENTITY_ENCHANTMENT_EFFECTS.register("blow_power", () -> BlowPowerEnchantmentEffect.CODEC);

    public static Supplier<Enchantment> FOCUS =
            ENCHANTMENTS.register("focus",
                    () -> new FocusEnchantment(Enchantment.Rarity.RARE,
                            EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));

    public static void register(IEventBus eventBus) {
        ENTITY_ENCHANTMENT_EFFECTS.register(eventBus);
    }
}
