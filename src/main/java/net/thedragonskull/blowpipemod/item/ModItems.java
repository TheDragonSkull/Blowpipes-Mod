package net.thedragonskull.blowpipemod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.item.custom.DartItem;
import net.thedragonskull.blowpipemod.item.custom.BlowPipe;
import net.thedragonskull.blowpipemod.item.custom.DartPouchItem;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, BlowPipeMod.MOD_ID);

    public static final RegistryObject<Item> BLOWPIPE = ITEMS.register("blowpipe",
            () -> new BlowPipe(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> BAMBOO_BLOWPIPE = ITEMS.register("bamboo_blowpipe",
            () -> new BlowPipe(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> DARK_BAMBOO_BLOWPIPE = ITEMS.register("dark_bamboo_blowpipe",
            () -> new BlowPipe(new Item.Properties().stacksTo(1)));




    public static final RegistryObject<Item> DART_BASE = ITEMS.register("dart_base",
            () -> new DartItem(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> IRON_HEAD_DART = ITEMS.register("iron_head_dart",
            () -> new DartItem(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> POISON_DART = ITEMS.register("poison_dart",
            () -> new DartItem(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> POWDER_DART = ITEMS.register("powder_dart",
            () -> new DartItem(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> LURE_DART = ITEMS.register("lure_dart",
            () -> new DartItem(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> RAZOR_DART = ITEMS.register("razor_dart",
            () -> new DartItem(new Item.Properties().stacksTo(16)));



    public static final RegistryObject<Item> DART_POUCH = ITEMS.register("dart_pouch",
            () -> new DartPouchItem(new Item.Properties().stacksTo(1)));



    //MISC

    public static final RegistryObject<Item> RAT_ADV = ITEMS.register("rat_adv",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
