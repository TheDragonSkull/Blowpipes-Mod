package net.thedragonskull.blowpipemod.mixin;

import com.google.common.collect.Sets;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.thedragonskull.blowpipemod.villager.ModVillagers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Set;

@Mixin(AbstractVillager.class)
public abstract class VillagerOfferCapMixin extends Entity {

    public VillagerOfferCapMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    /**
     * @author
     * @reason
     */
    @Overwrite
    protected void addOffersFromItemListings(MerchantOffers pGivenMerchantOffers, VillagerTrades.ItemListing[] pNewTrades, int pMaxNumbers) {
        AbstractVillager villager = (AbstractVillager) (Object) this;
        Villager actualVillager = (Villager) villager;

        if (actualVillager.getVillagerData().getProfession() == ModVillagers.HUNTER.get()) {

            for (VillagerTrades.ItemListing itemListing : pNewTrades) {
                MerchantOffer merchantOffer = itemListing.getOffer(villager, this.random);
                if (merchantOffer != null) {
                    pGivenMerchantOffers.add(merchantOffer);
                }
            }

        } else {
            Set<Integer> set = Sets.newHashSet();
            if (pNewTrades.length > pMaxNumbers) {
                while (set.size() < pMaxNumbers) {
                    set.add(this.random.nextInt(pNewTrades.length));
                }
            } else {
                for (int i = 0; i < pNewTrades.length; ++i) {
                    set.add(i);
                }
            }

            for (Integer integer : set) {
                VillagerTrades.ItemListing villagertrades$itemlisting = pNewTrades[integer];
                MerchantOffer merchantoffer = villagertrades$itemlisting.getOffer(this, this.random);
                if (merchantoffer != null) {
                    pGivenMerchantOffers.add(merchantoffer);
                }
            }
        }
    }
}
