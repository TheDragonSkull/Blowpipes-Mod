package net.thedragonskull.blowpipemod.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.util.RangeGogglesUtil;
import org.joml.Quaternionf;

public class GogglesScopeOverlay {
    public static final ResourceLocation SCOPE = new ResourceLocation(BlowPipeMod.MOD_ID,
            "textures/gui/scope.png");

    public static final IGuiOverlay SCOPE_OVERLAY = (((gui, poseStack, partialTick, width, height) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player != null && RangeGogglesUtil.hasGogglesEquipped(player) && mc.options.getCameraType().isFirstPerson()) {

            if (mc.options.renderDebug) {
                return;
            }

            // Scope
            GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
            int imgSize = 175;
            int x = (width - imgSize) / 2;
            int y = (height - imgSize) / 2;

            guiGraphics.blit(SCOPE, x, y, 0, 0, imgSize, imgSize, imgSize, imgSize);

            guiGraphics.pose().scale(0.5f,0.5f,0.5f);

            // 10 Blocks
            guiGraphics.drawString(mc.font, Component.literal("10 blocks"), (x * 2) + 295, (y * 2) + 165, 0x000000, false);

            // 15 Blocks
            guiGraphics.drawString(mc.font, Component.literal("15 blocks"), (x * 2) + 270, (y * 2) + 184, 0x000000, false);

            // 20 Blocks
            guiGraphics.drawString(mc.font, Component.literal("20 blocks"), (x * 2) + 260, (y * 2) + 219, 0x000000, false);

            // 25 Blocks
            guiGraphics.drawString(mc.font, Component.literal("25 blocks"), (x * 2) + 247, (y * 2) + 251, 0x000000, false);

            // 30 Blocks
            guiGraphics.drawString(mc.font, Component.literal("30 blocks"), (x * 2) + 235, (y * 2) + 283, 0x000000, false);

            // 35 Blocks
            guiGraphics.drawString(mc.font, Component.literal("35+ blocks"), (x * 2) + 225, (y * 2) + 312, 0x000000, false);



            double maxDistance = 40.0;
            Vec3 eyePosition = player.getEyePosition(partialTick);
            Vec3 lookVector = player.getViewVector(partialTick).scale(maxDistance);
            Vec3 targetPosition = eyePosition.add(lookVector);

            EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(
                    player.level(),
                    player,
                    eyePosition,
                    targetPosition,
                    player.getBoundingBox().expandTowards(lookVector).inflate(1.0),
                    (entity) -> !entity.isSpectator() && entity.isPickable()
            );


            if (entityHitResult != null && entityHitResult.getEntity() instanceof LivingEntity livingEntity) {
                String mobName = livingEntity.getName().getString();
                int health = (int) livingEntity.getHealth();
                int maxHealth = (int) livingEntity.getMaxHealth();
                int distance = (int) player.distanceTo(livingEntity);

                guiGraphics.drawString(mc.font, Component.literal(mobName), x + 10, y + 10, 0xFFFFFF, false);
                guiGraphics.drawString(mc.font, Component.literal("Health: " + health + "/" + maxHealth), x + 10, y + 25, 0xFFFFFF, false);
                guiGraphics.drawString(mc.font, Component.literal("Distance: " + distance + " blocks"), x + 10, y + 40, 0xFFFFFF, false);

                renderEntityInGUI(guiGraphics, x + 50, y + 80, 40, livingEntity);
            }
        }

    }));

    private static void renderEntityInGUI(GuiGraphics guiGraphics, int x, int y, int size, LivingEntity entity) {

        float entityHeight = entity.getBbHeight();
        float entityWidth = entity.getBbWidth();

        float maxEntityHeight = 2.0F;

        float scale = (size / maxEntityHeight) * (maxEntityHeight / entityHeight);

        scale = Math.min(scale, size / entityWidth * 0.8F);

        InventoryScreen.renderEntityInInventoryFollowsAngle(guiGraphics, x, y, (int) scale, 1, 0, entity);
    }

}
