package com.gullible.content.items;

import com.gullible.core.ModTags;
import com.gullible.core.config.ConfigUtil;
import com.gullible.registry.ModSoundEvents;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
public class Megaphone extends Item {
    public Megaphone(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        boolean canPlayRareSound = level.random.nextDouble() < 0.005;
        level.playSound(player,player.blockPosition(), canPlayRareSound ? ModSoundEvents.ITEM_MEGAPHONE_EASTER_EGG : ModSoundEvents.ITEM_MEGAPHONE, SoundSource.PLAYERS);

        if (!player.level().isClientSide()){
            AABB rangeBox = new AABB(player.blockPosition()).inflate(ConfigUtil.config.gadgets.megaphone.megaphoneRange);
            List<LivingEntity> nearbyPlayers = level.getNearbyEntities(LivingEntity.class, TargetingConditions.forNonCombat(), player, rangeBox);
            for (LivingEntity entity : nearbyPlayers) {
                if (entity != player && entity instanceof Player){
                    entity.lookAt(EntityAnchorArgument.Anchor.EYES, player.getEyePosition());
                }
                if (entity instanceof PathfinderMob mob){
                    if (mob.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE)){
                        mob.setTarget(player);
                    }
                    mob.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(player, 1, 0));
                }

            }

            if (ConfigUtil.config.gadgets.megaphone.canShatterBlocks) {
                Iterator<BlockPos> boxResult = BlockPos.betweenClosedStream(rangeBox).iterator();
                boxResult.forEachRemaining(blockPos -> {
                    if (level.getBlockState(blockPos).getTags().anyMatch(Predicate.isEqual(ModTags.WEAK_TO_VIBRATION))) {
                        level.destroyBlock(blockPos, true);
                    }
                });
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(interactionHand));
    }
}
