package com.gullible.content.blocks;

import com.gullible.core.config.ConfigUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CashRegister extends Block {
    public static final DirectionProperty FACING;
    private static final VoxelShape SHAPE_TOP_PLATE;
    private static final VoxelShape SHAPE_WEST;
    private static final VoxelShape SHAPE_NORTH;
    private static final VoxelShape SHAPE_EAST;
    private static final VoxelShape SHAPE_SOUTH;
    private static final VoxelShape SHAPE_POST;
    private static final VoxelShape SHAPE_COLLISION;
    public CashRegister(Properties properties) {
        super(properties);
        this.registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.EAST));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (level.isClientSide){
            List<String> insults = ConfigUtil.config.gadgets.cashRegister.insults;
            if (!insults.isEmpty()){
                InsultPlayer(level.random.nextInt(0, insults.size()), player);
            }
        }
        return ItemInteractionResult.SUCCESS;
    }

    private void InsultPlayer(int rand, Player player){
        player.displayClientMessage(Component.literal("\"").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GREEN).append("").append(Component.translatable(ConfigUtil.config.gadgets.cashRegister.insults.get(rand))).append("\"").withStyle(ChatFormatting.GREEN), true);
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState blockState) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return switch (blockState.getValue(FACING)) {
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case EAST -> SHAPE_EAST;
            case WEST -> SHAPE_WEST;
            default -> SHAPE_POST;
        };
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE_COLLISION;
    }

    @Override
    protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
        return false;
    }
    
    @Override
    protected BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;

        SHAPE_POST = Block.box(2.0, 0.0, 6.0, 14.0, 7.0, 11.0);
        SHAPE_TOP_PLATE = Block.box(2.0, 7.0, 2.0, 14.0, 8.0, 14.0);
        SHAPE_COLLISION = Shapes.or(SHAPE_POST, SHAPE_TOP_PLATE);
        SHAPE_WEST = Shapes.or(Block.box(5.0, 0.0, 2.0, 11.0, 7.0, 14.0), new VoxelShape[]{Block.box(5.0, 5.0, 1.0, 8.0, 11.0, 15.0), Block.box(2.0, 3.0, 1.0, 5.0, 9.0, 15.0), Block.box(8.0, 7.0, 1.0, 11.0, 13.0, 15.0), Block.box(11.0, 9.0, 1.0, 14.0, 15.0, 15.0)});
        SHAPE_NORTH = Shapes.or(Block.box(2.0, 0.0, 5.0, 14.0, 7.0, 10.0), new VoxelShape[]{Block.box(1.0, 5.0, 5.0, 15.0, 11.0, 8.0), Block.box(1.0, 3.0, 2.0, 15.0, 9.0, 5.0), Block.box(1.0, 7.0, 8.0, 15.0, 13.0, 11.0), Block.box(1.0, 9.0, 11.0, 15.0, 15.0, 14.0)});
        SHAPE_EAST = Shapes.or(Block.box(5.0, 0.0, 2.0, 11.0, 7.0, 14.0), new VoxelShape[]{Block.box(8.0, 5.0, 1.0, 11.0, 11.0, 15.0), Block.box(11.0, 3.0, 1.0, 14.0, 9.0, 15.0), Block.box(5.0, 7.0, 1.0, 8.0, 13.0, 15),Block.box(2.0, 9.0, 1.0, 5.0, 15.0, 15.0)});
        SHAPE_SOUTH = Shapes.or(Block.box(2.0, 0.0, 5.0, 14.0, 7.0, 10.0), new VoxelShape[]{Block.box(1.0, 5.0, 8.0, 15.0, 11.0, 11.0), Block.box(1.0, 3.0, 11.0, 15.0, 9.0, 14.0), Block.box(1.0, 7.0, 5.0, 15.0, 13.0, 8.0), Block.box(1.0, 9.0, 2.0, 15.0, 15.0, 5.0)});
    }
}
