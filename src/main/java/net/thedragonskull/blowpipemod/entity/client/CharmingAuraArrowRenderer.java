package net.thedragonskull.blowpipemod.entity.client;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.entity.custom.CharmingAuraArrowEntity;

public class CharmingAuraArrowRenderer extends ArrowRenderer<CharmingAuraArrowEntity> {
    public static final ResourceLocation CHARMING_AURA_ARROW_LOCATION = new ResourceLocation(BlowPipeMod.MOD_ID, "textures/entity/arrows/charming_aura_arrow_entity.png");

    public CharmingAuraArrowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(CharmingAuraArrowEntity pEntity) {
        return CHARMING_AURA_ARROW_LOCATION;
    }

}
