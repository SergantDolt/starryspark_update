package com.gullible.content.blocks;

import com.gullible.core.ModTags;
import com.gullible.registry.ModBlocks;
import com.terraformersmc.modmenu.util.mod.Mod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BananaTreePlant extends Block implements BonemealableBlock {
    public static final IntegerProperty AGE;

    protected static final VoxelShape THICKEST_TRUNK;
    protected static final VoxelShape THICK_TRUNK;
    protected static final VoxelShape THINNER_TRUNK;
    protected static final VoxelShape THINNEST_TRUNK;

    public BananaTreePlant(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return true;
    }

    @Override
    protected boolean isCollisionShapeFullBlock(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }

    @Override
    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return switch (blockState.getValue(AGE)) {
            default -> THICKEST_TRUNK;
            case 1 -> THICK_TRUNK;
            case 2 -> THINNER_TRUNK;
            case 3 -> THINNEST_TRUNK;
        };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
        if (!fluidState.isEmpty()){
            return null;
        } else {
            BlockState blockState = blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos());
            if (blockState.is(ModBlocks.BANANA_TREE_SAPLING)){
                return this.defaultBlockState().setValue(AGE, 0);
            } else if (blockState.is(ModBlocks.BANANA_TREE_PLANT)){
                int i = blockState.getValue(AGE) > 0 ? 1 : 0;
                return this.defaultBlockState().setValue(AGE, i);
            }
        }
        return super.getStateForPlacement(blockPlaceContext);
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
            levelAccessor.scheduleTick(blockPos, this,1);
        }
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState blockState) {
        return blockState.getValue(AGE) != 3;
    }

    @Override
    protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (randomSource.nextInt(3) == 0 && serverLevel.isEmptyBlock(blockPos.above()) && serverLevel.getRawBrightness(blockPos.above(), 0) >= 9){
            this.grow(blockPos, blockState, serverLevel, randomSource);
        }


    }

    @Override
    protected boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return levelReader.getBlockState(blockPos.below()).is(ModTags.BANANA_TREE_PLANTABLE) || levelReader.getBlockState(blockPos.below()).is(ModBlocks.BANANA_TREE_PLANT) || levelReader.getBlockState(blockPos.below()).is(ModBlocks.BUDDING_BANANA_TREE_PLANT) || levelReader.getBlockState(blockPos.below()).is(ModBlocks.CHOPPED_BANANA_TREE_PLANT);
    }

    @Override
    protected float getDestroyProgress(BlockState blockState, Player player, BlockGetter blockGetter, BlockPos blockPos) {
        if (blockGetter.getBlockState(blockPos.below()).is(ModBlocks.BANANA_TREE_PLANT)){
            player.level().setBlock(blockPos.below(), ModBlocks.CHOPPED_BANANA_TREE_PLANT.defaultBlockState().setValue(BananaTreeChoppedPlant.AGE, player.level().getBlockState(blockPos.below()).getValue(BananaTreePlant.AGE)), 3);
        }
        return super.getDestroyProgress(blockState, player, blockGetter, blockPos);
    }

    static {
        AGE = BlockStateProperties.AGE_3;

        THICKEST_TRUNK = Block.box(2.0,0.0,2.0, 14.0, 16.0, 14.0);
        THICK_TRUNK = Block.box(4.0, 0.0, 4.0, 12.0,16.0, 12.0);
        THINNER_TRUNK = Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
        THINNEST_TRUNK = Block.box(7.0, 0.0, 7.0, 9.0, 5.0, 9.0);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return levelReader.getBlockState(blockPos.above()).is(Blocks.AIR) && blockState.getValue(AGE) != 3;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return blockState.getValue(AGE) != 3;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        this.grow(blockPos, blockState, serverLevel, randomSource);
    }

    protected void grow(BlockPos blockPos, BlockState blockState, Level level, RandomSource randomSource){
        BlockPos blockPos2 = blockPos.above();
        int age = blockState.getValue(AGE);
        if (age != 3){
            level.setBlock(blockPos2, this.defaultBlockState().setValue(AGE, randomSource.nextFloat() > 0.75 ? age + 1 : age), 3);

            if (randomSource.nextInt(3) == 0 && level.getBlockState(blockPos).getValue(AGE) == 2 && !level.getBlockState(blockPos.below()).is(ModBlocks.BUDDING_BANANA_TREE_PLANT)){
                level.setBlock(blockPos, ModBlocks.BUDDING_BANANA_TREE_PLANT.defaultBlockState().setValue(BuddingBananaTreePlant.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(randomSource)), 3);
            }
        }
    }
}
