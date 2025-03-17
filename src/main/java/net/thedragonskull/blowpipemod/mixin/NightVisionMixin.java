package net.thedragonskull.blowpipemod.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.client.handler.ClientForgeHandler;
import net.thedragonskull.blowpipemod.util.RangeGogglesUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class NightVisionMixin {

    @Unique
    private static final ResourceLocation NIGHT_VISION_SHADER = new ResourceLocation(BlowPipeMod.MOD_ID,"shaders/post/nightvision.json");

    @Inject(method = "tick", at = @At("HEAD"))
    public void onRenderLevel(CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player != null && !player.isSpectator() && mc.options.getCameraType().isFirstPerson()) {
            if (RangeGogglesUtil.hasGogglesEquipped(player) && ClientForgeHandler.isNightVision) {
                if (mc.gameRenderer.currentEffect() == null) {
                    mc.gameRenderer.loadEffect(NIGHT_VISION_SHADER);
                }
            } else {
                if (mc.gameRenderer.currentEffect() != null) {
                    mc.gameRenderer.shutdownEffect();
                }
            }
        }
    }
}
