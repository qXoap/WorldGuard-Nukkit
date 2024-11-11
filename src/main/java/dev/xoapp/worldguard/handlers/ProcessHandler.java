package dev.xoapp.worldguard.handlers;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import dev.xoapp.worldguard.session.Session;
import dev.xoapp.worldguard.session.SessionFactory;

public class ProcessHandler implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Session session = SessionFactory.get(player.getName());
        if (session == null) {
            return;
        }

        if (session.getProcess() == null) {
            return;
        }

        session.getProcess().handleInteract(event);
    }
}
