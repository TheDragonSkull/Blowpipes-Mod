package net.thedragonskull.blowpipemod.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.component.ModDataComponents;
import net.thedragonskull.blowpipemod.component.custom.DartPouchContents;
import net.thedragonskull.blowpipemod.item.custom.*;
import net.thedragonskull.blowpipemod.potion.custom.CharmingAuraLingeringPotion;
import net.thedragonskull.blowpipemod.potion.custom.CharmingAuraPotion;
import net.thedragonskull.blowpipemod.potion.custom.CharmingAuraSplashPotion;

import java.util.HashMap;
import java.util.Map;

public class ModItems {

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(BlowPipeMod.MOD_ID);

    public static final DeferredItem<Item> BLOWPIPE = ITEMS.register("blowpipe",
            () -> new BlowPipe(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> BAMBOO_BLOWPIPE = ITEMS.register("bamboo_blowpipe",
            () -> new BlowPipe(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> DARK_BAMBOO_BLOWPIPE = ITEMS.register("dark_bamboo_blowpipe",
            () -> new BlowPipe(new Item.Properties().stacksTo(1)));


    public static final DeferredItem<Item> DART_BASE = ITEMS.register("dart_base",
            () -> new DartItem(new Item.Properties().stacksTo(16)));

    public static final DeferredItem<Item> IRON_HEAD_DART = ITEMS.register("iron_head_dart",
            () -> new DartItem(new Item.Properties().stacksTo(16)));

    public static final DeferredItem<Item> POISON_DART = ITEMS.register("poison_dart",
            () -> new DartItem(new Item.Properties().stacksTo(16)));

    public static final DeferredItem<Item> POWDER_DART = ITEMS.register("powder_dart",
            () -> new DartItem(new Item.Properties().stacksTo(16)));

    public static final DeferredItem<Item> LURE_DART = ITEMS.register("lure_dart",
            () -> new DartItem(new Item.Properties().stacksTo(16)));

    public static final DeferredItem<Item> RAZOR_DART = ITEMS.register("razor_dart",
            () -> new DartItem(new Item.Properties().stacksTo(16)));

    public static final DeferredItem<Item> ANNIHILATION_DART = ITEMS.register("annihilation_dart",
            () -> new DartItem(new Item.Properties().stacksTo(16)));

    public static final DeferredItem<Item> OBLIVION_DART = ITEMS.register("oblivion_dart",
            () -> new DartItem(new Item.Properties().stacksTo(16)) {
                @Override
                public boolean isFoil(ItemStack pStack) {
                    return true;
                }
            });


    public static final DeferredItem<Item> NETHERITE_ROD = ITEMS.register("netherite_rod",
            () -> new Item(new Item.Properties()));


    public static final DeferredItem<Item> RANGE_GOGGLES = ITEMS.register("range_goggles",
            () -> new RangeGoggles(new Item.Properties().stacksTo(1)));



    public static final DeferredItem<Item> DART_POUCH = ITEMS.register("dart_pouch",
            () -> new DartPouchItem(null, new Item.Properties().stacksTo(1)));

    public static final Map<DyeColor, DeferredItem<Item>> COLORED_DART_POUCHES = new HashMap<>();

    static {
        for (DyeColor color : DyeColor.values()) {
            COLORED_DART_POUCHES.put(color, ITEMS.register("dart_pouch_" + color.getName(),
                    () -> new DartPouchItem(color, new Item.Properties().stacksTo(1))));
        }
    }


    //MISC

    public static final DeferredItem<Item> INV_TEST_ITEM = ITEMS.register("inv_test_item",
            () -> new InvTestItem(new Item.Properties()));

    public static final DeferredItem<Item> RAT_ADV = ITEMS.register("rat_adv",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> FOLLOW_LEADER_ADV = ITEMS.register("follow_leader_adv",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CHARMING_AURA_POTION = ITEMS.register("charming_aura_potion",
            () -> new CharmingAuraPotion(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CHARMING_AURA_SPLASH_POTION = ITEMS.register("charming_aura_splash_potion",
            () -> new CharmingAuraSplashPotion(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CHARMING_AURA_LINGERING_POTION = ITEMS.register("charming_aura_lingering_potion",
            () -> new CharmingAuraLingeringPotion(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CHARMING_AURA_ARROW = ITEMS.register("charming_aura_arrow",
            () -> new CharmingAuraArrow(new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
