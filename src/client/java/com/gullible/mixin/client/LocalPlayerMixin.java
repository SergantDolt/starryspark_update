package com.gullible.mixin.client;

import com.gullible.registry.ModEnchantments;
import com.mojang.authlib.GameProfile;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(LocalPlayer.class)
public class LocalPlayerMixin extends AbstractClientPlayer {
    public LocalPlayerMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @Inject(method = "drop", at = @At(value = "HEAD"), cancellable = true)
    public void dropItemMixin(boolean bl, CallbackInfoReturnable<Boolean> callbackInfoReturnable){
        ItemStack itemStack = this.getInventory().getSelected();
        if (itemStack.getEnchantments().getLevel(ModEnchantments.WAY_TOO_ATTACHED) == 1){
            callbackInfoReturnable.setReturnValue(false);
        }
    }

}
