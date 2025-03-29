package net.thedragonskull.blowpipemod.menu;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thedragonskull.blowpipemod.BlowPipeMod;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(Registries.MENU, BlowPipeMod.MOD_ID);

    public static final RegistryObject<MenuType<DartPouchMenu>> DART_POUCH_MENU = MENU_TYPES.register("dart_pouch",
            () -> IForgeMenuType.create((id, inventory, buf) -> {
                ItemStackHandler pouchInventory = new ItemStackHandler(6);
                return new DartPouchMenu(id, inventory, pouchInventory);
            }));
}
