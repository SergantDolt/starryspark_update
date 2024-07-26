package com.gullible.registry;

import com.gullible.StarrysparkUpdate;
import com.gullible.content.enchantments.AttachmentCurseEnchantment;
import com.gullible.content.enchantments.ComeHereEnchantment;
import com.gullible.core.ModTags;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Optional;

public class ModEnchantments {
    public static final ComeHereEnchantment COME_HERE = registerEnchantment("come_here", new ComeHereEnchantment(
            new Enchantment.EnchantmentDefinition(ModTags.COME_HERE_ABLE, Optional.of(ModTags.COME_HERE_ABLE),
                    1, 1, new Enchantment.Cost(0, 0), new Enchantment.Cost(0, 0),
                    0, FeatureFlags.DEFAULT_FLAGS, EquipmentSlot.values())
    ));

    public static final AttachmentCurseEnchantment WAY_TOO_ATTACHED = registerEnchantment("attachment", new AttachmentCurseEnchantment(
            new Enchantment.EnchantmentDefinition(ModTags.WAY_TOO_ATTACHED, Optional.of(ModTags.WAY_TOO_ATTACHED),
                    1, 1, new Enchantment.Cost(0, 0), new Enchantment.Cost(0, 0),
                    0, FeatureFlags.DEFAULT_FLAGS, EquipmentSlot.values())
    ));

    static <T extends Enchantment> T registerEnchantment(String name, T newEnchantment){
        return Registry.register(BuiltInRegistries.ENCHANTMENT, StarrysparkUpdate.ModLocation(name), newEnchantment);
    }
    public static void init(){}
}
