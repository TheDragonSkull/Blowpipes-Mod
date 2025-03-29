package net.thedragonskull.blowpipemod.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.block.entity.ModBlockEntities;
import net.thedragonskull.blowpipemod.block.entity.renderer.BlowpipeGroundStandBlockEntityRenderer;
import net.thedragonskull.blowpipemod.block.entity.renderer.BlowpipeWallStandBlockEntityRenderer;
import net.thedragonskull.blowpipemod.block.entity.renderer.DartStandBlockEntityRenderer;
import net.thedragonskull.blowpipemod.client.Keybindings;
import net.thedragonskull.blowpipemod.client.gui.GogglesScopeOverlay;
import net.thedragonskull.blowpipemod.client.gui.SelectedDartOverlay;
import net.thedragonskull.blowpipemod.client.screen.DartPouchScreen;
import net.thedragonskull.blowpipemod.entity.ModEntities;
import net.thedragonskull.blowpipemod.entity.client.*;
import net.thedragonskull.blowpipemod.item.ModItems;
import net.thedragonskull.blowpipemod.item.custom.DartPouchItem;
import net.thedragonskull.blowpipemod.item.custom.RangeGoggles;
import net.thedragonskull.blowpipemod.menu.ModMenuTypes;
import net.thedragonskull.blowpipemod.particle.ModParticles;
import net.thedragonskull.blowpipemod.particle.custom.LureGlintParticles;
import net.thedragonskull.blowpipemod.util.DartPouchTooltipComponent;
import net.thedragonskull.blowpipemod.util.ModItemProperties;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.util.function.Supplier;

@EventBusSubscriber(modid = BlowPipeMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

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
            EntityRenderers.register(ModEntities.ANNIHILATION_DART.get(), AnnihilationDartProjectileRenderer::new);
            EntityRenderers.register(ModEntities.OBLIVION_DART.get(), OblivionDartProjectileRenderer::new);
        });
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DartProjectileModel.LAYER_LOCATION, DartProjectileModel::createBodyLayer);
        event.registerLayerDefinition(DartPouchModel.LAYER_LOCATION, DartPouchModel::createBodyLayer);
    }


    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.BLOWPIPE_GROUND_STAND_BE.get(), BlowpipeGroundStandBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.BLOWPIPE_WALL_STAND_BE.get(), BlowpipeWallStandBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.DART_STAND_BE.get(), DartStandBlockEntityRenderer::new);

        event.registerEntityRenderer(ModEntities.CHARMING_AURA_ARROW_ENTITY.get(), CharmingAuraArrowRenderer::new);
    }

    @SubscribeEvent
    public static void registerCurioRenderers(EntityRenderersEvent.AddLayers event) {
        DartPouchRenderer dartPouchRenderer = new DartPouchRenderer(event.getEntityModels().bakeLayer(DartPouchModel.LAYER_LOCATION));

        ModItems.ITEMS.getEntries().stream()
                .map(Supplier::get)
                .filter(item -> item instanceof DartPouchItem)
                .forEach(item -> CuriosRendererRegistry.register(item, () -> dartPouchRenderer));

        ModItems.ITEMS.getEntries().stream()
                .map(Supplier::get)
                .filter(item -> item instanceof RangeGoggles)
                .forEach(item -> CuriosRendererRegistry.register(item, RangeGogglesRenderer::new));
    }

    @SubscribeEvent
    public static void onRegisterGuiOverlays(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(BlowPipeMod.MOD_ID, "selected_dart"), SelectedDartOverlay.SELECTED_DART);
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(BlowPipeMod.MOD_ID, "scope"), GogglesScopeOverlay.SCOPE_OVERLAY);
    }

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(Keybindings.INSTANCE.reloadKey);
        event.register(Keybindings.INSTANCE.openDartPouchKey);
        event.register(Keybindings.INSTANCE.nextDart);
        event.register(Keybindings.INSTANCE.prevDart);
        event.register(Keybindings.INSTANCE.nightVision);
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
