package net.thedragonskull.blowpipemod.util;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.thedragonskull.blowpipemod.component.ModDataComponents;
import net.thedragonskull.blowpipemod.item.ModItems;

public class ModItemProperties {

    public static void addCustomItemProperties() {
        blowpipeStates(ModItems.BLOWPIPE.get());
        blowpipeStates(ModItems.BAMBOO_BLOWPIPE.get());
        blowpipeStates(ModItems.DARK_BAMBOO_BLOWPIPE.get());
    }

    private static void blowpipeStates(Item item) {
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("loaded"), (stack, world, entity, seed) -> BlowpipeUtil.isLoaded(stack) ? 1.0F : 0.0F);

        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("dart_type"), (stack, world, entity, seed) -> {
            var dartTypeComponent = stack.get(ModDataComponents.DART_TYPE.get());

            if (dartTypeComponent != null) {
                return BlowpipeUtil.isLoaded(stack) ? dartTypeComponent : 0.0F;
            }

            return 0.0F;
        });


    }

}
