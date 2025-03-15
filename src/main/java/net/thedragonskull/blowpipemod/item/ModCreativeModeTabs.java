package net.thedragonskull.blowpipemod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.block.ModBlocks;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BlowPipeMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> BLOWPIPES_TAB = CREATIVE_MODE_TABS.register("blowpipe_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.BLOWPIPE.get()))
                    .title(Component.translatable("creativetab.blowpipe_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(ModItems.DART_POUCH.get());

                        for (DyeColor color : DyeColor.values()) {
                            pOutput.accept(ModItems.COLORED_DART_POUCHES.get(color).get());
                        }

                        pOutput.accept(ModItems.BLOWPIPE.get());
                        pOutput.accept(ModItems.BAMBOO_BLOWPIPE.get());
                        pOutput.accept(ModItems.DARK_BAMBOO_BLOWPIPE.get());

                        pOutput.accept(ModItems.DART_BASE.get());
                        pOutput.accept(ModItems.IRON_HEAD_DART.get());
                        pOutput.accept(ModItems.POISON_DART.get());
                        pOutput.accept(ModItems.POWDER_DART.get());
                        pOutput.accept(ModItems.LURE_DART.get());
                        pOutput.accept(ModItems.RAZOR_DART.get());

                        pOutput.accept(ModItems.RANGE_GOGGLES.get());

                        pOutput.accept(ModBlocks.DART_TABLE.get());

                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
