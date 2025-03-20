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
import net.thedragonskull.blowpipemod.block.custom.BlowpipeWallStandBlock;
import net.thedragonskull.blowpipemod.block.custom.DartStandBlock;
import net.thedragonskull.blowpipemod.block.entity.DartStandBlockEntity;
import org.joml.Quaternionf;

public class DartStandBlockEntityRenderer implements BlockEntityRenderer<DartStandBlockEntity> {

    public DartStandBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    public Quaternionf getDartRotation(Direction direction) {
        return switch (direction) {
            case NORTH -> Axis.YP.rotationDegrees(0);
            case EAST -> Axis.YP.rotationDegrees(270);
            case SOUTH -> Axis.YP.rotationDegrees(180);
            case WEST -> Axis.YP.rotationDegrees(90);
            default -> new Quaternionf();
        };
    }

    @Override
    public void render(DartStandBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStack = pBlockEntity.getStoredDart();
        BlockState state = pBlockEntity.getBlockState();
        Quaternionf quaternionf = getDartRotation(state.getValue(DartStandBlock.FACING));

        if (itemStack.isEmpty())
            return;

        pPoseStack.pushPose();

        pPoseStack.translate(0.5F, 0.5F, 0.5F);
        pPoseStack.mulPose(quaternionf);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(180));

        pPoseStack.translate(0.0F, -0.00325F, 0.15F);


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
