package com.gullible.mixin;

import com.gullible.StarrysparkUpdate;
import com.gullible.registry.ModDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.swing.text.html.Option;
import java.util.Optional;

@Mixin(ItemCost.class)
public class ItemCostMixin {
    @Shadow
    Holder<Item> item;
    @Shadow
    DataComponentPredicate components;

    @Inject(method = "test", at = @At(value = "HEAD"), cancellable = true)
    private void test(ItemStack itemStack, CallbackInfoReturnable<Boolean> callbackInfoReturnable){
        if (itemStack.has(ModDataComponents.STORED_CURRENCY)){
            callbackInfoReturnable.setReturnValue(metAskingPrice(itemStack));
        }
    }
    @Unique
    private boolean metAskingPrice(ItemStack itemStack){
        Optional<? extends Integer> stored_value = itemStack.getComponentsPatch().get(ModDataComponents.STORED_CURRENCY);
        Optional<? extends Integer> price = this.components.asPatch().get(ModDataComponents.STORED_CURRENCY);

        if (stored_value != null && stored_value.isPresent() && price != null && price.isPresent()){
           return stored_value.get() >= price.get();
        } else {
            return false;
        }
    }
}
