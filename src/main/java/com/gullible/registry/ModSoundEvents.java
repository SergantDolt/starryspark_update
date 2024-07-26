package com.gullible.registry;

import com.gullible.StarrysparkUpdate;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

public class ModSoundEvents {

    public static SoundEvent ITEM_MEGAPHONE = registerSound("megaphone", 3);
    public static SoundEvent ITEM_MEGAPHONE_EASTER_EGG = registerSound("megaphone_easter_egg", 5);


    static SoundEvent registerSound(String id, float range){
        SoundEvent soundEvent = SoundEvent.createFixedRangeEvent(StarrysparkUpdate.ModLocation(id), range);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, StarrysparkUpdate.ModLocation(id), soundEvent);
    }

    public static void onInit(){}
}
