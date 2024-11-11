package dev.xoapp.worldguard.data;

import cn.nukkit.utils.Config;
import dev.xoapp.worldguard.Loader;

import java.util.Map;

public class DataCreator {

    private final Config config;

    public DataCreator(String path) {
        config = new Config(Loader.getInstance().getDataFolder() + path, Config.JSON);
    }

    public void set(String key, Object value) {
        config.set(key, value);
        config.save();
    }

    public Object get(String key) {
        return config.get(key);
    }

    public void delete(String key) {
        config.remove(key);
        config.save();
    }

    public Map<String, Object> getSavedData() {
        return config.getAll();
    }
}
