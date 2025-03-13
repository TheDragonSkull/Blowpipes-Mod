package net.thedragonskull.blowpipemod.mixin;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.thedragonskull.blowpipemod.entity.client.layer.HunterRenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerRenderer.class)
public class HunterRendererMixin {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(EntityRendererProvider.Context context, CallbackInfo ci) {
        VillagerRenderer renderer = (VillagerRenderer) (Object) this;
        renderer.addLayer(new HunterRenderLayer(renderer));
    }

}
