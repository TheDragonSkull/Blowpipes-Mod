package net.thedragonskull.blowpipemod.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.util.DartPouchUtil;

import java.util.List;

import static net.thedragonskull.blowpipemod.util.DartPouchUtil.getDartsFromPouch;

public class SelectedDartOverlay {
    public static int currentDartIndex = 0;

    private static final ResourceLocation UPHEAVAL_FONT = new ResourceLocation(BlowPipeMod.MOD_ID, "upheaval");
    private static final Style STYLE = Style.EMPTY.withFont(UPHEAVAL_FONT);

    public static final ResourceLocation SELECTED_DART_FRAME = new ResourceLocation(BlowPipeMod.MOD_ID,
            "textures/gui/selected_dart_frame_square.png");

    public static final ResourceLocation LEATHER_BG = new ResourceLocation(BlowPipeMod.MOD_ID,
            "textures/gui/selected_dart_frame_bg.png");

    public static final IGuiOverlay SELECTED_DART = (((gui, poseStack, partialTick, width, height) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null) {
            return;
        }
        if (mc.options.renderDebug) {
            return;
        }

        ItemStack pouchStack = DartPouchUtil.findDartPouch(player);
        if (pouchStack.isEmpty()) {
            return;
        }

        List<ItemStack> darts = getDartsFromPouch(pouchStack);
        if (darts.isEmpty()) {
            return;
        }

        //Get selected dart
        currentDartIndex = Math.floorMod(currentDartIndex, darts.size());
        ItemStack selectedDart = darts.get(currentDartIndex);

        int xPos = 7;
        int yPos = 17;
        int scale = 42;
        int leatherScale = 22;

        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());

        //Render frame
        guiGraphics.blit(LEATHER_BG, xPos + 8, yPos + 8, 0, 0, leatherScale, leatherScale, leatherScale, leatherScale);
        guiGraphics.blit(SELECTED_DART_FRAME, xPos - 2, yPos - 2, 0, 0, scale, scale, scale, scale);

        //Render item
        guiGraphics.renderItem(selectedDart, 18, 28);
        guiGraphics.renderItemDecorations(mc.font, selectedDart, 18, 28);

        //Render slot number
        String slotNumber = String.valueOf(currentDartIndex + 1);
        Component slotText = Component.literal("SLOT: " + slotNumber).setStyle(STYLE);
        guiGraphics.drawString(mc.font, slotText, 4, 5, 0xFFFFFF, true);

    }));


}



