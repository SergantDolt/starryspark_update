package com.gullible.registry;

import com.gullible.StarrysparkUpdate;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;

import net.minecraft.util.ExtraCodecs;
import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final DataComponentType<Integer> STORED_CURRENCY = register("wallet_currency", (builder ->
            builder.persistent(ExtraCodecs.intRange(0, Integer.MAX_VALUE)).networkSynchronized(ByteBufCodecs.INT)));
    public static final DataComponentType<Integer> WORTH = register("worth", builder ->
            builder.persistent(ExtraCodecs.intRange(1, Integer.MAX_VALUE)).networkSynchronized(ByteBufCodecs.INT));
    public static final DataComponentType<Integer> PLASTIC_UPGRADE_AMOUNT = register("plastic_upgrade_amount", builder ->
            builder.persistent(ExtraCodecs.intRange(1, 5)).networkSynchronized(ByteBufCodecs.INT));
    private static <T> DataComponentType<T> register(String string, UnaryOperator<DataComponentType.Builder<T>> unaryOperator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, StarrysparkUpdate.ModLocation(string), unaryOperator.apply(DataComponentType.builder()).build());
    }

    public static void init(){};
}
