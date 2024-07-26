package com.gullible.mixin;

import com.gullible.registry.ModEnchantments;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public class SlotMixin {
    @Shadow
    Container container;
    @Shadow
    int slot;
    @Inject(method = "mayPickup",at = @At(value = "HEAD"), cancellable = true)
    public void doNotSelectAttachment(Player player, CallbackInfoReturnable<Boolean> callbackInfoReturnable){
        if (this.hasCursedAttachment(container.getItem(slot))){
            callbackInfoReturnable.setReturnValue(true);
        }
    }

    @Unique
    private boolean hasCursedAttachment(ItemStack itemStack){
        return container.getItem(slot).getEnchantments().getLevel(ModEnchantments.WAY_TOO_ATTACHED) == 1;
    }
}
