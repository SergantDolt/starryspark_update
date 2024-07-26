package com.gullible.content.entities.boulders;

import com.gullible.content.blocks.boulders.ExplosiveBananaBoulderBlock;
import com.gullible.content.blocks.boulders.MegaDamageBananaBoulderBlock;
import com.gullible.registry.ModBlocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

public class MegaDamageBananaBoulder extends AbstractBoulderEntity {
    private final BlockState state;
    public MegaDamageBananaBoulder(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.state = ModBlocks.MEGA_DAMAGE_BANANA_BOULDER.defaultBlockState().setValue(MegaDamageBananaBoulderBlock.IS_FALLING, true);
    }
    public BlockState getState() {
        return this.state;
    }
    @Override
    public void uponLanding() {
        Predicate<Entity> predicate = EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(EntitySelector.LIVING_ENTITY_STILL_ALIVE);
        this.level().getEntities(this, this.getBoundingBox(), predicate).forEach((entity -> entity.hurt(entity.damageSources().genericKill(), 100)));

    }
}
