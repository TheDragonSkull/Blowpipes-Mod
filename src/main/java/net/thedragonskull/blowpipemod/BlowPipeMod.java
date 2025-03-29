package net.thedragonskull.blowpipemod;

import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.thedragonskull.blowpipemod.block.ModBlocks;
import net.thedragonskull.blowpipemod.block.entity.ModBlockEntities;
import net.thedragonskull.blowpipemod.config.BlowPipeModCommonConfigs;
import net.thedragonskull.blowpipemod.effect.ModEffects;
import net.thedragonskull.blowpipemod.enchantment.ModEnchantmentEffects;
import net.thedragonskull.blowpipemod.entity.ModEntities;
import net.thedragonskull.blowpipemod.item.ModCreativeModeTabs;
import net.thedragonskull.blowpipemod.item.ModItems;
import net.thedragonskull.blowpipemod.menu.ModMenuTypes;
import net.thedragonskull.blowpipemod.particle.ModParticles;
import net.thedragonskull.blowpipemod.potion.ModPotions;
import net.thedragonskull.blowpipemod.sound.ModSounds;
import net.thedragonskull.blowpipemod.trigger.ModTriggers;
import net.thedragonskull.blowpipemod.villager.ModVillagers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Mod(BlowPipeMod.MOD_ID)
public class BlowPipeMod {
    public static final String MOD_ID = "blowpipemod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public BlowPipeMod(IEventBus modEventBus, ModContainer modContainer) {

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModSounds.register(modEventBus);
        ModEntities.register(modEventBus);
        ModParticles.register(modEventBus);
        ModEnchantmentEffects.register(modEventBus);
        ModVillagers.register(modEventBus);

        ModEffects.register(modEventBus);
        ModPotions.register(modEventBus);

        ModTriggers.register();
        ModCreativeModeTabs.register(modEventBus);

        ModMenuTypes.MENU_TYPES.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, BlowPipeModCommonConfigs.SPEC, "blowpipemod-common.toml");

        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {

            event.accept(ModItems.BLOWPIPE);
            event.accept(ModItems.BAMBOO_BLOWPIPE);
            event.accept(ModItems.DARK_BAMBOO_BLOWPIPE);

            event.accept(ModItems.DART_BASE);
            event.accept(ModItems.IRON_HEAD_DART);
            event.accept(ModItems.POISON_DART);
            event.accept(ModItems.POWDER_DART);
            event.accept(ModItems.LURE_DART);
            event.accept(ModItems.RAZOR_DART);
            event.accept(ModItems.ANNIHILATION_DART);
            event.accept(ModItems.OBLIVION_DART);


            event.getEntries().putAfter(SetPotionFunction.setPotion(new ItemStack(Items.TIPPED_ARROW), ModPotions.BLEED_POTION.get()),
                    new ItemStack(ModItems.CHARMING_AURA_ARROW.get()),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {

            event.getEntries().putAfter(Items.ELYTRA.getDefaultInstance(),
                    ModItems.RANGE_GOGGLES.get().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.accept(ModItems.DART_POUCH);

            for (DyeColor color : DyeColor.values()) {
                event.accept(ModItems.COLORED_DART_POUCHES.get(color));
            }
        }

        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.getEntries().putAfter(Blocks.SMITHING_TABLE.asItem().getDefaultInstance(),
                    ModBlocks.DART_TABLE.get().asItem().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(ModBlocks.DART_TABLE.get().asItem().getDefaultInstance(),
                    ModBlocks.BLOWPIPE_GROUND_STAND.get().asItem().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(ModBlocks.BLOWPIPE_GROUND_STAND.get().asItem().getDefaultInstance(),
                    ModBlocks.BLOWPIPE_WALL_STAND.get().asItem().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(ModBlocks.BLOWPIPE_WALL_STAND.get().asItem().getDefaultInstance(),
                    ModBlocks.DART_STAND.get().asItem().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }

        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.getEntries().putAfter(Items.BLAZE_ROD.asItem().getDefaultInstance(),
                    ModItems.NETHERITE_ROD.get().asItem().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }

        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.getEntries().putAfter(PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.BLEED_POTION.get()),
                    new ItemStack(ModItems.CHARMING_AURA_POTION.get()),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.getEntries().putAfter(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), ModPotions.BLEED_POTION.get()),
                    new ItemStack(ModItems.CHARMING_AURA_SPLASH_POTION.get()),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.getEntries().putAfter(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), ModPotions.BLEED_POTION.get()),
                    new ItemStack(ModItems.CHARMING_AURA_LINGERING_POTION.get()),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

}
