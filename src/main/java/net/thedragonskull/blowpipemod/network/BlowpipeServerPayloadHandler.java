package net.thedragonskull.blowpipemod.network;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.thedragonskull.blowpipemod.menu.DartPouchMenu;
import net.thedragonskull.blowpipemod.trigger.ModTriggers;
import net.thedragonskull.blowpipemod.util.BlowpipeUtil;
import net.thedragonskull.blowpipemod.util.ReloadBlowpipeUtil;

import static net.thedragonskull.blowpipemod.capabilities.DartPouchCapabilityProvider.DART_POUCH_INVENTORY;
import static net.thedragonskull.blowpipemod.util.DartPouchUtil.findDartPouch;

public class BlowpipeServerPayloadHandler {

    private static final BlowpipeServerPayloadHandler INSTANCE = new BlowpipeServerPayloadHandler();

    public static BlowpipeServerPayloadHandler getInstance() {
        return INSTANCE;
    }

    public void handleReloadBlowpipe(final C2SReloadBlowpipePacket data, final IPayloadContext ctx) {
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

    public void handleHamelinTrigger(final C2SHamelinTriggerPacket data, final IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();

            if (!(player instanceof ServerPlayer serverPlayer)) {
                return;
            }

            ModTriggers.BLOWPIPE_HAMELIN.get().trigger(serverPlayer);
        });
    }


    public void handleOpenPouch(final C2SOpenPouchMenuPacket data, final IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();

            if (!(player instanceof ServerPlayer serverPlayer)) {
                return;
            }

            ItemStack pouchStack = findDartPouch(player);
            if (pouchStack.isEmpty()) return;

            var cap = pouchStack.getCapability(DART_POUCH_INVENTORY, null);
            if (cap != null) {
                serverPlayer.openMenu(
                        new SimpleMenuProvider(
                                (id, inventoryPlayer, p) -> new DartPouchMenu(id, inventoryPlayer),
                                Component.literal("Dart Pouch")));
            }
        });
    }

    public void handleAnnihilationDartParticles(final C2SAnnihilationDartParticlesPacket data, final IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();

            if (!(player instanceof ServerPlayer serverPlayer)) {
                return;
            }

            ServerLevel level = serverPlayer.serverLevel();
            double centerY = data.entityPos().y + data.entityHeight() / 2.0;

            level.sendParticles(ParticleTypes.SMOKE, data.entityPos().x, centerY, data.entityPos().z,
                    500, 0.5, 0.5, 0.5, 0.1);

        });
    }

    public void handleOblivionDartParticles(final C2SOblivionDartParticlesPacket data, final IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();

            if (!(player instanceof ServerPlayer serverPlayer)) {
                return;
            }

            ServerLevel level = serverPlayer.serverLevel();
            double centerY = data.entityPos().y + data.entityHeight() / 2.0;

            level.sendParticles(ParticleTypes.WHITE_ASH, data.entityPos().x, centerY, data.entityPos().z,
                    1000, 0.5, 0.5, 0.5, 0.1);
            level.sendParticles(ParticleTypes.ASH, data.entityPos().x, centerY, data.entityPos().z,
                    1000, 0.5, 0.5, 0.5, 0.1);

        });
    }

    // more handle here

}
