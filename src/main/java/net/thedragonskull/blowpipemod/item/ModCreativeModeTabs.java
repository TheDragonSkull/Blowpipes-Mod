package net.thedragonskull.blowpipemod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.block.ModBlocks;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BlowPipeMod.MOD_ID);

    public static final Supplier<CreativeModeTab> BLOWPIPES_TAB = CREATIVE_MODE_TABS.register("blowpipe_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.BLOWPIPE.get()))
                    .title(Component.translatable("creativetab.blowpipe_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(ModItems.DART_POUCH);

                        for (DyeColor color : DyeColor.values()) {
                            pOutput.accept(ModItems.COLORED_DART_POUCHES.get(color));
                        }

                        pOutput.accept(ModItems.BLOWPIPE);
                        pOutput.accept(ModItems.BAMBOO_BLOWPIPE);
                        pOutput.accept(ModItems.DARK_BAMBOO_BLOWPIPE);

                        pOutput.accept(ModItems.DART_BASE);
                        pOutput.accept(ModItems.IRON_HEAD_DART);
                        pOutput.accept(ModItems.POISON_DART);
                        pOutput.accept(ModItems.POWDER_DART);
                        pOutput.accept(ModItems.LURE_DART);
                        pOutput.accept(ModItems.RAZOR_DART);
                        pOutput.accept(ModItems.ANNIHILATION_DART);
                        pOutput.accept(ModItems.OBLIVION_DART);

                        pOutput.accept(ModItems.NETHERITE_ROD);

                        pOutput.accept(ModItems.RANGE_GOGGLES);

                        pOutput.accept(ModBlocks.DART_TABLE);
                        pOutput.accept(ModBlocks.BLOWPIPE_GROUND_STAND);
                        pOutput.accept(ModBlocks.BLOWPIPE_WALL_STAND);
                        pOutput.accept(ModBlocks.DART_STAND);

                        pOutput.accept(ModItems.CHARMING_AURA_POTION);
                        pOutput.accept(ModItems.CHARMING_AURA_SPLASH_POTION);
                        pOutput.accept(ModItems.CHARMING_AURA_LINGERING_POTION);
                        pOutput.accept(ModItems.CHARMING_AURA_ARROW);

                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
