package com.gullible.core.config;

import com.gullible.StarrysparkUpdate;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

import java.util.ArrayList;
import java.util.List;

@Config(name = StarrysparkUpdate.ID)
public class CommonConfig extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("functional_gadgets")
    @ConfigEntry.Gui.TransitiveObject
    public FunctionalGadgets gadgets = new FunctionalGadgets();

    @Config(name = "functional_gadgets")
    public static class FunctionalGadgets implements ConfigData {
        @ConfigEntry.Gui.CollapsibleObject
        public CashRegisterConfig cashRegister = new CashRegisterConfig();
        @ConfigEntry.Gui.CollapsibleObject
        public MegaphoneConfig megaphone = new MegaphoneConfig();
        @Config(name = "cash_register")
        public static class CashRegisterConfig {
            @ConfigEntry.Gui.RequiresRestart
            public List<String> insults = new ArrayList<>();
        }
        //Megaphone Config
        @Config(name = "megaphone")
        public static class MegaphoneConfig {
            @ConfigEntry.Gui.RequiresRestart
            public boolean canShatterBlocks = true;
            @ConfigEntry.Gui.RequiresRestart
            public int megaphoneRange = 10;
        }
    }

}
