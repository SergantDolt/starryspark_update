package com.gullible.registry;

import com.google.common.collect.ImmutableSet;
import com.gullible.StarrysparkUpdate;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;


public class ModVillagerProfessions {

    public static void init() {}

    public static final ResourceKey<PoiType> WALLET_INSPECTOR_POI_KEY = registerPoiKey("wallet_inspector_poi");
    public static final PoiType WALLET_INSPECTOR_POI = registerPoi("wallet_inspector_poi", ModBlocks.CASH_REGISTER);
    public static final VillagerProfession WALLET_INSPECTOR = registerProfession("wallet_inspector", WALLET_INSPECTOR_POI_KEY);


    public static VillagerProfession registerProfession(String name, ResourceKey<PoiType> type){
        return Registry.register(BuiltInRegistries.VILLAGER_PROFESSION, StarrysparkUpdate.ModLocation(name),
                new VillagerProfession(name, entry -> entry.is(type), entry -> entry.is(type),
                        ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_CARTOGRAPHER));
    }

    private static PoiType registerPoi(String name, Block block){
        return PointOfInterestHelper.register(StarrysparkUpdate.ModLocation(name), 1, 1, block);
    }

    private static ResourceKey<PoiType> registerPoiKey (String name){
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, StarrysparkUpdate.ModLocation(name));
    }
}
