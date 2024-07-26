package com.gullible.rendering;

import com.gullible.content.items.WalletItem;
import com.gullible.registry.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.world.item.component.DyedItemColor;

@Environment(EnvType.CLIENT)
public class ModItemColors {
    public ModItemColors(){}

    public static void onInit(){
        ColorProviderRegistry.ITEM.register(((itemStack, i) -> i ==  0 ?  DyedItemColor.getOrDefault(itemStack, DyedItemColor.LEATHER_COLOR) : -1), ModItems.WALLET_LEATHER);
    }
}
