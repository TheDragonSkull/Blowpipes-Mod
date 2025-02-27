package net.thedragonskull.blowpipemod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.thedragonskull.blowpipemod.BlowPipeMod;

public class DartPouchModel extends EntityModel<AbstractClientPlayer> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BlowPipeMod.MOD_ID, "textures/entity/dart_pouch.png"), "main");
    private final ModelPart pouch;

    public DartPouchModel(ModelPart pouch) {
        this.pouch = pouch.getChild("pouch");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition pouch = partdefinition.addOrReplaceChild("pouch", CubeListBuilder.create().texOffs(0, 0).addBox(-1.6F, -0.1333F, -1.6F, 3.2F, 3.0F, 3.2F, new CubeDeformation(0.0F))
                .texOffs(11, 14).addBox(-0.625F, -0.6333F, -0.625F, 1.25F, 0.5F, 1.25F, new CubeDeformation(0.0F))
                .texOffs(0, 13).addBox(-1.125F, -1.3333F, -1.125F, 2.25F, 0.7F, 2.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 11.1333F, -3.9F, -0.1745F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(AbstractClientPlayer pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        pouch.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
