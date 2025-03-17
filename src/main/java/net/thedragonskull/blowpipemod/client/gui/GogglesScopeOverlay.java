package net.thedragonskull.blowpipemod.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.client.handler.ClientForgeHandler;
import net.thedragonskull.blowpipemod.util.RangeGogglesUtil;

public class GogglesScopeOverlay {
    public static final ResourceLocation SCOPE = new ResourceLocation(BlowPipeMod.MOD_ID,
            "textures/gui/scope_v2.png");
    public static final ResourceLocation SCOUTER = new ResourceLocation(BlowPipeMod.MOD_ID,
            "textures/gui/scope_indicator.png");


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
            guiGraphics.drawString(mc.font, Component.literal("10 blocks"), (x * 2) + 269, (y * 2) + 198, 0x000000, false);

            // 15 Blocks
            guiGraphics.drawString(mc.font, Component.literal("15 blocks"), (x * 2) + 260, (y * 2) + 214, 0x000000, false);

            // 20 Blocks
            guiGraphics.drawString(mc.font, Component.literal("20 blocks"), (x * 2) + 249, (y * 2) + 236, 0x000000, false);

            // 25 Blocks
            guiGraphics.drawString(mc.font, Component.literal("25 blocks"), (x * 2) + 239, (y * 2) + 256, 0x000000, false);

            // 30 Blocks
            guiGraphics.drawString(mc.font, Component.literal("30 blocks"), (x * 2) + 230, (y * 2) + 287, 0x000000, false);

            // 35 Blocks
            guiGraphics.drawString(mc.font, Component.literal("35 blocks"), (x * 2) + 220, (y * 2) + 332, 0x000000, false);
        }

    }));

    public static final IGuiOverlay SCOPE_INFO = (((gui, poseStack, partialTick, width, height) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (mc.options.renderDebug) {
            return;
        }

        if (player != null && RangeGogglesUtil.hasGogglesEquipped(player) && mc.options.getCameraType().isFirstPerson()) {

            // Ray tracing & target render
            double maxDistance = ClientForgeHandler.isZooming ? 80.0 : 40.0;

            Vec3 eyePosition = player.getEyePosition(partialTick);
            Vec3 lookVector = player.getViewVector(partialTick).scale(maxDistance);
            Vec3 targetPosition = eyePosition.add(lookVector);

            BlockHitResult blockHitResult = player.level().clip(new ClipContext(
                    eyePosition,
                    targetPosition,
                    ClipContext.Block.COLLIDER,
                    ClipContext.Fluid.NONE,
                    player
            ));

            double blockDistance = blockHitResult.getType() != HitResult.Type.MISS
                    ? eyePosition.distanceTo(blockHitResult.getLocation())
                    : maxDistance;

            EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(
                    player.level(),
                    player,
                    eyePosition,
                    targetPosition,
                    player.getBoundingBox().expandTowards(lookVector).inflate(1.0),
                    (entity) -> !entity.isSpectator() && entity.isPickable()
            );

            // Scope Info
            if (entityHitResult != null) {
                double entityDistance = eyePosition.distanceTo(entityHitResult.getLocation());

                if (entityDistance < blockDistance) {
                    Entity targetEntity = entityHitResult.getEntity();

                    if (targetEntity instanceof LivingEntity livingEntity) {
                        String mobName = livingEntity.getName().getString();
                        int health = (int) livingEntity.getHealth();
                        int maxHealth = (int) livingEntity.getMaxHealth();
                        int distance = (int) player.distanceTo(livingEntity);

                        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
                        int scouterSize = 111;
                        int scouter_x = (width - scouterSize);
                        int scouter_y = 0;

                        guiGraphics.blit(SCOUTER, scouter_x - 5, scouter_y - 15, 0, 0, scouterSize, scouterSize, scouterSize, scouterSize);
                        renderEntityInGUI(guiGraphics, scouter_x + 70, scouter_y + 58, 25, livingEntity);

                        guiGraphics.pose().pushPose();
                        float scale = 0.6f;
                        guiGraphics.pose().scale(scale, scale, scale);

                        int adj_x = (int) ((scouter_x - 4) / scale);
                        int adj_y1 = (int) ((scouter_y + 25) / scale);
                        int adj_y2 = (int) ((scouter_y + 35) / scale);
                        int adj_y3 = (int) ((scouter_y + 44) / scale);

                        guiGraphics.drawString(mc.font, Component.literal(mobName), adj_x, adj_y1, 0xFFFFFF, false);
                        guiGraphics.drawString(mc.font, Component.literal("Health: " + health + "/" + maxHealth), adj_x, adj_y2, 0xFFFFFF, false);
                        guiGraphics.drawString(mc.font, Component.literal("Dist: " + distance + " blocks"), adj_x, adj_y3, 0xFFFFFF, false);


                    }
                }
            }
        }

    }));

    private static void renderEntityInGUI(GuiGraphics guiGraphics, int x, int y, int size, LivingEntity entity) {

        float entityHeight = entity.getBbHeight();
        float entityWidth = entity.getBbWidth();

        float maxSize = Math.max(entityHeight, entityWidth);
        float scale = size / maxSize;
        scale = Math.min(scale, size * 0.8F / entityWidth);

        InventoryScreen.renderEntityInInventoryFollowsAngle(guiGraphics, x, y, (int) scale, 1, 0, entity);
    }

}
