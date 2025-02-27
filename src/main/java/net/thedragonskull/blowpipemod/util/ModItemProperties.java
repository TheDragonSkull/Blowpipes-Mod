package net.thedragonskull.blowpipemod.util;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.thedragonskull.blowpipemod.item.ModItems;
import net.thedragonskull.blowpipemod.item.custom.BlowPipe;

public class ModItemProperties {

    public static void addCustomItemProperties() {
        blowpipeStates(ModItems.BLOWPIPE.get());
    }

    private static void blowpipeStates(Item item) {
        ItemProperties.register(item, new ResourceLocation("loaded"), (stack, world, entity, seed) -> {
            return BlowPipe.isLoaded(stack) ? 1.0F : 0.0F;
        });

        ItemProperties.register(item, new ResourceLocation("dart_type"), (stack, world, entity, seed) -> {
            if (stack.getTag() != null) {
                float dartType = stack.getTag().getFloat("dart_type");
                return BlowPipe.isLoaded(stack) ? dartType : 0.0F;
            }
            return 0.0F;
        });


    }

}
