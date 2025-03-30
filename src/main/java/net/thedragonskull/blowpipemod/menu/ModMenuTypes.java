package net.thedragonskull.blowpipemod.menu;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thedragonskull.blowpipemod.BlowPipeMod;

import java.util.function.Supplier;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(Registries.MENU, BlowPipeMod.MOD_ID);

/*    public static final RegistryObject<MenuType<DartPouchMenu>> DART_POUCH_MENU = MENU_TYPES.register("dart_pouch",
            () -> IForgeMenuType.create((id, inventory, buf) -> {
                ItemStackHandler pouchInventory = new ItemStackHandler(6);
                return new DartPouchMenu(id, inventory, pouchInventory);
            }));

    public static final DeferredHolder<MenuType<?>, MenuType<DartPouchMenu>> DART_POUCH_MENU =
            registerMenuType("dart_pouch_menu", DartPouchMenu::new);
 */

    public static final Supplier<MenuType<DartPouchMenu>> DART_POUCH_MENU = MENU_TYPES.register("dart_pouch_menu",
            () -> new MenuType<>(DartPouchMenu::new, FeatureFlags.DEFAULT_FLAGS));

    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name,
                                                                                                               IContainerFactory<T> factory) {
        return MENU_TYPES.register(name, () -> IMenuTypeExtension.create(factory));
    }

}
