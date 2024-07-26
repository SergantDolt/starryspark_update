package com.gullible;

import com.gullible.core.ModTags;
import com.gullible.core.events.OnDeathEvent;
import com.gullible.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StarrysparkUpdate implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("starryspark_update");
	public static final String ID = "starryspark_update";

	public static ResourceLocation ModLocation(String name){
		return new ResourceLocation(ID, name);
	}


	@Override
	public void onInitialize() {
		ModDataComponents.init();
		ModTags.onInit();
		ModSoundEvents.onInit();
		ModEnchantments.init();
		ModItems.init();
		ModBlocks.init();
		ModCreativeMenu.onInit();
		ModVillagerProfessions.init();
		ModTrades.init();
		ModEntities.onInit();
		OnDeathEvent.onInit();

		LOGGER.info("Hello Fabric world!");
	}

}