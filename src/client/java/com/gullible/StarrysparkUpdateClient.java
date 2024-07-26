package com.gullible;
import com.gullible.core.config.CommonConfig;
import com.gullible.registry.ModRenderers;
import com.gullible.rendering.ModItemColors;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;


public class StarrysparkUpdateClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		ModItemColors.onInit();
		ModRenderers.onInit();
	}
}