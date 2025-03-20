package net.thedragonskull.blowpipemod.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.block.custom.BlowpipeGroundStandBlock;
import net.thedragonskull.blowpipemod.block.custom.BlowpipeWallStandBlock;
import net.thedragonskull.blowpipemod.block.custom.DartStandBlock;
import net.thedragonskull.blowpipemod.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, BlowPipeMod.MOD_ID);


    public static final RegistryObject<Block> DART_TABLE = registerBlock("dart_table",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BAMBOO_PLANKS)));

    public static final RegistryObject<Block> BLOWPIPE_GROUND_STAND = registerBlock("blowpipe_ground_stand",
            () -> new BlowpipeGroundStandBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_WOOD).noOcclusion()));

    public static final RegistryObject<Block> BLOWPIPE_WALL_STAND = registerBlock("blowpipe_wall_stand",
            () -> new BlowpipeWallStandBlock(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_WOOD).noOcclusion()));

    public static final RegistryObject<Block> DART_STAND = registerBlock("dart_stand",
            () -> new DartStandBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_WOOD).noOcclusion()));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
