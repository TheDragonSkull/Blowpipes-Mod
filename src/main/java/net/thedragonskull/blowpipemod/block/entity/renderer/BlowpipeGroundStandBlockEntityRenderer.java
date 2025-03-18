package net.thedragonskull.blowpipemod.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.thedragonskull.blowpipemod.block.custom.BlowpipeGroundStandBlock;
import net.thedragonskull.blowpipemod.block.entity.BlowpipeGroundStandBlockEntity;
import net.thedragonskull.blowpipemod.item.ModItems;

public class BlowpipeGroundStandBlockEntityRenderer implements BlockEntityRenderer<BlowpipeGroundStandBlockEntity> {

    public BlowpipeGroundStandBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(BlowpipeGroundStandBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStack = pBlockEntity.getStoredBlowpipe();
        BlockState state = pBlockEntity.getBlockState();
        Direction direction = state.getValue(BlowpipeGroundStandBlock.FACING);

        if (itemStack.isEmpty())
            return;

        pPoseStack.pushPose();

        switch (direction) {
            case NORTH -> {
                pPoseStack.translate(0.92f, 0.243f, 0.5f);
                pPoseStack.mulPose(Axis.ZP.rotationDegrees(90));
            }
            case SOUTH -> {
                pPoseStack.translate(0.5f, 0.243f, 0.086f);
                pPoseStack.mulPose(Axis.XP.rotationDegrees(90));
            }
            case EAST -> {
                pPoseStack.translate(0.92f, 0.243f, 0.5f);
                pPoseStack.mulPose(Axis.ZP.rotationDegrees(90));
            }
            case WEST -> {
                pPoseStack.translate(0.5f, 0.243f, 0.075f);
                pPoseStack.mulPose(Axis.XP.rotationDegrees(90));
            }
        }

        pPoseStack.mulPose(Axis.YP.rotationDegrees(45));

        itemRenderer.renderStatic(itemStack, ItemDisplayContext.HEAD, getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pBlockEntity.getLevel(), 1);

        pPoseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

}
