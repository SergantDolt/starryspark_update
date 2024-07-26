package com.gullible.core;

import com.gullible.StarrysparkUpdate;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class ModTags {
    public static final TagKey<Item> COME_HERE_ABLE = itemTagKey("enchantable/come_here");
    public static final TagKey<Item> WAY_TOO_ATTACHED = itemTagKey("enchantable/attached");
    public static final TagKey<Block> WEAK_TO_VIBRATION = blockTagKey("weak_to_vibration");
    public static final TagKey<Block> BANANA_TREE_PLANTABLE = blockTagKey("banana_tree_plantable_on");

    private static TagKey<Item> itemTagKey(String key) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(StarrysparkUpdate.ID, key));
    }
    private static TagKey<Block> blockTagKey(String key) {

        return TagKey.create(Registries.BLOCK, new ResourceLocation(StarrysparkUpdate.ID, key));
    }
    public static void onInit(){}

}
