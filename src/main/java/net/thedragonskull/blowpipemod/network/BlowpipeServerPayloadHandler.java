package net.thedragonskull.blowpipemod.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.thedragonskull.blowpipemod.trigger.ModTriggers;
import net.thedragonskull.blowpipemod.util.BlowpipeUtil;
import net.thedragonskull.blowpipemod.util.ReloadBlowpipeUtil;

public class BlowpipeServerPayloadHandler {

    private static final BlowpipeServerPayloadHandler INSTANCE = new BlowpipeServerPayloadHandler();

    public static BlowpipeServerPayloadHandler getInstance() {
        return INSTANCE;
    }

    public void handlerReloadBlowpipe(final C2SReloadBlowpipePacket data, final IPayloadContext ctx) {
        ctx.enqueueWork(() -> {

            if (!(ctx.player() instanceof ServerPlayer player)) {
                return;
            }

            ItemStack mainHand = player.getMainHandItem();

            if (BlowpipeUtil.isLoaded(mainHand)) {
                ReloadBlowpipeUtil.unloadBlowpipe(player, mainHand);
            } else {
                ReloadBlowpipeUtil.loadBlowpipe(player, mainHand);
            }

        });
    }

    public void handlerHamelinTrigger(final C2SHamelinTriggerPacket data, final IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();

            if (!(player instanceof ServerPlayer serverPlayer)) {
                return;
            }

            ModTriggers.BLOWPIPE_HAMELIN.get().trigger(serverPlayer);
        });
    }

    // more handle here

}
