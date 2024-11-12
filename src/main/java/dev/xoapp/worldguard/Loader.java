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

public class Loader extends PluginBase {

    @Getter
    private static Loader instance;

    @Override
    public void onEnable() {
        instance = this;

        RegionFactory.initialize();

        getServer().getCommandMap().register("region", new RegionCommand());

        for (Listener listener : getHandlers()) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    private Collection<Listener> getHandlers() {
        return new ArrayList<>(){{
            add(new EventListener());
            add(new ProcessHandler());
            add(new FlagsHandler());
        }};
    }

    @Override
    public void onDisable() {
        RegionFactory.save();
        SessionFactory.close();
    }
}