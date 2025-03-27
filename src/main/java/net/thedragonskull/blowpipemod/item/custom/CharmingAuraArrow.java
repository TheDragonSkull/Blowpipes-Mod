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
    public AbstractArrow createArrow(Level pLevel, ItemStack pStack, LivingEntity pShooter) {
        return new CharmingAuraArrowEntity(pLevel, pShooter);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.literal("§9Charming Aura (00:05)"));
    }
}
