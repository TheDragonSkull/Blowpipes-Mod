package net.thedragonskull.blowpipemod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.thedragonskull.blowpipemod.entity.custom.CharmingAuraArrowEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CharmingAuraArrow extends ArrowItem {

    public CharmingAuraArrow(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter, @Nullable ItemStack weapon) {
        return new CharmingAuraArrowEntity(level, shooter);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("§9Charming Aura (00:02)"));
    }
}
