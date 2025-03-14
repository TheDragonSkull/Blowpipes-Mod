package net.thedragonskull.blowpipemod.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.client.Keybindings;
import net.thedragonskull.blowpipemod.client.gui.SelectedDartOverlay;
import net.thedragonskull.blowpipemod.client.screen.DartPouchScreen;
import net.thedragonskull.blowpipemod.entity.ModEntities;
import net.thedragonskull.blowpipemod.entity.client.*;
import net.thedragonskull.blowpipemod.item.ModItems;
import net.thedragonskull.blowpipemod.item.custom.DartPouchItem;
import net.thedragonskull.blowpipemod.menu.ModMenuTypes;
import net.thedragonskull.blowpipemod.particle.ModParticles;
import net.thedragonskull.blowpipemod.particle.custom.LureGlintParticles;
import net.thedragonskull.blowpipemod.util.DartPouchTooltipComponent;
import net.thedragonskull.blowpipemod.util.ModItemProperties;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod.EventBusSubscriber(modid = BlowPipeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModHandler {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ModItemProperties.addCustomItemProperties();

            EntityRenderers.register(ModEntities.DART.get(), DartProjectileRenderer::new);
            EntityRenderers.register(ModEntities.POISON_DART.get(), PoisonDartProjectileRenderer::new);
            EntityRenderers.register(ModEntities.POWDER_DART.get(), PowderDartProjectileRenderer::new);
            EntityRenderers.register(ModEntities.LURE_DART.get(), LureDartProjectileRenderer::new);
            EntityRenderers.register(ModEntities.IRON_HEAD_DART.get(), IronHeadDartProjectileRenderer::new);
            EntityRenderers.register(ModEntities.RAZOR_DART.get(), RazorDartProjectileRenderer::new);

            MenuScreens.register(ModMenuTypes.DART_POUCH_MENU.get(), DartPouchScreen::new);
        });
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DartProjectileModel.LAYER_LOCATION, DartProjectileModel::createBodyLayer);
        event.registerLayerDefinition(DartPouchModel.LAYER_LOCATION, DartPouchModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerCurioRenderers(EntityRenderersEvent.AddLayers event) {
        DartPouchRenderer renderer = new DartPouchRenderer(event.getEntityModels().bakeLayer(DartPouchModel.LAYER_LOCATION));

        ModItems.ITEMS.getEntries().stream()
                .map(RegistryObject::get)
                .filter(item -> item instanceof DartPouchItem)
                .forEach(item -> CuriosRendererRegistry.register(item, () -> renderer));
    }

    @SubscribeEvent
    public static void onRegisterGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("selected_dart", SelectedDartOverlay.SELECTED_DART);
    }

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(Keybindings.INSTANCE.reloadKey);
        event.register(Keybindings.INSTANCE.openDartPouchKey);
        event.register(Keybindings.INSTANCE.nextDart);
        event.register(Keybindings.INSTANCE.prevDart);
    }

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ModParticles.LURE_GLINT_PARTICLES.get(),
                LureGlintParticles.Provider::new);
    }

    @SubscribeEvent
    public static void registerTooltipComponent(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(DartPouchTooltipComponent.class, component -> component);
    }

}
