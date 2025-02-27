package net.thedragonskull.blowpipemod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.entity.custom.DartProjectileEntity;
import org.jetbrains.annotations.NotNull;


public class DartProjectileRenderer extends EntityRenderer<DartProjectileEntity> {
    private DartProjectileModel model;

    public DartProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new DartProjectileModel(pContext.bakeLayer(DartProjectileModel.LAYER_LOCATION));
    }

    @Override
    public void render(DartProjectileEntity pEntity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        if(!pEntity.isGrounded()) {
            poseStack.mulPose(Axis.YP.rotationDegrees(180 + Mth.lerp(partialTicks, pEntity.yRotO, pEntity.getYRot())));
            float pitch = Mth.lerp(partialTicks, pEntity.xRotO, pEntity.getXRot());
            poseStack.mulPose(Axis.XP.rotationDegrees(pitch)); // Quitamos el negativo
            poseStack.translate(0, -1.5f, 0.05);


        } else {
            poseStack.mulPose(Axis.YP.rotationDegrees(pEntity.groundedOffset.y));
            poseStack.mulPose(Axis.XP.rotationDegrees(pEntity.groundedOffset.x));
            poseStack.translate(0, -1.5f, 0.05);
        }

        VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(
                buffer, this.model.renderType(this.getTextureLocation(pEntity)),false, false);
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1,1,1,1);
        poseStack.popPose();
        super.render(pEntity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }


    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull DartProjectileEntity pEntity) {
        return new ResourceLocation(BlowPipeMod.MOD_ID, "textures/entity/darts/dart_base.png");
    }
}
