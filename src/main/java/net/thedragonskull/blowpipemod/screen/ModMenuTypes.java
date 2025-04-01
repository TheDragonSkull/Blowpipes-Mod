package net.thedragonskull.blowpipemod.screen;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.screen.custom.DartPouchMenu;
import net.thedragonskull.blowpipemod.screen.custom.TestMenu;

import java.util.function.Supplier;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(Registries.MENU, BlowPipeMod.MOD_ID);


    public static final Supplier<MenuType<DartPouchMenu>> DART_POUCH_MENU =
            MENU_TYPES.register("dart_pouch_menu", () -> new MenuType<>(DartPouchMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static final Supplier<MenuType<TestMenu>> TEST_MENU =
            MENU_TYPES.register("test_menu", () -> new MenuType<>(TestMenu::new, FeatureFlags.DEFAULT_FLAGS));

    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name,
                                                                                                               IContainerFactory<T> factory) {
        return MENU_TYPES.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
