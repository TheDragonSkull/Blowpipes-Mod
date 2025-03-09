package net.thedragonskull.blowpipemod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.thedragonskull.blowpipemod.entity.custom.IronHeadDartProjectileEntity;
import net.thedragonskull.blowpipemod.item.ModItems;


public class IronHeadDartProjectileRenderer extends EntityRenderer<IronHeadDartProjectileEntity> {
    private final EntityRendererProvider.Context context;

    public IronHeadDartProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.context = pContext;
    }

    @Override
    public void render(IronHeadDartProjectileEntity pEntity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        if(!pEntity.isGrounded()) {
            poseStack.mulPose(Axis.YP.rotationDegrees(180 + Mth.lerp(partialTicks, pEntity.yRotO, pEntity.getYRot())));
            float pitch = Mth.lerp(partialTicks, pEntity.xRotO, pEntity.getXRot());
            poseStack.mulPose(Axis.XP.rotationDegrees(pitch)); // Quitamos el negativo
            poseStack.translate(0, -0.4f, 0.2);


        } else {
            poseStack.mulPose(Axis.YP.rotationDegrees(pEntity.groundedOffset.y));
            poseStack.mulPose(Axis.XP.rotationDegrees(pEntity.groundedOffset.x ));
            poseStack.translate(0, -0.4f, 0.2);
        }

        Level level = pEntity.level();
        ItemStack stack = new ItemStack(ModItems.IRON_HEAD_DART.get());

        this.context.getItemRenderer().renderStatic(
                stack,
                ItemDisplayContext.HEAD,
                15728880,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                level,
                0
                );

        poseStack.popPose();
        super.render(pEntity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(IronHeadDartProjectileEntity pEntity) {
        return null;
    }
}
