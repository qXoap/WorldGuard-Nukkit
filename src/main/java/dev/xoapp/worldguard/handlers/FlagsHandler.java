package dev.xoapp.worldguard.handlers;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.*;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityTeleportEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.utils.TextFormat;
import dev.xoapp.worldguard.factory.RegionFactory;
import dev.xoapp.worldguard.flags.ZoneFlag;
import dev.xoapp.worldguard.region.Region;
import dev.xoapp.worldguard.session.*;

public class FlagsHandler implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        Session session = SessionFactory.get(player.getName());
        if (session == null) {
            return;
        }

        Region region = RegionFactory.getByPosition(event.getBlock().getLocation());
        if (region == null) {
            return;
        }

        ZoneFlag flag = region.getFlags().get("blockBreak");
        if (flag == null) {
            return;
        }

        event.setCancelled(!flag.getFlagValue());

        if (!flag.getFlagValue()) {
            player.sendMessage(TextFormat.colorize("&6You cant break blocks on this region"));
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        Session session = SessionFactory.get(player.getName());
        if (session == null) {
            return;
        }

        Region region = RegionFactory.getByPosition(event.getBlock().getLocation());
        if (region == null) {
            return;
        }

        ZoneFlag flag = region.getFlags().get("blockPlace");
        if (flag == null) {
            return;
        }

        event.setCancelled(!flag.getFlagValue());

        if (!flag.getFlagValue()) {
            player.sendMessage(TextFormat.colorize("&6You cant place blocks on this region"));
        }
    }

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();

        Session session = SessionFactory.get(player.getName());
        if (session == null) {
            return;
        }

        Region region = session.getRegion();
        if (region == null) {
            return;
        }

        ZoneFlag flag = region.getFlags().get("playerConsume");
        if (flag == null) {
            return;
        }

        event.setCancelled(!flag.getFlagValue());

        if (!flag.getFlagValue()) {
            player.sendMessage(TextFormat.colorize("&6You cant consume on this region"));
        }
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        Session session = SessionFactory.get(player.getName());
        if (session == null) {
            return;
        }

        Region region = session.getRegion();
        if (region == null) {
            return;
        }

        ZoneFlag flag = region.getFlags().get("playerDrop");
        if (flag == null) {
            return;
        }

        event.setCancelled(!flag.getFlagValue());

        if (!flag.getFlagValue()) {
            player.sendMessage(TextFormat.colorize("&6You cant drop items on this region"));
        }
    }

    @EventHandler
    public void onPlayerFoodLevelChange(PlayerFoodLevelChangeEvent event) {
        Player player = event.getPlayer();

        Session session = SessionFactory.get(player.getName());
        if (session == null) {
            return;
        }

        Region region = session.getRegion();
        if (region == null) {
            return;
        }

        ZoneFlag flag = region.getFlags().get("playerFoodLevelChange");
        if (flag == null) {
            return;
        }

        event.setCancelled(!flag.getFlagValue());

        if (!flag.getFlagValue()) {
            player.sendMessage(TextFormat.colorize("&6You cant break blocks on this region"));
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player player)) {
            return;
        }

        Session session = SessionFactory.get(player.getName());
        if (session == null) {
            return;
        }

        Region region = session.getRegion();
        if (region == null) {
            return;
        }

        ZoneFlag flag = region.getFlags().get("entityDamage");
        if (flag == null) {
            return;
        }

        event.setCancelled(!flag.getFlagValue());
    }

    @EventHandler
    public void onEntityTeleport(EntityTeleportEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player player)) {
            return;
        }

        Session session = SessionFactory.get(player.getName());
        if (session == null) {
            return;
        }

        Region region = session.getRegion();
        if (region == null) {
            return;
        }

        ZoneFlag flag = region.getFlags().get("entityTeleport");
        if (flag == null) {
            return;
        }

        event.setCancelled(!flag.getFlagValue());

        if (!flag.getFlagValue()) {
            player.sendMessage(TextFormat.colorize("&6You cant get teleported on this region"));
        }
    }
}