package net.thedragonskull.blowpipemod.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import net.thedragonskull.blowpipemod.capabilities.DartPouchCapabilityProvider;
import net.thedragonskull.blowpipemod.menu.DartPouchMenu;
import net.thedragonskull.blowpipemod.util.DartPouchTooltipComponent;
import net.thedragonskull.blowpipemod.util.DartPouchUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DartPouchItem extends Item implements ICurioItem {
    private final DyeColor color; // Guardamos el color

    public DartPouchItem(@Nullable DyeColor color, Properties properties) {
        super(properties);
        this.color = color;
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        DartPouchCapabilityProvider provider = new DartPouchCapabilityProvider(stack);

        if (stack.hasTag() && stack.getTag().contains("dart_pouch_inventory")) {
            provider.deserializeNBT(stack.getTag());
        }

        return provider;
    }


    //Open menu on use
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            stack.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(cap -> {
                if (cap instanceof ItemStackHandler handler) {
                    NetworkHooks.openScreen(
                            (ServerPlayer) player,
                            new SimpleMenuProvider(
                                    (id, inventoryPlayer, p) -> new DartPouchMenu(id, inventoryPlayer, handler),
                                    Component.translatable("container.dart_pouch")), buf -> {});
                }
            });
        }

        playPouchOpen(player);

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    private void playPouchOpen(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            stack.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(cap -> {
                if (cap instanceof ItemStackHandler handler) {
                    tooltip.add(Component.literal("Content:").withStyle(ChatFormatting.GOLD));

                    for (int i = 0; i < handler.getSlots(); i++) {
                        ItemStack storedItem = handler.getStackInSlot(i);
                        System.out.println("  - Slot " + i + ": " + storedItem);
                        if (!storedItem.isEmpty()) {
                            tooltip.add(Component.literal("Slot " + (i + 1) + ": " + storedItem.getCount() + "x ")
                                    .append(storedItem.getHoverName())
                                    .withStyle(ChatFormatting.GRAY));
                        }
                    }
                }
            });
        } else {
            tooltip.add(Component.literal("Press Shift for details").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }


    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        List<ItemStack> items = DartPouchUtil.getDartsFromPouch(stack);
        return Optional.of(new DartPouchTooltipComponent(items));
    }

    @Override
    public List<Component> getSlotsTooltip(List<Component> tooltips, ItemStack stack) {
        tooltips.clear();
        return tooltips;
    }

    @Override
    public boolean canSync(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public CompoundTag writeSyncData(SlotContext slotContext, ItemStack stack) {
        CompoundTag tag = new CompoundTag();

        stack.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(cap -> {
            if (cap instanceof ItemStackHandler handler) {
                tag.put("dart_pouch_inventory", handler.serializeNBT());
            }
        });

        return tag;
    }

    @Override
    public void readSyncData(SlotContext slotContext, CompoundTag compound, ItemStack stack) {
        if (compound.contains("dart_pouch_inventory")) {
            stack.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(cap -> {
                if (cap instanceof ItemStackHandler handler) {
                    handler.deserializeNBT(compound.getCompound("dart_pouch_inventory"));
                }
            });
        }
    }

    public DyeColor getColor() {
        return color;
    }

}
