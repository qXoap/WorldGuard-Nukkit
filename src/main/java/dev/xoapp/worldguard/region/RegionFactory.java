package dev.xoapp.worldguard.region;

import cn.nukkit.level.Position;
import cn.nukkit.utils.TextFormat;
import dev.xoapp.worldguard.Loader;
import dev.xoapp.worldguard.data.DataCreator;
import dev.xoapp.worldguard.library.serializer.Serializer;
import dev.xoapp.worldguard.utils.WorldGuard;
import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class RegionFactory {

    @Getter
    private static final Map<String, Region> regions = new HashMap<>();

    private static DataCreator dataCreator = null;

    public static void initialize() {
        dataCreator = new DataCreator("regions.json");

        Map<String, Object> savedData = dataCreator.getSavedData();
        savedData.forEach((key, value) -> {
            Region region = new Region(key);

            LinkedHashMap<String, Object> data = WorldGuard.stringToMap(value.toString());
            LinkedHashMap<String, Object> flagData = (LinkedHashMap<String, Object>) data.get("flags");

            Position firstPosition = Serializer.stringToPos(data.get("firstPosition").toString());
            Position secondPosition = Serializer.stringToPos(data.get("secondPosition").toString());

            region.setFirstPosition(firstPosition);
            region.setSecondPosition(secondPosition);

            WorldGuard.getDefaultFlags().forEach((name, flag) -> region.getFlags().put(name, flag));

            flagData.forEach((name, flagValue) -> region.getFlags()
                    .get(name)
                    .setFlagValue(Boolean.parseBoolean(
                            flagValue.toString()
                    )));

            regions.put(region.getName(), region);
        });

        Loader.getInstance().getLogger().info(TextFormat.colorize(
                "&aSuccessfully loaded &e" + regions.size() + "&a Regions"
        ));
    }

    public static void create(Region region) {
        WorldGuard.getDefaultFlags().forEach(
                (key, flag) -> region.getFlags().put(key, flag)
        );

        regions.put(region.getName(), region);
    }

    public static Region get(String name) {
        return regions.get(name);
    }

    public static void delete(String name) {
        regions.remove(name);
        dataCreator.delete(name);
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