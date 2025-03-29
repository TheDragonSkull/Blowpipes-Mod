package net.thedragonskull.blowpipemod.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.thedragonskull.blowpipemod.block.entity.BlowpipeWallStandBlockEntity;
import net.thedragonskull.blowpipemod.block.entity.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class BlowpipeWallStandBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final MapCodec<BlowpipeWallStandBlock> CODEC = simpleCodec(BlowpipeWallStandBlock::new);


    private static final VoxelShape SHAPE_NORTH = Block.box(3.5, 0, 12, 12.5, 16, 16);
    private static final VoxelShape SHAPE_SOUTH = Block.box(3.5, 0, 0, 12.5, 16, 4);
    private static final VoxelShape SHAPE_EAST = Block.box(0, 0, 3.5, 4, 16, 12.5);
    private static final VoxelShape SHAPE_WEST = Block.box(12, 0, 3.5, 16, 16, 12.5);

    public BlowpipeWallStandBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        InteractionHand hand = InteractionHand.MAIN_HAND;

        if (level.isClientSide)
            return InteractionResult.SUCCESS;

        Direction facing = state.getValue(FACING);
        Direction clickedFace = hit.getDirection();
        BlockEntity blockEntity = level.getBlockEntity(pos);

        boolean isValidFace = clickedFace == facing ||
                clickedFace == facing.getClockWise() ||
                clickedFace == facing.getCounterClockWise() ||
                clickedFace == Direction.UP;

        if (isValidFace && blockEntity instanceof BlowpipeWallStandBlockEntity stand) {
            Vec3 localPos = hit.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
            double localY = localPos.y;

            if (localY < 0.33) { // INFERIOR
                //player.sendSystemMessage(Component.literal("Has hecho clic en la parte INFERIOR."));
                stand.interact(player, hand, 0);

            } else if (localY < 0.66) { // CENTRAL
                //player.sendSystemMessage(Component.literal("Has hecho clic en la parte CENTRAL."));
                stand.interact(player, hand, 1);

            } else { // SUPERIOR
                //player.sendSystemMessage(Component.literal("Has hecho clic en la parte SUPERIOR."));
                stand.interact(player, hand, 2);
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {

        if (!level.isClientSide && state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);

            if (blockEntity instanceof BlowpipeWallStandBlockEntity standEntity) {
                for (int i = 0; i < 3; i++) {
                    ItemStack storedBlowpipe = standEntity.getStoredBlowpipes(i);

                    if (!storedBlowpipe.isEmpty()) {
                        ItemEntity drop = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, storedBlowpipe);
                        level.addFreshEntity(drop);
                    }
                }
            }
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BlowpipeWallStandBlockEntity(pPos, pState);
    }

    @Override
    public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 0.8F;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case EAST -> SHAPE_EAST;
            case WEST -> SHAPE_WEST;
            default -> SHAPE_NORTH;
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.BLOWPIPE_WALL_STAND_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick());
    }
}
