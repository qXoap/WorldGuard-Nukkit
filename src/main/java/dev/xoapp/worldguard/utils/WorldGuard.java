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

    public static LinkedHashMap<String, Object> stringToMap(String data) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Stack<Map<String, Object>> stack = new Stack<>();

        stack.push(map);

        int i = 1;

        String key = null;
        StringBuilder value = new StringBuilder();

        boolean insideNestedMap = false;

        while (i < data.length() - 1) {
            char c = data.charAt(i);

            i++;

            switch (c) {
                case '=' -> {
                    if (key != null) {
                        continue;
                    }

                    key = value.toString().trim();
                    value.setLength(0);
                }

                case '{' -> {
                    LinkedHashMap<String, Object> nestedMap = new LinkedHashMap<>();

                    stack.peek().put(key, nestedMap);
                    stack.push(nestedMap);

                    key = null;

                    value.setLength(0);
                    insideNestedMap = true;
                }

                case '}' -> {
                    if (!value.isEmpty()) {
                        stack.peek().put(key, value.toString().trim());
                        value.setLength(0);
                    }

                    stack.pop();
                    insideNestedMap = !stack.isEmpty();

                    key = null;
                }

                case ',' -> {
                    if (key != null) {
                        stack.peek().put(key, value.toString().trim());
                        key = null;
                    }

                    value.setLength(0);
                }

                default -> value.append(c);
            }
        }

        if (key != null && !value.isEmpty()) {
            stack.peek().put(key, value.toString().trim());
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