package net.thedragonskull.blowpipemod.sound;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.thedragonskull.blowpipemod.item.custom.BlowPipe;

public class BlowpipeSoundInstance extends AbstractTickableSoundInstance {
    private final LocalPlayer player;
    private int time = 0;

    public BlowpipeSoundInstance(LocalPlayer player, SoundEvent soundEvent) {
        super(soundEvent, SoundSource.PLAYERS, SoundInstance.createUnseededRandom());
        this.player = player;
        this.volume = 1.0F;
    }

    @Override
    public void tick() {

        if (!this.player.isRemoved() && this.player.isUsingItem()) {

            this.x = this.player.getX();
            this.y = this.player.getY();
            this.z = this.player.getZ();

        } else {
            this.stop();
        }

        time++;

        if (time >= 140) {
            this.stop();
            BlowPipe.activeRatSound = null;
        }
    }

}
