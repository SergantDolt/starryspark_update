package com.gullible.content.entities.boulders;

import com.gullible.content.blocks.boulders.ExplosiveBananaBoulderBlock;
import com.gullible.registry.ModBlocks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ExplosiveBananaBoulder extends AbstractBoulderEntity {
    private final BlockState state;
    public ExplosiveBananaBoulder(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.state = ModBlocks.EXPLOSIVE_BANANA_BOULDER.defaultBlockState().setValue(ExplosiveBananaBoulderBlock.IS_FALLING, true);
    }

    public BlockState getState() {
        return this.state;
    }

    @Override
    public void uponLanding() {
        this.level().explode(this, this.getX(), this.getY(), this.getZ(), 5, Level.ExplosionInteraction.NONE);
    }
}
