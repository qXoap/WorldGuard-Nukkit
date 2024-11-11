package dev.xoapp.worldguard;

import cn.nukkit.plugin.PluginBase;
import dev.xoapp.worldguard.commands.RegionCommand;
import dev.xoapp.worldguard.factory.RegionFactory;
import dev.xoapp.worldguard.handlers.FlagsHandler;
import dev.xoapp.worldguard.handlers.ProcessHandler;
import dev.xoapp.worldguard.session.SessionFactory;
import lombok.Getter;

public class Loader extends PluginBase {

    @Getter
    private static Loader instance;

    @Override
    public void onEnable() {
        instance = this;

        RegionFactory.initialize();

        getServer().getCommandMap().register("region", new RegionCommand());

        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getServer().getPluginManager().registerEvents(new ProcessHandler(), this);
        getServer().getPluginManager().registerEvents(new FlagsHandler(), this);
    }

    @Override
    public void onDisable() {
        RegionFactory.save();
        SessionFactory.close();
    }
}
