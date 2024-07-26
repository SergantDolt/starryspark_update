package com.gullible.content.blocks.boulders;

import com.gullible.content.entities.boulders.MegaDamageBananaBoulder;
import com.gullible.registry.ModEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.swing.text.html.parser.Entity;

public class MegaDamageBananaBoulderBlock extends Block {
    public static final BooleanProperty IS_FALLING = BooleanProperty.create("is_falling");

    public MegaDamageBananaBoulderBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(getStateDefinition().any().setValue(IS_FALLING, false));
    }


    @Override
    protected boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return Block.canSupportCenter(levelReader, blockPos.above(), Direction.DOWN) && !levelReader.isWaterAt(blockPos);
    }

    @Override
    protected BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (!this.canSurvive(blockState, levelAccessor, blockPos)){
            levelAccessor.scheduleTick(blockPos, this, 0);
        }
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }

    @Override
    protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (!blockState.canSurvive(serverLevel, blockPos)){
            this.summonBoulder(serverLevel, blockPos);
            serverLevel.destroyBlock(blockPos, true);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(IS_FALLING);
    }

    @Override
    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return Block.box(4, 0, 4, 12, 8, 12);
    }
    private void summonBoulder(Level level, BlockPos pos){
        MegaDamageBananaBoulder boulder = ModEntities.MEGA_DAMAGE_BANANA_BOULDER.create(level);
        if (boulder != null){
            boulder.moveTo(pos.getCenter());
            level.addFreshEntity(boulder);
        }
    }
}
