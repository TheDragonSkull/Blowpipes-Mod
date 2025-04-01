package net.thedragonskull.blowpipemod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.thedragonskull.blowpipemod.component.ModDataComponents;

import java.util.List;


public class InvTestItem extends Item {
    public final ItemStackHandler INVENTORY = new ItemStackHandler(1);

    public InvTestItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        ItemStack stack = context.getItemInHand();
        BlockState blockState = level.getBlockState(context.getClickedPos());
        ItemStack blockItemStack = blockState.getBlock().asItem().getDefaultInstance();


        if (player != null && !level.isClientSide) {
            stack.set(ModDataComponents.BLOCK_STACK, blockItemStack.getItem().toString());
        }

        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        if(stack.get(ModDataComponents.BLOCK_STACK) != null) {
            tooltipComponents.add(Component.literal("Last Block changed at " + stack.get(ModDataComponents.BLOCK_STACK)));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
