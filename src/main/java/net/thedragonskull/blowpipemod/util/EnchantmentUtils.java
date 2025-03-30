package net.thedragonskull.blowpipemod.util;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.Optional;

public class EnchantmentUtils {

/*    public static int getEnchantmentLevel(RegistryAccess registryAccess, ItemStack stack, String enchantmentId) {
        if (stack.isEmpty()) return 0;

        // Obtiene los encantamientos usando DataComponents
        ItemEnchantments enchantments = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);

        // Obtiene el encantamiento desde el registro
        Optional<Holder.Reference<Enchantment>> enchantmentHolder = registryAccess.registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(ResourceLocation.fromNamespaceAndPath(enchantmentId));

        if (enchantmentHolder.isEmpty()) return 0; // Si el encantamiento no existe, retorna 0

        return enchantments.getLevel(enchantmentHolder.get()); // Obtiene el nivel del encantamiento
    }*/
}
