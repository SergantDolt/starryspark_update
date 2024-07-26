package com.gullible.registry;

import com.gullible.StarrysparkUpdate;
import com.gullible.content.entities.boulders.ExplosiveBananaBoulder;
import com.gullible.content.entities.boulders.MegaDamageBananaBoulder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {

    public static void onInit() {}

    public static final EntityType<ExplosiveBananaBoulder> EXPLOSIVE_BANANA_BOULDER = registerEntity("explosive_banana_boulder", ExplosiveBananaBoulder::new, MobCategory.MISC, 0.2F,0.2F, 0xFFFFFF, 0xFFFFFF);
    public static final EntityType<MegaDamageBananaBoulder> MEGA_DAMAGE_BANANA_BOULDER = registerEntity("damage_banana_boulder", MegaDamageBananaBoulder::new, MobCategory.MISC, 0.2F,0.2F, 0xFFFFFF, 0xFFFFFF);
    static <T extends Entity> EntityType<T> registerEntity(String name, EntityType.EntityFactory<T> newEntity, MobCategory category, float width, float height, int primaryEggColor, int secondaryEggColor){
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, StarrysparkUpdate.ModLocation(name),
                EntityType.Builder.of(newEntity, category).sized(width, height).build(name));
    }
}
