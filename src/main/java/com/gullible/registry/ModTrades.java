package com.gullible.registry;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Blocks;

import java.util.Optional;

public class ModTrades {
    public static void init(){
        TradeOfferHelper.registerVillagerOffers(ModVillagerProfessions.WALLET_INSPECTOR, 1,
                (factories) -> {
                    factories.add(((entity, randomSource) -> new MerchantOffer(
                            new ItemCost(ModItems.WALLET_LEATHER, 1).withComponents(builder -> builder.expect(ModDataComponents.STORED_CURRENCY, 1)),
                            new ItemStack(ModItems.COMMON_TRADE_CURRENCY),
                            128, 1, 0.01F
                    )));
                    factories.add(((entity, randomSource) -> new MerchantOffer(
                            new ItemCost(ModItems.WALLET_LEATHER, 1).withComponents(builder -> builder.expect(ModDataComponents.STORED_CURRENCY, 5)),
                            new ItemStack(ModItems.BASIC_TRADE_CURRENCY),
                            64, 1, 0.01F
                    )));
                });
        TradeOfferHelper.registerVillagerOffers(ModVillagerProfessions.WALLET_INSPECTOR, 2,
                ((factories) -> factories.add(((entity, randomSource) -> new MerchantOffer(
                        new ItemCost(ModItems.WALLET_LEATHER, 1).withComponents(builder -> builder.expect(ModDataComponents.STORED_CURRENCY, 10)),
                        new ItemStack(ModItems.RARE_TRADE_CURRENCY),
                        32, 1, 0.01F
                )))));
        TradeOfferHelper.registerVillagerOffers(ModVillagerProfessions.WALLET_INSPECTOR, 3,
                (factories) -> {
                    factories.add((entity, randomSource) -> new MerchantOffer(
                            new ItemCost(ModItems.WALLET_LEATHER,1).withComponents(builder -> builder.expect(ModDataComponents.STORED_CURRENCY, 5000)),
                            EnchantedBookItem.createForEnchantment(new EnchantmentInstance(ModEnchantments.WAY_TOO_ATTACHED, 1)),
                            1, 0, 0.01F
                    ));
                    factories.add((entity, randomSource) -> new MerchantOffer(
                            new ItemCost(ModItems.WALLET_LEATHER,1).withComponents(builder -> builder.expect(ModDataComponents.STORED_CURRENCY, 25)),
                            Optional.of(new ItemCost(Items.BLACKSTONE, 1)),
                            new ItemStack(Items.GILDED_BLACKSTONE),
                            1, 0, 0.01F
                    ));
                });
        TradeOfferHelper.registerVillagerOffers(ModVillagerProfessions.WALLET_INSPECTOR, 4,
                (factories) -> {
                    factories.add((entity, randomSource) -> new MerchantOffer(
                            new ItemCost(ModItems.WALLET_LEATHER,1).withComponents(builder -> builder.expect(ModDataComponents.STORED_CURRENCY, 256)),
                            EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.MENDING, 1)),
                            1, 0, 0.0F
                    ));
                });
        TradeOfferHelper.registerVillagerOffers(ModVillagerProfessions.WALLET_INSPECTOR, 5,
                (factories) -> {
                    factories.add((entity, randomSource) -> new MerchantOffer(
                            new ItemCost(ModItems.WALLET_LEATHER,1).withComponents(builder -> builder.expect(ModDataComponents.STORED_CURRENCY, 10000)),
                            EnchantedBookItem.createForEnchantment(new EnchantmentInstance(ModEnchantments.COME_HERE, 1)),
                            1, 0, 0.01F
                    ));
                });
    }
}
