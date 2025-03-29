package net.thedragonskull.blowpipemod.villager;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.block.ModBlocks;

import java.util.function.Supplier;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, BlowPipeMod.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(Registries.VILLAGER_PROFESSION, BlowPipeMod.MOD_ID);

    public static final Supplier<PoiType> HUNTER_POI = POI_TYPES.register("hunter_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.DART_TABLE.get().getStateDefinition().getPossibleStates()),
                    1, 1));

    public static final Supplier<VillagerProfession> HUNTER =
            VILLAGER_PROFESSIONS.register("hunter", () -> new VillagerProfession("hunter",
                    holder -> holder.value() == HUNTER_POI.get(), poiTypeHolder -> poiTypeHolder.value() == HUNTER_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.TRIDENT_HIT));


    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
