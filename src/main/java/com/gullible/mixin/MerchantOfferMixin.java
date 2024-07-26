package com.gullible.mixin;

import com.gullible.registry.ModDataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MerchantOffer.class)
public abstract class MerchantOfferMixin {
    @Shadow
    abstract ItemStack getBaseCostA();
    @Shadow
    abstract ItemStack getCostB();

    @Inject(method = "take", at = @At(value = "HEAD"), cancellable = true)
    private void take(ItemStack itemStack, ItemStack itemStack2, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (itemStack.has(ModDataComponents.STORED_CURRENCY) || itemStack2.has(ModDataComponents.STORED_CURRENCY)) {
            if (!this.getCostB().isEmpty()){
                int walletB = itemStack2.getOrDefault(ModDataComponents.STORED_CURRENCY,1);
                int priceB = this.getCostB().getOrDefault(ModDataComponents.STORED_CURRENCY, 1);

                itemStack2.set(ModDataComponents.STORED_CURRENCY, walletB - priceB);
                callbackInfoReturnable.setReturnValue(walletB - priceB >= 0);
            }
            int walletA = itemStack.getOrDefault(ModDataComponents.STORED_CURRENCY,1);
            int priceA = this.getBaseCostA().getOrDefault(ModDataComponents.STORED_CURRENCY, 1);

            itemStack.set(ModDataComponents.STORED_CURRENCY, walletA - priceA);
            callbackInfoReturnable.setReturnValue(walletA - priceA >= 0);
        }
    }

}