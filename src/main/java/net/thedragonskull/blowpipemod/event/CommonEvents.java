package net.thedragonskull.blowpipemod.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.item.ModItems;
import net.thedragonskull.blowpipemod.villager.ModVillagers;

import java.util.List;

@Mod.EventBusSubscriber(modid = BlowPipeMod.MOD_ID)
public class CommonEvents {


    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.LEATHERWORKER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            for (int level = 1; level <= 5; level++) {
                for (int i = 0; i < 2; i++) {
                    trades.get(level).add((pTrader, pRandom) -> {

                        DyeColor randomColor = DyeColor.values()[pRandom.nextInt(DyeColor.values().length)];
                        ItemStack coloredPouch = new ItemStack(ModItems.COLORED_DART_POUCHES.get(randomColor).get());

                        Item dyeItem = switch (randomColor) {
                            case WHITE -> Items.WHITE_DYE;
                            case ORANGE -> Items.ORANGE_DYE;
                            case MAGENTA -> Items.MAGENTA_DYE;
                            case LIGHT_BLUE -> Items.LIGHT_BLUE_DYE;
                            case YELLOW -> Items.YELLOW_DYE;
                            case LIME -> Items.LIME_DYE;
                            case PINK -> Items.PINK_DYE;
                            case GRAY -> Items.GRAY_DYE;
                            case LIGHT_GRAY -> Items.LIGHT_GRAY_DYE;
                            case CYAN -> Items.CYAN_DYE;
                            case PURPLE -> Items.PURPLE_DYE;
                            case BLUE -> Items.BLUE_DYE;
                            case BROWN -> Items.BROWN_DYE;
                            case GREEN -> Items.GREEN_DYE;
                            case RED -> Items.RED_DYE;
                            case BLACK -> Items.BLACK_DYE;
                        };
                        ItemStack dyeStack = new ItemStack(dyeItem, 1);

                        return new MerchantOffer(
                                new ItemStack(Items.RABBIT_HIDE, 7),
                                dyeStack,
                                coloredPouch,
                                10,
                                5,
                                0.2f
                        );
                    });
                }
            }
        }

        if (event.getType() == ModVillagers.HUNTER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1
            trades.get(1).add((pTrader, pRandom) ->
                    new MerchantOffer(
                            new ItemStack(Items.EMERALD, 2),
                            new ItemStack(ModItems.DART_BASE.get(), 4),
                            10, 2, 0.05f
                    )
            );

            trades.get(1).add((pTrader, pRandom) ->
                    new MerchantOffer(
                            new ItemStack(Items.EMERALD, 2),
                            new ItemStack(ModItems.IRON_HEAD_DART.get(), 1),
                            10,
                            2,
                            0.05f
                    )
            );

            trades.get(1).add((pTrader, pRandom) ->
                    new MerchantOffer(
                            new ItemStack(Items.EMERALD, 2),
                            new ItemStack(ModItems.BLOWPIPE.get(), 1),
                            100,
                            3,
                            0.1f
                    )
            );

            // Level 2
            trades.get(2).add((pTrader, pRandom) ->
                    new MerchantOffer(
                            new ItemStack(Items.EMERALD, 4),
                            new ItemStack(ModItems.DART_POUCH.get(), 1),
                            100,
                            5,
                            0.3f
                    )
            );

            trades.get(2).add((pTrader, pRandom) ->
                    new MerchantOffer(
                            new ItemStack(Items.EMERALD, 2),
                            new ItemStack(ModItems.POISON_DART.get(), 1),
                            100,
                            4,
                            0.2f
                    )
            );

            // Level 3
            trades.get(3).add((pTrader, pRandom) ->
                    new MerchantOffer(
                            new ItemStack(Items.EMERALD, 2),
                            new ItemStack(ModItems.LURE_DART.get(), 1),
                            100,
                            5,
                            0.3f
                    )
            );

            trades.get(3).add((pTrader, pRandom) ->
                    new MerchantOffer(
                            new ItemStack(Items.EMERALD, 3),
                            new ItemStack(ModItems.POWDER_DART.get(), 1),
                            100,
                            4,
                            0.3f
                    )
            );

            trades.get(3).add((pTrader, pRandom) ->
                    new MerchantOffer(
                            new ItemStack(Items.EMERALD, 4),
                            new ItemStack(ModItems.BAMBOO_BLOWPIPE.get(), 1),
                            100,
                            4,
                            0.3f
                    )
            );

            // Level 4
            trades.get(4).add((pTrader, pRandom) ->
                    new MerchantOffer(
                            new ItemStack(Items.EMERALD, 3),
                            new ItemStack(ModItems.RAZOR_DART.get(), 1),
                            100,
                            5,
                            0.3f
                    )
            );

        }

    }

    @SubscribeEvent
    public static void addCustomWanderingTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades(); //TODO: goggles lvl 5
        
        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 6),
                new ItemStack(ModItems.DART_POUCH.get(), 1),
                3, 2, 0.2f));

        genericTrades.add((pTrader, pRandom) -> {
            DyeColor randomColor = DyeColor.values()[pRandom.nextInt(DyeColor.values().length)];
            ItemStack coloredPouch = new ItemStack(ModItems.COLORED_DART_POUCHES.get(randomColor).get());

            return new MerchantOffer(
                new ItemStack(Items.EMERALD, 8),
                new ItemStack(coloredPouch.getItem()),
                3, 2, 0.2f
            );
        });

        genericTrades.add((pTrader, pRandom) -> {
            DyeColor randomColor = DyeColor.values()[pRandom.nextInt(DyeColor.values().length)];
            ItemStack coloredPouch = new ItemStack(ModItems.COLORED_DART_POUCHES.get(randomColor).get());

            return new MerchantOffer(
                    new ItemStack(Items.EMERALD, 8),
                    new ItemStack(coloredPouch.getItem()),
                    3, 2, 0.2f
            );
        });
    }


}
