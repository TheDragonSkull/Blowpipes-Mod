package net.thedragonskull.blowpipemod.client.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.client.Keybindings;
import net.thedragonskull.blowpipemod.menu.DartPouchMenu;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class DartPouchScreen extends AbstractContainerScreen<DartPouchMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BlowPipeMod.MOD_ID, "textures/gui/dart_pouch_screen.png");

    public DartPouchScreen(DartPouchMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        renderBackground(pGuiGraphics);
        pGuiGraphics.blit(TEXTURE ,leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        for (int i = 0; i < 6; i++) {
            Slot slot = this.menu.getSlot(i);
            if (isMouseOverSlot(slot, pMouseX, pMouseY)) {
                if (slot.hasItem()) {
                    ItemStack itemStack = slot.getItem();
                    Minecraft minecraft = Minecraft.getInstance();
                    List<Component> tooltip = itemStack.getTooltipLines(minecraft.player,
                            minecraft.options.advancedItemTooltips ? TooltipFlag.ADVANCED : TooltipFlag.NORMAL
                    );

                    //Add slot number
                    tooltip.add(Component.literal("Pouch Slot: " + (i + 1)).withStyle(ChatFormatting.DARK_PURPLE));

                    //Render tooltip
                    pGuiGraphics.renderTooltip(this.font, tooltip, Optional.empty(), pMouseX, pMouseY);
                } else {
                    //If empty, render only slot number
                    pGuiGraphics.renderTooltip(this.font,
                            List.of(Component.literal("Pouch Slot: " + (i + 1)).withStyle(ChatFormatting.GRAY)),
                            Optional.empty(), pMouseX, pMouseY
                    );
                }
                return;
            }
        }

        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    //Empty, not rendering inventory titles
    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
    }

    private boolean isMouseOverSlot(Slot slot, int mouseX, int mouseY) {
        int slotX = slot.x + this.leftPos;
        int slotY = slot.y + this.topPos;

        return mouseX >= slotX && mouseX < slotX + 18 && mouseY >= slotY && mouseY < slotY + 18;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        Player player = Minecraft.getInstance().player;

        if (player != null && Keybindings.INSTANCE.openDartPouchKey.matches(keyCode, scanCode)) {
            player.closeContainer();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

}
