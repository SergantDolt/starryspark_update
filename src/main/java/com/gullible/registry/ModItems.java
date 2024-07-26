package com.gullible.registry;

import com.gullible.StarrysparkUpdate;
import com.gullible.content.items.Megaphone;
import com.gullible.content.items.WalletItem;
import net.minecraft.core.Registry;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.block.Block;

public class ModItems {
    public static final Item WALLET_LEATHER = registerItem("wallet_leather", new WalletItem(new Item.Properties().component(ModDataComponents.STORED_CURRENCY, 0).stacksTo(1).component(DataComponents.DYED_COLOR, new DyedItemColor(DyedItemColor.LEATHER_COLOR, false))));
    public static final Item MEGAPHONE = registerItem("megaphone", new Megaphone(new Item.Properties().stacksTo(1)));
    public static final Item BANANA = registerItem("banana", new Item(new Item.Properties()));
    public static final Item GOLDEN_BANANA = registerItem("golden_banana", new Item(new Item.Properties().component(ModDataComponents.WORTH, 100)));

    public static final Item COMMON_TRADE_CURRENCY = registerItem("very_common_star_sample", new Item(new Item.Properties().component(ModDataComponents.WORTH, 1)));
    public static final Item BASIC_TRADE_CURRENCY = registerItem("slightly_dubious_star_fragment", new Item(new Item.Properties().component(ModDataComponents.WORTH, 5)));
    public static final Item RARE_TRADE_CURRENCY = registerItem("dubious_star_shard", new Item(new Item.Properties().component(ModDataComponents.WORTH, 10)));
    public static final Item LEGENDARY_TRADE_CURRENCY = registerItem("gestalts_star", new Item(new Item.Properties().component(ModDataComponents.WORTH, 20)));

    static <T extends Item> T registerItem(String name, T newItem){
         return Registry.register(BuiltInRegistries.ITEM, StarrysparkUpdate.ModLocation(name), newItem);
    }

    public static void init(){};
}
