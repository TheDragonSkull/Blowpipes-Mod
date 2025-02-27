package net.thedragonskull.blowpipemod.util;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.thedragonskull.blowpipemod.BlowPipeMod;

import java.util.List;

public class DartPouchTooltipComponent implements TooltipComponent, ClientTooltipComponent {
    private static final ResourceLocation POUCH_BACKGROUND = new ResourceLocation(BlowPipeMod.MOD_ID, "textures/gui/dart_pouch_tooltip.png");
    private final List<ItemStack> items;

    public DartPouchTooltipComponent(List<ItemStack> items) {
        this.items = items;
    }

    @Override
    public int getWidth(Font font) {
        return 3 * 18; //3 columns 18px each
    }

    @Override
    public int getHeight() {
        return 2 * 28; //2 rows 28px each
    }


    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
        int columns = 3;
        int rows = 2;
        int slotSize = 18;
        int width = columns * slotSize;
        int height = rows * slotSize;

        int imageWidth = 64;
        int imageHeight = 44;

        int xOffset = x + (width - imageWidth) / 2 + 8;
        int yOffset = y + (height - imageHeight) / 2 + 8;

        guiGraphics.blit(POUCH_BACKGROUND, xOffset - 4, yOffset - 4, 0, 0, imageWidth + 8, imageHeight + 8, imageWidth + 8, imageHeight + 8);

        for (int i = 0; i < items.size(); i++) {
            int itemX = x + (i % columns) * slotSize + 9;
            int itemY = y + (i / columns) * slotSize + 9;

            guiGraphics.renderItem(items.get(i), itemX, itemY);
            guiGraphics.renderItemDecorations(font, items.get(i), itemX, itemY);
        }
    }


}
