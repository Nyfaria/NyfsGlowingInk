package com.nyfaria.nyfsglow.init;

import com.nyfaria.nyfsglow.NyfsGlow;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemInit {

    public static final Item RED_DYE_ITEM = new DyeItem(DyeColor.RED ,new FabricItemSettings().group(ItemGroup.MISC));


    public static void init(){
        Registry.register(Registry.ITEM,new Identifier(NyfsGlow.MOD_ID,"red_glow_dye"), RED_DYE_ITEM);
    }


}
