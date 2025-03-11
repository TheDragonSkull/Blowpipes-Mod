package net.thedragonskull.blowpipemod.event;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.network.PacketHandler;
import net.thedragonskull.blowpipemod.potion.ModPotions;
import net.thedragonskull.blowpipemod.util.BrewingRecipeUtil;

@Mod.EventBusSubscriber(modid = BlowPipeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            event.enqueueWork(PacketHandler::register);

            BrewingRecipeRegistry.addRecipe(new BrewingRecipeUtil(
                    Potions.AWKWARD,
                    Items.PRISMARINE_SHARD,
                    ModPotions.BLEED_POTION.get()));
        });
    }

}
