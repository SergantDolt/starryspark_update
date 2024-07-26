package com.gullible.registry;

import com.gullible.StarrysparkUpdate;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeMenu {
    public static final CreativeModeTab CREATIVE_MODE_TAB = FabricItemGroup.builder()
            .title(Component.translatable("item.creative.tab.name"))
            .icon(() -> new ItemStack(ModItems.BASIC_TRADE_CURRENCY))
            .displayItems(((itemDisplayParameters, output) -> {
                output.accept(ModItems.BASIC_TRADE_CURRENCY);
                output.accept(ModItems.COMMON_TRADE_CURRENCY);
                output.accept(ModItems.RARE_TRADE_CURRENCY);
                output.accept(ModItems.LEGENDARY_TRADE_CURRENCY);
                output.accept(ModItems.GOLDEN_BANANA);
                output.accept(ModItems.BANANA);
                output.accept(ModItems.MEGAPHONE);
                output.accept(ModItems.WALLET_LEATHER);
                output.accept(ModBlocks.CASH_REGISTER);
                output.accept(ModBlocks.BANANA_TREE_SAPLING);
                output.accept(ModBlocks.EXPLOSIVE_BANANA_BOULDER);
                output.accept(ModBlocks.MEGA_DAMAGE_BANANA_BOULDER);
            }))
            .build();
    public static void onInit(){
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, StarrysparkUpdate.ModLocation("starryspark_update_tab"), CREATIVE_MODE_TAB);
    }
}
