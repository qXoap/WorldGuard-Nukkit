package dev.xoapp.worldguard;

import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import dev.xoapp.worldguard.commands.RegionCommand;
import dev.xoapp.worldguard.region.RegionFactory;
import dev.xoapp.worldguard.handlers.FlagsHandler;
import dev.xoapp.worldguard.handlers.ProcessHandler;
import dev.xoapp.worldguard.session.SessionFactory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Loader extends PluginBase {

    @Getter
    private static Loader instance;

    @Override
    public void onEnable() {
        instance = this;

        RegionFactory.initialize();

        getServer().getCommandMap().register("region", new RegionCommand());

        Collection<Listener> handlers = List.of(new EventListener(), new ProcessHandler(), new FlagsHandler());
        for (Listener listener : handlers) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    @Override
    public void onDisable() {
        RegionFactory.save();
        SessionFactory.close();
    }
}