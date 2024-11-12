package dev.xoapp.worldguard.data;

import cn.nukkit.utils.Config;
import dev.xoapp.worldguard.Loader;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executors;

public class DataCreator {

    private final Config config;

    public DataCreator(String path) {
        File file = new File(Loader.getInstance().getDataFolder() + "/" + path);

        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        config = new Config(file, Config.JSON);
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
