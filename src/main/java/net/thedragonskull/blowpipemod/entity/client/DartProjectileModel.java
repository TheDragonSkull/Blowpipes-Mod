package net.thedragonskull.blowpipemod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.entity.custom.DartProjectileEntity;

public class DartProjectileModel extends EntityModel<DartProjectileEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BlowPipeMod.MOD_ID, "dart"), "main");
    private final ModelPart dart;
    private final ModelPart point;

    public DartProjectileModel(ModelPart root) {
        this.dart = root.getChild("dart");
        this.point = this.dart.getChild("point");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition dart = partdefinition.addOrReplaceChild("dart", CubeListBuilder.create().texOffs(6, 1).addBox(-0.15F, -0.5062F, -0.15F, 0.3F, 2.0F, 0.3F, new CubeDeformation(0.0F))
                .texOffs(1, 15).addBox(-0.175F, -0.6062F, -0.175F, 0.35F, 0.225F, 0.35F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 23.8062F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition point = dart.addOrReplaceChild("point", CubeListBuilder.create().texOffs(0, 1).addBox(-0.3375F, 4.1507F, -0.1937F, 0.675F, 0.15F, 0.3875F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(-0.3F, 4.0007F, -0.15F, 0.6F, 0.15F, 0.3F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(-0.2625F, 3.8507F, -0.1312F, 0.525F, 0.15F, 0.2625F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(-0.225F, 3.7007F, -0.1125F, 0.45F, 0.15F, 0.225F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(-0.1875F, 3.5882F, -0.0937F, 0.375F, 0.1125F, 0.1875F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(-0.15F, 3.4757F, -0.075F, 0.3F, 0.1125F, 0.15F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(-0.1125F, 3.3632F, -0.0562F, 0.225F, 0.1125F, 0.1125F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(-0.075F, 3.2507F, -0.0375F, 0.15F, 0.1125F, 0.075F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(-0.3375F, 4.3007F, -0.1937F, 0.675F, 0.15F, 0.3875F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(-0.3F, 4.4507F, -0.15F, 0.6F, 0.15F, 0.3F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(-0.225F, 4.6007F, -0.1125F, 0.45F, 0.1125F, 0.225F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(-0.15F, 4.7132F, -0.075F, 0.3F, 0.1125F, 0.15F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.3312F, 0.0F));

        return LayerDefinition.create(meshdefinition, 8, 8);
    }

    @Override
    public void setupAnim(DartProjectileEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        dart.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
