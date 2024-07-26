package com.gullible.content.entities.boulders;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Predicate;

public abstract class AbstractBoulderEntity extends Entity {
    private int timer = 0;
    public AbstractBoulderEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        this.applyGravity();
        this.move(MoverType.SELF, this.getDeltaMovement());
        if (this.onGround() || this.isLandingOnEntities()){
            timer++;
            this.uponLanding();

            if (timer > 2){
                this.remove(RemovalReason.DISCARDED);
            }
        } else {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5, 0.7));
        }
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
    }

    @Override
    protected double getDefaultGravity() {
        return 0.55;
    }

    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    public abstract void uponLanding();

    public boolean isLandingOnEntities(){
        Predicate<Entity> predicate = EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(EntitySelector.LIVING_ENTITY_STILL_ALIVE);
        List<Entity> victims = this.level().getEntities(this, this.getBoundingBox().inflate(0.1), predicate);
        return !victims.isEmpty();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }
}
