package net.thedragonskull.blowpipemod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

import static net.thedragonskull.blowpipemod.util.RangeGogglesUtil.hasGogglesEquipped;

public class RangeGoggles extends Item implements ICurioItem, Equipable {

    public RangeGoggles(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }

    @Override
    public List<Component> getSlotsTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
        tooltips.clear();
        return tooltips;
    }

    // NeoForge
    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot armorType, LivingEntity entity) {
        if (entity instanceof Player player && armorType == EquipmentSlot.HEAD) {
            return !hasGogglesEquipped(player);
        }
        return false;    }

    // Curio
    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            return !hasGogglesEquipped(player);
        }
        return false;
    }

    // Curio
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            return !hasGogglesEquipped(player);
        }
        return false;
    }
}
