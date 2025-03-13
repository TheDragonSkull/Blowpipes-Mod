package net.thedragonskull.blowpipemod;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.thedragonskull.blowpipemod.block.ModBlocks;
import net.thedragonskull.blowpipemod.config.BlowPipeModCommonConfigs;
import net.thedragonskull.blowpipemod.effect.ModEffects;
import net.thedragonskull.blowpipemod.enchantment.ModEnchantments;
import net.thedragonskull.blowpipemod.entity.ModEntities;
import net.thedragonskull.blowpipemod.item.ModItems;
import net.thedragonskull.blowpipemod.menu.ModMenuTypes;
import net.thedragonskull.blowpipemod.particle.ModParticles;
import net.thedragonskull.blowpipemod.potion.ModPotions;
import net.thedragonskull.blowpipemod.sound.ModSounds;
import net.thedragonskull.blowpipemod.trigger.ModTriggers;
import net.thedragonskull.blowpipemod.villager.ModVillagers;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BlowPipeMod.MOD_ID)
public class BlowPipeMod {
    public static final String MOD_ID = "blowpipemod";

    public BlowPipeMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModSounds.register(modEventBus);
        ModEntities.register(modEventBus);
        ModParticles.register(modEventBus);
        ModEnchantments.register(modEventBus);
        ModVillagers.register(modEventBus);

        ModEffects.register(modEventBus);
        ModPotions.register(modEventBus);

        ModTriggers.register();

        ModMenuTypes.MENU_TYPES.register(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BlowPipeModCommonConfigs.SPEC, "blowpipemod-common.toml");

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.getEntries().putAfter(Items.TRIDENT.getDefaultInstance(),
                    ModItems.BLOWPIPE.get().asItem().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(ModItems.BLOWPIPE.get().getDefaultInstance(),
                    ModItems.BAMBOO_BLOWPIPE.get().asItem().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(ModItems.BAMBOO_BLOWPIPE.get().getDefaultInstance(),
                    ModItems.DARK_BAMBOO_BLOWPIPE.get().asItem().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.accept(ModItems.DART_BASE);
            event.accept(ModItems.IRON_HEAD_DART);
            event.accept(ModItems.POISON_DART);
            event.accept(ModItems.POWDER_DART);
            event.accept(ModItems.LURE_DART);
            event.accept(ModItems.RAZOR_DART);

            event.accept(ModItems.DART_POUCH);

            for (DyeColor color : DyeColor.values()) {
                event.accept(ModItems.COLORED_DART_POUCHES.get(color));
            }
        }

        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.getEntries().putAfter(Blocks.SMITHING_TABLE.asItem().getDefaultInstance(),
                    ModBlocks.DART_TABLE.get().asItem().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}
