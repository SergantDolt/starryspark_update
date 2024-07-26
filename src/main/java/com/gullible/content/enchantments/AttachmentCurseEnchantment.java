package com.gullible.content.enchantments;

import net.minecraft.world.item.enchantment.Enchantment;

public class AttachmentCurseEnchantment extends Enchantment {
    public AttachmentCurseEnchantment(EnchantmentDefinition enchantmentDefinition) {
        super(enchantmentDefinition);
    }
    @Override
    public boolean isTreasureOnly() {
        return true;
    }
    @Override
    public boolean isCurse() {
        return true;
    }
}
