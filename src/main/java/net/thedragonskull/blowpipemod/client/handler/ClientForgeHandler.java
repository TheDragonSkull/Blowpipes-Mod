package net.thedragonskull.blowpipemod.client.handler;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.client.Keybindings;
import net.thedragonskull.blowpipemod.enchantment.ModEnchantments;
import net.thedragonskull.blowpipemod.item.custom.BlowPipe;
import net.thedragonskull.blowpipemod.network.C2SOpenPouchMenuPacket;
import net.thedragonskull.blowpipemod.network.C2SReloadBlowpipePacket;
import net.thedragonskull.blowpipemod.network.PacketHandler;
import net.thedragonskull.blowpipemod.util.RangeGogglesUtil;

import static net.thedragonskull.blowpipemod.util.DartPouchUtil.findDartPouch;
import static net.thedragonskull.blowpipemod.util.DartPouchUtil.updateDartIndex;
import static net.thedragonskull.blowpipemod.util.RangeGogglesUtil.hasGogglesEquipped;

@Mod.EventBusSubscriber(modid = BlowPipeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeHandler {

    @SubscribeEvent
    public static void onInputEvent(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        ItemStack pouchStack = findDartPouch(mc.player);

        if (mc.player != null) {
            if (Keybindings.INSTANCE.reloadKey.isDown()) {
                PacketHandler.sendToServer(new C2SReloadBlowpipePacket());
            }

            if (Keybindings.INSTANCE.openDartPouchKey.consumeClick()) {
                PacketHandler.sendToServer(new C2SOpenPouchMenuPacket());

                if (!pouchStack.isEmpty()) {
                    playPouchOpen(mc.player);
                }
            }

            if (Keybindings.INSTANCE.nextDart.consumeClick()) {
                if (!pouchStack.isEmpty()) {
                    updateDartIndex(true);
                }
            }

            if (Keybindings.INSTANCE.prevDart.consumeClick()) {
                if (!pouchStack.isEmpty()) {
                    updateDartIndex(false);
                }
            }
        }
    }

    private static void playPouchOpen(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F);
    }

    @SubscribeEvent
    public static void onComputerFovModifierEvent(ComputeFovModifierEvent event) {
        if(event.getPlayer().isUsingItem() && event.getPlayer().getUseItem().getItem() instanceof BlowPipe) {
            ItemStack itemStack = event.getPlayer().getItemBySlot(EquipmentSlot.MAINHAND);
            int blowPowerLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.FOCUS.get(), itemStack);

            if (blowPowerLevel > 0) {
                float fovModifier = 1f;
                int ticksUsingItem = event.getPlayer().getTicksUsingItem();
                float deltaTicks = (float) ticksUsingItem / 20f;

                if (deltaTicks > 1f) {
                    deltaTicks = 1f;
                } else {
                    deltaTicks *= deltaTicks;
                }

                fovModifier *= 1f - deltaTicks * 0.5f;
                event.setNewFovModifier(fovModifier);
            }
        }
    }

    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;

        if (hasGogglesEquipped(player)) {
            if (player != null && player.getUseItem().getItem() instanceof BlowPipe) {
                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 0.35f);
            }
        }
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player != null && RangeGogglesUtil.hasGogglesEquipped(player)) {
            if (event.getOverlay() == VanillaGuiOverlay.CROSSHAIR.type()) {
                event.setCanceled(true);
            }
        }
    }


}
