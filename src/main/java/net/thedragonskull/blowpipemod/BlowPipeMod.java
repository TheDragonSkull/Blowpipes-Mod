package net.thedragonskull.blowpipemod;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.thedragonskull.blowpipemod.enchantment.ModEnchantments;
import net.thedragonskull.blowpipemod.entity.ModEntities;
import net.thedragonskull.blowpipemod.item.ModItems;
import net.thedragonskull.blowpipemod.menu.ModMenuTypes;
import net.thedragonskull.blowpipemod.particle.ModParticles;
import net.thedragonskull.blowpipemod.sound.ModSounds;
import net.thedragonskull.blowpipemod.trigger.ModTriggers;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BlowPipeMod.MOD_ID)
public class BlowPipeMod {
    public static final String MOD_ID = "blowpipemod";

    public BlowPipeMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        ModItems.register(modEventBus);
        ModSounds.register(modEventBus);
        ModEntities.register(modEventBus);
        ModParticles.register(modEventBus);
        ModEnchantments.register(modEventBus);

        ModTriggers.register();

        ModMenuTypes.MENU_TYPES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.BLOWPIPE);
            event.accept(ModItems.DART_BASE);
            event.accept(ModItems.POISON_DART);
            event.accept(ModItems.POWDER_DART);
            event.accept(ModItems.LURE_DART);

            event.accept(ModItems.DART_POUCH);
        }
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}
