package dev.xoapp.worldguard.utils;

import dev.xoapp.worldguard.flags.ZoneFlag;
import dev.xoapp.worldguard.flags.block.BlockBreakFlag;
import dev.xoapp.worldguard.flags.block.BlockPlaceFlag;
import dev.xoapp.worldguard.flags.entity.EntityDamageFlag;
import dev.xoapp.worldguard.flags.entity.EntityTeleportFlag;
import dev.xoapp.worldguard.flags.player.PlayerConsumeFlag;
import dev.xoapp.worldguard.flags.player.PlayerDropFlag;
import dev.xoapp.worldguard.flags.player.PlayerFoodLevelChange;

import java.util.*;

public class WorldGuard {

    public static Map<String, ZoneFlag> getDefaultFlags() {
        Map<String, ZoneFlag> map = new HashMap<>();

        map.put("blockBreak", new BlockBreakFlag(false));
        map.put("blockPlace", new BlockPlaceFlag(false));

        map.put("entityDamage", new EntityDamageFlag(false));
        map.put("entityTeleport", new EntityTeleportFlag(true));

        map.put("playerConsume", new PlayerConsumeFlag(true));
        map.put("playerDrop", new PlayerDropFlag(true));
        map.put("playerFoodLevelChange", new PlayerFoodLevelChange(false));

        return map;
    }

    public static Map<String, Object> stringToMap(String data) {
        Map<String, Object> map = new HashMap<>();

        data = data.substring(1, data.length() - 1);
        if (data.isEmpty()) {
            return map;
        }

        String[] pairs = data.split(", ");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            map.put(keyValue[0], keyValue[1]);
        }

        return map;
    }

    public static List<Object> stringToList(String data) {
        data = data.substring(1, data.length() - 1);
        if (data.isEmpty()) {
            return null;
        }

        String[] pairs = data.split(", ");
        return new ArrayList<>(Arrays.asList(pairs));
    }
}