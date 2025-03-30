package net.thedragonskull.blowpipemod.client.gui;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.gui.screens.Overlay;
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
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.client.handler.ClientForgeHandler;
import net.thedragonskull.blowpipemod.util.RangeGogglesUtil;

public class GogglesScopeOverlay implements LayeredDraw.Layer {
    public static final ResourceLocation SCOPE = ResourceLocation.fromNamespaceAndPath(BlowPipeMod.MOD_ID,
            "textures/gui/scope_v2.png");
    public static final ResourceLocation SCOUTER = ResourceLocation.fromNamespaceAndPath(BlowPipeMod.MOD_ID,
            "textures/gui/scope_indicator.png");

    public static final GogglesScopeOverlay SCOPE_OVERLAY = new GogglesScopeOverlay();

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (mc.getDebugOverlay().showDebugScreen())
            return;

        if (player == null || !RangeGogglesUtil.hasGogglesEquipped(player) || !mc.options.getCameraType().isFirstPerson()) {
            return;
        }

        drawScope(guiGraphics);

        drawScopeInfo(guiGraphics, deltaTracker.getGameTimeDeltaPartialTick(true), player);
    }

    private void drawScope(GuiGraphics guiGraphics) {
        int imgSize = 175;
        int x = (guiGraphics.guiWidth() - imgSize) / 2;
        int y = (guiGraphics.guiWidth() - imgSize) / 2;

        guiGraphics.blit(SCOPE, x, y, 0, 0, imgSize, imgSize, imgSize, imgSize);

        guiGraphics.pose().scale(0.5f, 0.5f, 0.5f);
        guiGraphics.drawString(Minecraft.getInstance().font, Component.literal("10 blocks"), (x * 2) + 269, (y * 2) + 198, 0x000000, false);
        guiGraphics.drawString(Minecraft.getInstance().font, Component.literal("15 blocks"), (x * 2) + 260, (y * 2) + 214, 0x000000, false);
        guiGraphics.drawString(Minecraft.getInstance().font, Component.literal("20 blocks"), (x * 2) + 249, (y * 2) + 236, 0x000000, false);
        guiGraphics.drawString(Minecraft.getInstance().font, Component.literal("25 blocks"), (x * 2) + 239, (y * 2) + 256, 0x000000, false);
        guiGraphics.drawString(Minecraft.getInstance().font, Component.literal("30 blocks"), (x * 2) + 230, (y * 2) + 287, 0x000000, false);
        guiGraphics.drawString(Minecraft.getInstance().font, Component.literal("35 blocks"), (x * 2) + 220, (y * 2) + 332, 0x000000, false);
    }

    private void drawScopeInfo(GuiGraphics guiGraphics, float partialTick, Player player) {
        Minecraft mc = Minecraft.getInstance();
        double maxDistance = ClientForgeHandler.isZooming ? 80.0 : 40.0;

        Vec3 eyePosition = player.getEyePosition();
        Vec3 lookVector = player.getViewVector(partialTick).scale(maxDistance);
        Vec3 targetPosition = eyePosition.add(lookVector);

        BlockHitResult blockHitResult = player.level().clip(new ClipContext(
                eyePosition, targetPosition, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));

        double blockDistance = blockHitResult.getType() != HitResult.Type.MISS
                ? eyePosition.distanceTo(blockHitResult.getLocation())
                : maxDistance;

        EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(
                player.level(), player, eyePosition, targetPosition,
                player.getBoundingBox().expandTowards(lookVector).inflate(1.0),
                (entity) -> !entity.isSpectator() && entity.isPickable());

        if (entityHitResult != null) {
            double entityDistance = eyePosition.distanceTo(entityHitResult.getLocation());
            if (entityDistance < blockDistance) {
                Entity targetEntity = entityHitResult.getEntity();
                if (targetEntity instanceof LivingEntity livingEntity) {
                    drawEntityInfo(guiGraphics, livingEntity, player);
                }
            }
        }
    }

    private void drawEntityInfo(GuiGraphics guiGraphics, LivingEntity entity, Player player) {
        Minecraft mc = Minecraft.getInstance();
        String mobName = entity.getName().getString();
        int health = (int) entity.getHealth();
        int maxHealth = (int) entity.getMaxHealth();
        int distance = (int) player.distanceTo(entity);

        int scouterSize = 111;
        int scouter_x = (guiGraphics.guiWidth() - scouterSize);
        int scouter_y = 0;

        guiGraphics.blit(SCOUTER, scouter_x - 5, scouter_y - 15, 0, 0, scouterSize, scouterSize, scouterSize, scouterSize);
        renderEntityInGUI(guiGraphics, scouter_x + 70, scouter_y + 58, 25, entity);

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


    private static void renderEntityInGUI(GuiGraphics guiGraphics, int x, int y, int size, LivingEntity entity) {

        float entityHeight = entity.getBbHeight();
        float entityWidth = entity.getBbWidth();

        float maxSize = Math.max(entityHeight, entityWidth);
        float scale = size / maxSize;
        scale = Math.min(scale, size * 0.8F / entityWidth);

        InventoryScreen.renderEntityInInventoryFollowsAngle(guiGraphics, x, y, (int) scale,
                1, 0,0.0f, 0.0f, 0.0f, entity);
    }
}
