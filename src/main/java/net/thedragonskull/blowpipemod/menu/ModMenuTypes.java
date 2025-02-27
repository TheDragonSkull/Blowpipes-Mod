package net.thedragonskull.blowpipemod.menu;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thedragonskull.blowpipemod.BlowPipeMod;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, BlowPipeMod.MOD_ID);

    public static final RegistryObject<MenuType<DartPouchMenu>> DART_POUCH_MENU = MENU_TYPES.register("dart_pouch",
            () -> IForgeMenuType.create((id, inventory, buf) -> {
                ItemStackHandler pouchInventory = new ItemStackHandler(6);
                return new DartPouchMenu(id, inventory, pouchInventory);
            }));
}
