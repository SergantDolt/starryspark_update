package com.gullible.registry;

import com.gullible.rendering.ExplosiveBananaBoulderRenderer;
import com.gullible.rendering.MegaDamageBananaBoulderRenderer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;

public class ModRenderers {
    public static void onInit(){
        //Entities
        EntityRendererRegistry.register(ModEntities.MEGA_DAMAGE_BANANA_BOULDER, MegaDamageBananaBoulderRenderer::new);
        EntityRendererRegistry.register(ModEntities.EXPLOSIVE_BANANA_BOULDER, ExplosiveBananaBoulderRenderer::new);
        //Blocks
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BANANA_TREE_PLANT, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BUDDING_BANANA_TREE_PLANT, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BANANA_TREE_SAPLING, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MEGA_DAMAGE_BANANA_BOULDER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EXPLOSIVE_BANANA_BOULDER, RenderType.cutout());

    }
}
