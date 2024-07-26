package com.gullible.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class ModMenus {

    private static <T extends AbstractContainerMenu> MenuType<T> register(String name, MenuType.MenuSupplier<T> menuSupplier){
        return Registry.register(BuiltInRegistries.MENU, name, new MenuType<>(menuSupplier, FeatureFlags.DEFAULT_FLAGS));
    }
}
