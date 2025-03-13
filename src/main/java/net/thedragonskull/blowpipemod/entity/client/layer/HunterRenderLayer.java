package net.thedragonskull.blowpipemod.entity.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemDisplayContext;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.entity.client.DartPouchModel;
import net.thedragonskull.blowpipemod.item.ModItems;
import net.thedragonskull.blowpipemod.villager.ModVillagers;

public class HunterRenderLayer extends RenderLayer<Villager, VillagerModel<Villager>> {

    private final ModelPart pouch;

    public HunterRenderLayer(RenderLayerParent<Villager, VillagerModel<Villager>> pRenderer) {
        super(pRenderer);
        ModelPart root = Minecraft.getInstance().getEntityModels().bakeLayer(DartPouchModel.LAYER_LOCATION);
        this.pouch = root.getChild("pouch");
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, Villager pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (pLivingEntity.getVillagerData().getProfession().equals(ModVillagers.HUNTER.get())) {

            poseStack.pushPose();
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

            poseStack.translate(-0.31D, 0.5D, 0.1D);
            poseStack.scale(0.7F, 0.7F, 0.7F);

            itemRenderer.renderStatic(
                    ModItems.BLOWPIPE.get().getDefaultInstance(),
                    ItemDisplayContext.HEAD,
                    pPackedLight,
                    OverlayTexture.NO_OVERLAY,
                    poseStack,
                    pBuffer,
                    pLivingEntity.level(),
                    0
            );
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(-0.31D, 0.5D, -0.15D);
            poseStack.scale(0.7F, 0.7F, 0.7F);
            poseStack.mulPose(Axis.XP.rotationDegrees(15));

            itemRenderer.renderStatic(
                    ModItems.BAMBOO_BLOWPIPE.get().getDefaultInstance(),
                    ItemDisplayContext.HEAD,
                    pPackedLight,
                    OverlayTexture.NO_OVERLAY,
                    poseStack,
                    pBuffer,
                    pLivingEntity.level(),
                    0
            );
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(0.1D, 0D, -0.2D);
            poseStack.mulPose(Axis.YP.rotationDegrees(-90));

            VertexConsumer vertexConsumer = pBuffer.getBuffer(RenderType.entityCutout(new ResourceLocation(BlowPipeMod.MOD_ID, "textures/entity/dart_pouch.png")));
            pouch.render(poseStack, vertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY);

            poseStack.popPose();
        }
    }
}
