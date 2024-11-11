package dev.xoapp.worldguard.items;

import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;

public class CustomItem extends Item {

    public CustomItem(String itemID, String customName, String compoundName) {
        super(itemID);

        getOrCreateNamedTag().putString("worldGuard", compoundName);

        setCustomName(TextFormat.colorize(customName));
    }
}
