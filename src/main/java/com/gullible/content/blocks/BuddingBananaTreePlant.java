package com.gullible.content.blocks;

import com.gullible.registry.ModBlocks;
import com.gullible.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BuddingBananaTreePlant extends Block {
    public static final DirectionProperty FACING;
    public static final IntegerProperty AGE;
    private static final VoxelShape BASE;

    public BuddingBananaTreePlant(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(AGE, 0).setValue(FACING, Direction.NORTH));
    }

    @Override
    protected boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return levelReader.getBlockState(blockPos.below()).is(ModBlocks.BANANA_TREE_PLANT) || levelReader.getBlockState(blockPos.below()).is(ModBlocks.BUDDING_BANANA_TREE_PLANT);
    }

    @Override
    protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (!blockState.canSurvive(serverLevel, blockPos)){
            serverLevel.destroyBlock(blockPos, true);
        }
    }

    @Override
    protected BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (!blockState.canSurvive(levelAccessor, blockPos)){
            levelAccessor.scheduleTick(blockPos,this, 1);
        }
        return super.updateShape(blockState, direction,blockState2, levelAccessor, blockPos,blockPos2);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState blockState) {
        return true;
    }

    @Override
    protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (randomSource.nextInt(3) == 0 && blockState.getValue(AGE) != 2 && serverLevel.getRawBrightness(blockPos.above(), 0) >= 9){
            serverLevel.setBlock(blockPos, blockState.setValue(AGE, blockState.getValue(AGE) + 1), 3);
        }
        if (serverLevel.getBlockState(blockPos.above()).is(Blocks.AIR) && randomSource.nextInt(3) != 0 ){
            serverLevel.setBlock(blockPos, ModBlocks.BANANA_TREE_PLANT.defaultBlockState().setValue(BananaTreePlant.AGE, 2), 1);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, AGE);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (player.getItemInHand(interactionHand).is(Items.SHEARS) && blockState.getValue(AGE) == 2){
            if (level.random.nextInt(128) == 0){
                ItemEntity itemEntity = new ItemEntity(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(ModItems.GOLDEN_BANANA, 1));
                level.addFreshEntity(itemEntity);
            }
            ItemEntity itemEntity = new ItemEntity(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(ModItems.BANANA,  level.random.nextIntBetweenInclusive(12, 24)));
            level.addFreshEntity(itemEntity);
            itemStack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(interactionHand));
            level.setBlock(blockPos, ModBlocks.BANANA_TREE_PLANT.defaultBlockState().setValue(BananaTreePlant.AGE, 2),11);
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.FAIL;
    }

    @Override
    protected float getDestroyProgress(BlockState blockState, Player player, BlockGetter blockGetter, BlockPos blockPos) {
        if (!player.getMainHandItem().isEmpty()){
            if (blockGetter.getBlockState(blockPos.below()).is(ModBlocks.BANANA_TREE_PLANT)){
                player.level().setBlock(blockPos.below(), ModBlocks.CHOPPED_BANANA_TREE_PLANT.defaultBlockState().setValue(BananaTreeChoppedPlant.AGE, player.level().getBlockState(blockPos.below()).getValue(BananaTreePlant.AGE)), 3);
            }
            return 1.0F;
        } else {
            return super.getDestroyProgress(blockState, player, blockGetter, blockPos);
        }
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return true;
    }

    @Override
    protected boolean isCollisionShapeFullBlock(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return true;
    }

    @Override
    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return BASE;
    }



    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        AGE = BlockStateProperties.AGE_2;

        BASE = Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
    }
}
