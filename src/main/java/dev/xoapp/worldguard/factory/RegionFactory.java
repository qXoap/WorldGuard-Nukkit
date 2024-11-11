package dev.xoapp.worldguard.factory;

import cn.nukkit.level.Position;
import dev.xoapp.worldguard.data.DataCreator;
import dev.xoapp.worldguard.region.Region;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class RegionFactory {

    @Getter
    private static final Map<String, Region> regions = new HashMap<>();

    private static DataCreator dataCreator = null;

    public static void initialize() {
        dataCreator = new DataCreator("regions.json");

        Map<String, Object> savedData = dataCreator.getSavedData();
    }

    public static void create(Region region) {
        regions.put(region.getName(), region);
    }

    public static Region get(String name) {
        return regions.get(name);
    }

    public static void delete(String name) {
        regions.remove(name);
    }

    public static Region getByPosition(Position position) {
        AtomicReference<Region> findRegion = new AtomicReference<>();

        regions.forEach((name, region) -> {
            if (!region.isInside(position)) {
                return;
            }

            findRegion.set(region);
        });

        return findRegion.get();
    }

    public static void save() {
        regions.forEach((key, region) -> dataCreator.set(key, region.toMap()));
    }
}
