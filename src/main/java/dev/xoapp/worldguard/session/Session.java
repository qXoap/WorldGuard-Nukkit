package dev.xoapp.worldguard.session;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.item.Item;
import dev.xoapp.worldguard.process.SetupRegionProcess;
import dev.xoapp.worldguard.region.Region;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class Session {

    private final String name;

    @Nullable
    private Region region = null;

    private SetupRegionProcess process = null;

    private Map<Integer, Item> inventoryContents = new HashMap<>();

    public Session(String name) {
        this.name = name;
    }

    public Player getPlayer() {
        return Server.getInstance().getPlayerExact(name);
    }

    public void saveInventory() {
        Player player = getPlayer();
        if (player == null) {
            return;
        }

        setInventoryContents(player.getInventory().getContents());
    }

    public void returnInventory() {
        Player player = getPlayer();
        if (player == null) {
            return;
        }

        player.getInventory().setContents(getInventoryContents());
    }
}
