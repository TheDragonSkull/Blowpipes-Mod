package net.thedragonskull.blowpipemod.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;

public class FuseSoundInstance extends AbstractTickableSoundInstance {
    private final Entity entity;

    public FuseSoundInstance(Entity entity) {
        super(SoundEvents.TNT_PRIMED, SoundSource.NEUTRAL, entity.level().random);
        this.entity = entity;
        this.looping = false;
        this.delay = 0;
        this.volume = 1.0F;
        this.pitch = 1.0F;
        this.x = entity.getX();
        this.y = entity.getY();
        this.z = entity.getZ();
    }

    @Override
    public void tick() {
        if (entity.isRemoved()) {
            this.stop();
        } else {
            this.x = entity.getX();
            this.y = entity.getY();
            this.z = entity.getZ();
        }
    }
}
