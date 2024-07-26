package com.gullible.mixin;

import com.gullible.core.events.OnDeathEvent;
import com.gullible.registry.ModEnchantments;
import net.fabricmc.fabric.impl.transfer.item.CursorSlotWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import net.minecraft.network.protocol.login.ServerboundKeyPacket;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    @Shadow
    Inventory inventory;

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Redirect(method = "dropEquipment", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;dropAll()V"))
    public void dropNonEnchantedEquipment(Inventory inventory){
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.COME_HERE, itemStack) <= 0){
                inventory.player.drop(itemStack, true, false);
                inventory.setItem(i, ItemStack.EMPTY);
            }
            OnDeathEvent.recordOldInventory(inventory.player.getUUID(), inventory);
        }
    }

    @Inject(method = "drop", at = @At(value = "HEAD"), cancellable = true)
    public void preventDrop(ItemStack itemStack, boolean bl, CallbackInfoReturnable<ItemEntity> callbackInfoReturnable){
        if (this.hasAttachCurse(itemStack)){
            callbackInfoReturnable.setReturnValue(null);
        }
    }

    @Unique
    private boolean hasAttachCurse(ItemStack itemStack){
        return itemStack.getEnchantments().getLevel(ModEnchantments.WAY_TOO_ATTACHED) == 1;
    }

}
