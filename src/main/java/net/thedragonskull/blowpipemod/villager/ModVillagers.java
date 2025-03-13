package net.thedragonskull.blowpipemod.villager;

import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.block.ModBlocks;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, BlowPipeMod.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, BlowPipeMod.MOD_ID);

    public static final RegistryObject<PoiType> HUNTER_POI = POI_TYPES.register("hunter_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.DART_TABLE.get().getStateDefinition().getPossibleStates()),
                    1, 1));

    public static final RegistryObject<VillagerProfession> HUNTER =
            VILLAGER_PROFESSIONS.register("hunter", () -> new VillagerProfession("hunter",
                    holder -> holder.get() == HUNTER_POI.get(), poiTypeHolder -> poiTypeHolder.get() == HUNTER_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.TRIDENT_HIT));


    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
