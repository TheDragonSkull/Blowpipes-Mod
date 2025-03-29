package net.thedragonskull.blowpipemod.event;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.capabilities.DartPouchCapabilityProvider;
import net.thedragonskull.blowpipemod.item.ModItems;
import net.thedragonskull.blowpipemod.network.PacketHandler;
import net.thedragonskull.blowpipemod.potion.ModPotions;
import net.thedragonskull.blowpipemod.util.BrewingRecipeUtil;


@EventBusSubscriber(modid = BlowPipeMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class CommonModEvents {

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            event.enqueueWork(PacketHandler::register);

            // Bleed potion
            BrewingRecipeRegistry.addRecipe(new BrewingRecipeUtil(
                    Potions.AWKWARD,
                    Items.PRISMARINE_SHARD,
                    ModPotions.BLEED_POTION.get()));

            // Charming aura potion
            BrewingRecipeRegistry.addRecipe(new BrewingRecipeUtil(
                    Potions.AWKWARD,
                    Items.AMETHYST_SHARD,
                    ModItems.CHARMING_AURA_POTION.get()));

            // Charming aura splash potion
            BrewingRecipeRegistry.addRecipe(new BrewingRecipeUtil(
                    ModItems.CHARMING_AURA_POTION.get(),
                    Items.GUNPOWDER,
                    ModItems.CHARMING_AURA_SPLASH_POTION.get()));

            // Charming aura lingering potion
            BrewingRecipeRegistry.addRecipe(new BrewingRecipeUtil(
                    ModItems.CHARMING_AURA_POTION.get(),
                    Items.DRAGON_BREATH,
                    ModItems.CHARMING_AURA_LINGERING_POTION.get()));
        });
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(
                Capabilities.ItemHandler.ITEM,
                (stack, ctx) -> new DartPouchCapabilityProvider(),
                ModItems.DART_POUCH.get()
        );
    }
}
