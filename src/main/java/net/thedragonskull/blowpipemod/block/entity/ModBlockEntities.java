package net.thedragonskull.blowpipemod.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.block.ModBlocks;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BlowPipeMod.MOD_ID);


    public static final RegistryObject<BlockEntityType<BlowpipeGroundStandBlockEntity>> BLOWPIPE_GROUND_STAND_BE =
            BLOCK_ENTITIES.register("blowpipe_ground_stand_be", () ->
                    BlockEntityType.Builder.of(BlowpipeGroundStandBlockEntity::new,
                            ModBlocks.BLOWPIPE_GROUND_STAND.get()).build(null));

    public static final RegistryObject<BlockEntityType<BlowpipeWallStandBlockEntity>> BLOWPIPE_WALL_STAND_BE =
            BLOCK_ENTITIES.register("blowpipe_wall_stand_be", () ->
                    BlockEntityType.Builder.of(BlowpipeWallStandBlockEntity::new,
                            ModBlocks.BLOWPIPE_WALL_STAND.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
