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
import net.minecraft.world.phys.Vec3;
import net.thedragonskull.blowpipemod.block.custom.BlowpipeWallStandBlock;
import net.thedragonskull.blowpipemod.block.entity.BlowpipeWallStandBlockEntity;
import net.thedragonskull.blowpipemod.item.ModItems;
import org.joml.Quaternionf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlowpipeWallStandBlockEntityRenderer implements BlockEntityRenderer<BlowpipeWallStandBlockEntity> {

    public BlowpipeWallStandBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    public List<Vec3> blowpipePositionsByHeight = new ArrayList<>(List.of(
            new Vec3(-0.8685F, -0.095F, -0.18775),  // Inferior
            new Vec3(-0.8685F, -0.095F, -0.5006F),  // Central
            new Vec3(-0.8685F, -0.095F, -0.8125F)  // Superior
    ));

    public Quaternionf getBlowpipesRotation(Direction direction) {
        return switch (direction) {
            case NORTH -> Axis.YP.rotationDegrees(0);
            case EAST -> Axis.YP.rotationDegrees(270);
            case SOUTH -> Axis.YP.rotationDegrees(180);
            case WEST -> Axis.YP.rotationDegrees(90);
            default -> new Quaternionf();
        };
    }

    @Override
    public void render(BlowpipeWallStandBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        BlockState state = pBlockEntity.getBlockState();
        Quaternionf quaternionf = getBlowpipesRotation(state.getValue(BlowpipeWallStandBlock.FACING));

        ItemStack[] blowpipes = new ItemStack[3];
        for (int i = 0; i < 3; i++) {
            blowpipes[i] = pBlockEntity.getStoredBlowpipes(i);
        }

        boolean hasItems = Arrays.stream(blowpipes).anyMatch(stack -> !stack.isEmpty());
        if (!hasItems)
            return;


        pPoseStack.pushPose();

        pPoseStack.translate(0.5F, 0.5F, 0.5F);
        pPoseStack.mulPose(quaternionf);
        pPoseStack.translate(-0.5F, -0.5F, -0.5F);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(90));

        for (int i = 0; i < 3; i++) {
            if (!blowpipes[i].isEmpty()) {
                pPoseStack.pushPose();
                Vec3 pos = blowpipePositionsByHeight.get(i);
                pPoseStack.translate(pos.x, pos.y, pos.z);
                pPoseStack.mulPose(Axis.YP.rotationDegrees(45));

                itemRenderer.renderStatic(blowpipes[i], ItemDisplayContext.HEAD,
                        getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()),
                        OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pBlockEntity.getLevel(), 1);

                pPoseStack.popPose();
            }
        }



        pPoseStack.popPose();

    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

}
