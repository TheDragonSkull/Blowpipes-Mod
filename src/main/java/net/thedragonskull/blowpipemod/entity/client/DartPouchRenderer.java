package net.thedragonskull.blowpipemod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.item.custom.DartPouchItem;
import net.thedragonskull.blowpipemod.util.DartPouchColors;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class DartPouchRenderer implements ICurioRenderer {
    private static final ResourceLocation BASE_TEXTURE = new ResourceLocation(BlowPipeMod.MOD_ID, "textures/entity/dart_pouch_bw.png");
    private static final ResourceLocation OVERLAY_TEXTURE = new ResourceLocation(BlowPipeMod.MOD_ID, "textures/entity/dart_pouch_dart.png");

    private final ModelPart pouch;

    public DartPouchRenderer(ModelPart root) {
        this.pouch = root.getChild("pouch");
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        matrixStack.pushPose();

        if (renderLayerParent.getModel() instanceof net.minecraft.client.model.HumanoidModel<?> playerModel) {
            playerModel.body.translateAndRotate(matrixStack);
        }

        matrixStack.translate(0.0F, -0.2F, 0.0F);
        matrixStack.scale(1.0F, 1.0F, 1.0F);

        if (stack.getItem() instanceof DartPouchItem) {

            int dyeColor = getDyeColor(stack);
            float red = (dyeColor >> 16 & 255) / 255.0F;
            float green = (dyeColor >> 8 & 255) / 255.0F;
            float blue = (dyeColor & 255) / 255.0F;

            VertexConsumer baseTexture = renderTypeBuffer.getBuffer(RenderType.entityTranslucent(BASE_TEXTURE));
            pouch.render(matrixStack, baseTexture, light, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
        }


            VertexConsumer overlayTexture = renderTypeBuffer.getBuffer(RenderType.entityTranslucent(OVERLAY_TEXTURE));
            pouch.render(matrixStack, overlayTexture, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);


        matrixStack.popPose();
    }

    private int getDyeColor(ItemStack stack) {
        if (stack.getItem() instanceof DartPouchItem pouchItem) {
            return DartPouchColors.getCustomColor(pouchItem.getColor());
        }
        return 0xFFFFFF;
    }

}
