package net.thedragonskull.blowpipemod.block.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.block.ModBlocks;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, BlowPipeMod.MOD_ID);


    public static final Supplier<BlockEntityType<BlowpipeGroundStandBlockEntity>> BLOWPIPE_GROUND_STAND_BE =
            BLOCK_ENTITIES.register("blowpipe_ground_stand_be", () ->
                    BlockEntityType.Builder.of(BlowpipeGroundStandBlockEntity::new,
                            ModBlocks.BLOWPIPE_GROUND_STAND.get()).build(null));

    public static final Supplier<BlockEntityType<BlowpipeWallStandBlockEntity>> BLOWPIPE_WALL_STAND_BE =
            BLOCK_ENTITIES.register("blowpipe_wall_stand_be", () ->
                    BlockEntityType.Builder.of(BlowpipeWallStandBlockEntity::new,
                            ModBlocks.BLOWPIPE_WALL_STAND.get()).build(null));

    public static final Supplier<BlockEntityType<DartStandBlockEntity>> DART_STAND_BE =
            BLOCK_ENTITIES.register("dart_stand_be", () ->
                    BlockEntityType.Builder.of(DartStandBlockEntity::new,
                            ModBlocks.DART_STAND.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
