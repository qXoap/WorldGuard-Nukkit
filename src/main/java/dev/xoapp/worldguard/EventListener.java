package dev.xoapp.worldguard;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.*;
import dev.xoapp.worldguard.factory.RegionFactory;
import dev.xoapp.worldguard.process.SetupRegionProcess;
import dev.xoapp.worldguard.region.Region;
import dev.xoapp.worldguard.session.Session;
import dev.xoapp.worldguard.session.SessionFactory;

public class EventListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        SessionFactory.register(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Session session = SessionFactory.get(player.getName());
        if (session == null) {
            return;
        }

        SetupRegionProcess process = session.getProcess();
        if (process != null) {
            session.returnInventory();
        }

        SessionFactory.delete(player.getName());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        Session session = SessionFactory.get(player.getName());
        if (session == null) {
            return;
        }

        session.setRegion(RegionFactory.getByPosition(player.getPosition()));
    }
}