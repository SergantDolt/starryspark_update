package com.gullible.registry;

import com.gullible.StarrysparkUpdate;
import com.gullible.content.blocks.*;
import com.gullible.content.blocks.boulders.ExplosiveBananaBoulderBlock;
import com.gullible.content.blocks.boulders.MegaDamageBananaBoulderBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {

    public static Block CASH_REGISTER = registerBlock("cash_register", new CashRegister(BlockBehaviour.Properties.of().sound(SoundType.METAL)), true);
    public static Block BANANA_TREE_PLANT = registerBlock("banana_tree_plant", new BananaTreePlant(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()), false);
    public static Block BANANA_TREE_SAPLING = registerBlock("banana_tree_sapling", new BananaTreeSaplingBlock(BlockBehaviour.Properties.of().noCollission()), true);
    public static Block BUDDING_BANANA_TREE_PLANT = registerBlock("budding_banana_tree_plant", new BuddingBananaTreePlant(BlockBehaviour.Properties.of()), false);
    public static Block CHOPPED_BANANA_TREE_PLANT = registerBlock("chopped_banana_tree_plant", new BananaTreeChoppedPlant(BlockBehaviour.Properties.of()),false);
    public static Block MEGA_DAMAGE_BANANA_BOULDER  = registerBlock("damage_banana_boulder", new MegaDamageBananaBoulderBlock(BlockBehaviour.Properties.of().noCollission()), true);
    public static Block EXPLOSIVE_BANANA_BOULDER = registerBlock("explosive_banana_boulder", new ExplosiveBananaBoulderBlock(BlockBehaviour.Properties.of().noCollission()), true);

    public static void init(){}
    static <T extends Block> T registerBlock(String name, T newBlock, boolean canBeItem){
        if (canBeItem) {
            ModItems.registerItem(name, new BlockItem(newBlock, new Item.Properties()));
        }
        return registerBlock(name, newBlock);
    }
    static <T extends Block> T registerBlock(String name, T newBlock){
        return Registry.register(BuiltInRegistries.BLOCK, StarrysparkUpdate.ModLocation(name), newBlock);
    }

}
