package net.thedragonskull.blowpipemod.mixin;

import com.google.common.collect.Sets;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.thedragonskull.blowpipemod.villager.ModVillagers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(Villager.class)
public abstract class VillagerOfferCapMixin extends AbstractVillager{

    public VillagerOfferCapMixin(EntityType<? extends AbstractVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Shadow public abstract VillagerData getVillagerData();

    @Inject(method = "updateTrades", at = @At("HEAD"), cancellable = true)
    private void onUpdateTrades(CallbackInfo ci) {
        VillagerData villagerData = this.getVillagerData();

        if (villagerData.getProfession() == ModVillagers.HUNTER.get()) {
            MerchantOffers merchantOffers = this.getOffers();
            VillagerTrades.ItemListing[] newTrades = VillagerTrades.TRADES.get(villagerData.getProfession()).get(villagerData.getLevel());

            this.addOffersFromItemListings(merchantOffers, newTrades, 5);

            ci.cancel();
        }
    }

}
