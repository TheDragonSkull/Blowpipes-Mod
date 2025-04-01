package net.thedragonskull.blowpipemod.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.thedragonskull.blowpipemod.screen.custom.DartPouchMenu;
import net.thedragonskull.blowpipemod.util.DartPouchTooltipComponent;
import net.thedragonskull.blowpipemod.util.DartPouchUtil;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static net.thedragonskull.blowpipemod.capabilities.DartPouchCapabilityProvider.DART_POUCH_INVENTORY;

public class DartPouchItem extends Item implements ICurioItem {
    public final ItemStackHandler itemHandler = new ItemStackHandler(6);
    private final DyeColor color;

    public DartPouchItem(@Nullable DyeColor color, Properties properties) {
        super(properties);
        this.color = color;
    }

    //Open menu on use
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        stack.getCapability(Capabilities.ItemHandler.ITEM);

        if (!level.isClientSide) {
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.openMenu(
                        new SimpleMenuProvider(
                                (id, inventoryPlayer, p) -> new DartPouchMenu(id, inventoryPlayer, stack),
                                Component.literal("Dart Pouch")));
            }
        }

        playPouchOpen(player);

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }


    private void playPouchOpen(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            var cap = stack.getCapability(DART_POUCH_INVENTORY, null);
            if (cap != null && cap instanceof ItemStackHandler handler) {
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
        } else {
            tooltip.add(Component.literal("Press Shift for details").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, context, tooltip, tooltipFlag);
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        List<ItemStack> items = DartPouchUtil.getDartsFromPouch(stack);
        return Optional.of(new DartPouchTooltipComponent(items));
    }

    @Override
    public List<Component> getSlotsTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
        return new ArrayList<>();
    }

    @Override
    public boolean canSync(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public CompoundTag writeSyncData(SlotContext slotContext, ItemStack stack) {
        CompoundTag tag = new CompoundTag();
        HolderLookup.Provider provider = slotContext.entity().level().registryAccess();
        tag.put("dart_pouch_inventory", itemHandler.serializeNBT(provider)); //todo not null
        return tag;
    }

    @Override
    public void readSyncData(SlotContext slotContext, CompoundTag compound, ItemStack stack) {
        HolderLookup.Provider provider = slotContext.entity().level().registryAccess();
        itemHandler.deserializeNBT(provider, compound.getCompound("dart_pouch_inventory"));
    }

    public DyeColor getColor() {
        return color;
    }

}
