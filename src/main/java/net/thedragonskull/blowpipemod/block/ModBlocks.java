package net.thedragonskull.blowpipemod.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.block.custom.BlowpipeGroundStandBlock;
import net.thedragonskull.blowpipemod.block.custom.BlowpipeWallStandBlock;
import net.thedragonskull.blowpipemod.block.custom.DartStandBlock;
import net.thedragonskull.blowpipemod.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(BlowPipeMod.MOD_ID);


    public static final DeferredBlock<Block> DART_TABLE = registerBlock("dart_table",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.BAMBOO_WOOD)
                    .ignitedByLava()
            ));

    public static final DeferredBlock<Block> BLOWPIPE_GROUND_STAND = registerBlock("blowpipe_ground_stand",
            () -> new BlowpipeGroundStandBlock(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .mapColor(MapColor.PODZOL)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F).sound(SoundType.WOOD)
                    .ignitedByLava()
            ));

    public static final DeferredBlock<Block> BLOWPIPE_WALL_STAND = registerBlock("blowpipe_wall_stand",
            () -> new BlowpipeWallStandBlock(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .mapColor(MapColor.COLOR_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()
            ));

    public static final DeferredBlock<Block> DART_STAND = registerBlock("dart_stand",
            () -> new DartStandBlock(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .noOcclusion()
                    .mapColor(MapColor.PODZOL)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F).sound(SoundType.WOOD)
                    .ignitedByLava()
            ));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
