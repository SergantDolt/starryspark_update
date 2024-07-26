package com.gullible.content.items;
import com.gullible.registry.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class WalletItem extends Item {
    public WalletItem(Properties properties) {
        super(properties);
    }
    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        Integer c = itemStack.get(ModDataComponents.STORED_CURRENCY);
        if (c != null){
            list.add(Component.translatable("item.starryspark_update.wallet_amount").append(CommonComponents.SPACE).withStyle(ChatFormatting.BLUE));
            list.add(Component.literal("✴").append(String.valueOf(c)).withStyle(ChatFormatting.GOLD));
        }
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack itemStack, Slot slot, ClickAction clickAction, Player player) {
        if (clickAction != ClickAction.SECONDARY){
            return false;
        } else {
            ItemStack itemStack2 = slot.getItem();
            Integer w = itemStack2.get(ModDataComponents.WORTH);
            if (w == null){
                return false;
            } else {

                Integer c = itemStack.get(ModDataComponents.STORED_CURRENCY);
                assert c != null;

                w *= itemStack2.getCount();
                itemStack2.shrink(itemStack2.getCount());

                itemStack.set(ModDataComponents.STORED_CURRENCY, c + w);
                return true;
            }
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack itemStack, ItemStack itemStack2, Slot slot, ClickAction clickAction, Player player, SlotAccess slotAccess) {
        if (clickAction == ClickAction.SECONDARY && slot.allowModification(player)){
            Integer w = itemStack2.get(ModDataComponents.WORTH);
            if (w == null){
                return false;
            } else {
                Integer c = itemStack.get(ModDataComponents.STORED_CURRENCY);
                assert c != null;

                itemStack2.shrink(1);

                itemStack.set(ModDataComponents.STORED_CURRENCY, c + w);
                return true;
            }
        } else {
            return false;
        }
    }
}
