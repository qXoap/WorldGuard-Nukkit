package dev.xoapp.worldguard.process;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import dev.xoapp.worldguard.factory.RegionFactory;
import dev.xoapp.worldguard.items.region.SetFirstPosition;
import dev.xoapp.worldguard.items.region.SetSecondPosition;
import dev.xoapp.worldguard.items.save.SaveProcess;
import dev.xoapp.worldguard.region.Region;
import dev.xoapp.worldguard.session.Session;
import dev.xoapp.worldguard.session.SessionFactory;

public class SetupRegionProcess {

    private final String name;

    private Region baseRegion = null;

    public SetupRegionProcess(String name) {
        this.name = name;
    }

    public void prepare(Session session) {
        Player player = session.getPlayer();
        if (player == null) {
            return;
        }

        Inventory inventory = player.getInventory();

        session.saveInventory();

        baseRegion = new Region(name);

        inventory.setItem(0, new SetFirstPosition());
        inventory.setItem(1, new SetSecondPosition());
        inventory.setItem(8, new SaveProcess());

        player.sendMessage(TextFormat.colorize(
                "&aYou are now in a process of setup &e" + name + "&a region"
        ));
    }

    public void handleInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Item item = event.getItem();
        Block block = event.getBlock();

        if (item == null || block == null) {
            return;
        }

        Session session = SessionFactory.get(player.getName());
        if (session == null) {
            return;
        }

        String compoundName = item.getOrCreateNamedTag().getString("worldGuard");
        if (compoundName == null) {
            return;
        }

        if (baseRegion == null) {
            return;
        }

        switch (compoundName) {
            case "firstPosition" -> {
                baseRegion.setFirstPosition(block.getLocation());
                player.sendMessage(TextFormat.colorize("&aFirst position successfully putted"));
            }

            case "secondPosition" -> {
                baseRegion.setSecondPosition(block.getLocation());
                player.sendMessage(TextFormat.colorize("&aSecond position successfully putted"));
            }

            case "saveProcess" -> saveProcess(session);
        }
    }

    private void saveProcess(Session session) {
        Player player = session.getPlayer();
        if (player == null) {
            return;
        }

        session.returnInventory();

        Region region = RegionFactory.get(name);
        if (region == null) {
            session.setProcess(null);
            player.sendMessage(TextFormat.colorize("&6Error while saving changes"));
            return;
        }

        region.setFirstPosition(baseRegion.getFirstPosition());
        region.setSecondPosition(baseRegion.getSecondPosition());

        player.sendMessage(TextFormat.colorize("&aYou successfully saved the region changes"));
    }
}
