package com.gullible.rendering;

import com.gullible.StarrysparkUpdate;
import com.gullible.content.entities.boulders.ExplosiveBananaBoulder;
import com.gullible.content.entities.boulders.MegaDamageBananaBoulder;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class ExplosiveBananaBoulderRenderer extends EntityRenderer<ExplosiveBananaBoulder> {
    private final BlockRenderDispatcher dispatcher;

    public ExplosiveBananaBoulderRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.dispatcher = context.getBlockRenderDispatcher();
    }

    @Override
    public ResourceLocation getTextureLocation(ExplosiveBananaBoulder entity) {
        return StarrysparkUpdate.ModLocation("textures/block/banana_boulder_grenade.png");
    }

    @Override
    public void render(ExplosiveBananaBoulder entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        BlockState blockState = entity.getState();
        if(blockState.getRenderShape() == RenderShape.MODEL){
            Level level = entity.level();
            if (blockState != level.getBlockState(entity.blockPosition()) && blockState.getRenderShape() != RenderShape.INVISIBLE){
                poseStack.pushPose();
                BlockPos blockPos = BlockPos.containing(entity.getX(), entity.getBoundingBox().maxY, entity.getBlockZ());
                poseStack.translate(-0.5,0.0, -0.5);
                this.dispatcher.getModelRenderer().tesselateBlock(level, this.dispatcher.getBlockModel(blockState), blockState, blockPos, poseStack, multiBufferSource.getBuffer(ItemBlockRenderTypes.getMovingBlockRenderType(blockState)), false, RandomSource.create(), blockState.getSeed(blockPos), OverlayTexture.NO_OVERLAY);
                poseStack.popPose();
                super.render(entity, f, g, poseStack, multiBufferSource, i);
            }
        }
    }
}
