package com.gullible.mixin;

import com.gullible.registry.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "appendHoverText", at = @At(value = "HEAD"))
    public void appendWorthLore(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag, CallbackInfo callbackInfo){
        if (itemStack.has(ModDataComponents.WORTH)){
            Integer c = itemStack.get(ModDataComponents.WORTH);
            if (c != null){
                list.add(Component.literal("✴").append(String.valueOf(c)).withStyle(ChatFormatting.GOLD));
            }
        }
    }

}
