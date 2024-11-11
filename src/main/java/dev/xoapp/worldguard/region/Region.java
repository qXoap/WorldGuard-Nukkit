package dev.xoapp.worldguard.region;

import cn.nukkit.level.Position;
import dev.xoapp.worldguard.flags.ZoneFlag;
import dev.xoapp.worldguard.library.serializer.Serializer;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Region {

    private final String name;

    @Setter
    @Nullable
    private Position firstPosition;

    @Setter
    @Nullable
    private Position secondPosition;

    private final Map<String, ZoneFlag> flags = new HashMap<>();

    private final List<String> blockedPlayers = new ArrayList<>();

    public Region(String name) {
        this.name = name;
    }

    public boolean isInside(Position position) {
        if (firstPosition == null) {
            return false;
        }

        if (secondPosition == null) {
            return false;
        }

        double playerX = position.getX();
        double playerY = position.getY();
        double playerZ = position.getZ();

        double minX = Math.min(firstPosition.getX(), secondPosition.getX());
        double maxX = Math.max(firstPosition.getX(), secondPosition.getX());

        double minY = Math.min(firstPosition.getY(), secondPosition.getY());
        double maxY = Math.max(firstPosition.getY(), secondPosition.getY());

        double minZ = Math.min(firstPosition.getZ(), secondPosition.getZ());
        double maxZ = Math.max(firstPosition.getZ(), secondPosition.getZ());

        return playerX >= minX &&
                playerX <= maxX &&
                playerY >= minY &&
                playerY <= maxY &&
                playerZ >= minZ &&
                playerZ <= maxZ;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("firstPosition", Serializer.posToString(firstPosition));
        map.put("secondPosition", Serializer.posToString(secondPosition));

        map.put("flags", serializeFlags());
        map.put("blockedPlayers", blockedPlayers);

        return map;
    }

    private Map<String, Boolean> serializeFlags() {
        Map<String, Boolean> map = new HashMap<>();

        flags.forEach((name, flag) -> map.put(name, flag.getFlagValue()));

        return map;
    }
}