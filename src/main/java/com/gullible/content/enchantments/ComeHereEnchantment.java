package com.gullible.content.enchantments;


import net.minecraft.world.item.enchantment.Enchantment;

public class ComeHereEnchantment extends Enchantment {
    public ComeHereEnchantment(EnchantmentDefinition definition) {
        super(definition);
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }
}
