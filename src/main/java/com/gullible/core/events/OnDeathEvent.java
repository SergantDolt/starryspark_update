package com.gullible.core.events;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public interface OnDeathEvent {
    HashMap<UUID, Inventory> deadPlayerInventories = new HashMap<>();
    static void onInit(){
        ServerPlayerEvents.AFTER_RESPAWN.register(((oldPlayer, newPlayer, alive) -> {
            Inventory inv = deadPlayerInventories.get(newPlayer.getUUID());
            if (inv == null){
                return;
            }
            for (int i = 0; i < inv.getContainerSize(); i++) {
                ItemStack itemStack = inv.getItem(i);
                newPlayer.getInventory().add(i, itemStack);
            }
            deadPlayerInventories.remove(oldPlayer.getUUID(), inv);
        }));
    }

    static void recordOldInventory(UUID owner, Inventory inventory){
        deadPlayerInventories.put(owner, inventory);
    }
}
