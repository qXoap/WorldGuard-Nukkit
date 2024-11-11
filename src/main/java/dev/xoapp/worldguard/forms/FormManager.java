package dev.xoapp.worldguard.forms;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.Element;
import cn.nukkit.utils.TextFormat;
import com.denzelcode.form.element.Button;
import com.denzelcode.form.element.Dropdown;
import com.denzelcode.form.element.Input;
import com.denzelcode.form.element.Toggle;
import com.denzelcode.form.window.CustomWindowForm;
import com.denzelcode.form.window.SimpleWindowForm;
import dev.xoapp.worldguard.factory.RegionFactory;
import dev.xoapp.worldguard.flags.ZoneFlag;
import dev.xoapp.worldguard.process.SetupRegionProcess;
import dev.xoapp.worldguard.region.Region;
import dev.xoapp.worldguard.session.Session;
import dev.xoapp.worldguard.session.SessionFactory;
import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

public class FormManager {

    public static void openOptions(Player player) {
        SimpleWindowForm form = new SimpleWindowForm("World Guard");

        form.addButton("create_region", "Create Region");
        form.addButton("delete_region", "Delete Region");
        form.addButton("edit_region", "Edit Region");

        form.addHandler((e) -> {
            Button button = e.getButton();
            if (button == null) {
                return;
            }

            String buttonName = button.getName();

            switch (buttonName) {
                case "create_region" -> openCreationForm(player);
                case "delete_region" -> {
                    if (RegionFactory.getRegions().isEmpty()) {
                        player.sendMessage(TextFormat.colorize("&cNo regions registered"));
                        return;
                    }

                    openDeletionForm(player);
                }
                case "edit_region" -> {
                    if (RegionFactory.getRegions().isEmpty()) {
                        player.sendMessage(TextFormat.colorize("&cNo regions registered"));
                        return;
                    }

                    openEditRegionSelector(player);
                }
            }
        });

        form.sendTo(player);
    }

    private static void openCreationForm(Player player) {
        CustomWindowForm form = new CustomWindowForm("Create Region");

        form.addInput("regionName", "Region Name");

        form.addHandler((e) -> {
            Input input = e.getForm().getElement("regionName");
            if (input == null) {
                return;
            }

            if (input.getValue().isEmpty()) {
                return;
            }

            Region region = RegionFactory.get(input.getValue());
            if (region != null) {
                player.sendMessage(TextFormat.colorize("&6This region already exists"));
                return;
            }

            RegionFactory.create(new Region(input.getValue()));

            player.sendMessage(TextFormat.colorize(
                    "&aYou successfully created the region &e" + input.getValue()
            ));
        });

        form.sendTo(player);
    }

    private static void openDeletionForm(Player player) {
        CustomWindowForm form = new CustomWindowForm("Delete Region");

        List<String> regionNames = RegionFactory.getRegions().keySet().stream().toList();
        form.addDropdown("regionName", "Select Region", regionNames);

        form.addHandler((e) -> {
            Dropdown dropdown = e.getForm().getElement("regionName");
            if (dropdown == null) {
                return;
            }

            String regionName = regionNames.get(dropdown.getValue());
            Region region = RegionFactory.get(regionName);

            if (region == null) {
                player.sendMessage(TextFormat.colorize("&6This region does not exist"));
                return;
            }

            RegionFactory.delete(region.getName());
            player.sendMessage(TextFormat.colorize("&aDeleted region &e" + region.getName()));
        });

        form.sendTo(player);
    }

    private static void openEditRegionSelector(Player player) {
        CustomWindowForm form = new CustomWindowForm("Edit Region Selector");

        List<String> regionNames = RegionFactory.getRegions().keySet().stream().toList();
        form.addDropdown("regionName", "Select Region", regionNames);

        form.addHandler((e) -> {
            Dropdown dropdown = e.getForm().getElement("regionName");
            if (dropdown == null) {
                return;
            }

            String regionName = regionNames.get(dropdown.getValue());
            Region region = RegionFactory.get(regionName);

            if (region == null) {
                player.sendMessage(TextFormat.colorize("&6This region does not exist"));
                return;
            }

            openRegionOptions(player, region);
        });

        form.sendTo(player);
    }

    private static void openRegionOptions(Player player, Region region) {
        SimpleWindowForm form = new SimpleWindowForm("Region Options");

        form.addButton("modify_flags", "Modify Region Flags");
        form.addButton("add_block_player", "Add Block Player");
        form.addButton("remove_block_player", "Remove Block Player");
        form.addButton("modify_positions", "Modify Region Positions");

        form.addHandler((e) -> {
            Button button = e.getButton();
            if (button == null) {
                return;
            }

            String buttonName = button.getName();

            switch (buttonName) {
                case "modify_flags" -> modifyFlags(player, region);
                case "add_block_player" -> addBlockPlayer(player, region);
                case "remove_block_player" -> {
                    if (region.getBlockedPlayers().isEmpty()) {
                        player.sendMessage(TextFormat.colorize("&aThis region has no blocked players"));
                        return;
                    }

                    removeBlockPlayer(player, region);
                }
                case "modify_positions" -> {
                    Session session = SessionFactory.get(player.getName());
                    if (session == null) {
                        return;
                    }

                    SetupRegionProcess process = new SetupRegionProcess(region.getName());

                    process.prepare(session);
                    session.setProcess(process);
                }
            }
        });

        form.sendTo(player);
    }

    private static void modifyFlags(Player player, Region region) {
        CustomWindowForm form = new CustomWindowForm("Modify Region Flags");

        region.getFlags().forEach(
                (name, flag) -> form.addToggle(name, flag.getFlagBio(), flag.getFlagValue())
        );

        form.addHandler((e) -> {
            List<Element> elements = e.getForm().getElements();

            for (Element element : elements) {
                if (!(element instanceof Toggle)) {
                    continue;
                }

                String flagName = ((Toggle) element).getName();
                ZoneFlag flag = region.getFlags().get(flagName);

                if (flag == null) {
                    continue;
                }

                flag.setFlagValue(((Toggle) element).getValue());
            }

            player.sendMessage(TextFormat.colorize(
                    "&aYou successfully modified the region flags"
            ));
        });

        form.sendTo(player);
    }

    private static void addBlockPlayer(Player player, Region region) {
        CustomWindowForm form = new CustomWindowForm("Add Blocked Player");

        form.addInput("blockedPlayer", "Player Name");

        form.addHandler((e) -> {
            Input input = e.getForm().getElement("blockedPlayer");
            if (input == null) {
                return;
            }

            if (input.getValue().isEmpty()) {
                return;
            }

            String playerName = input.getValue();
            Player iPlayer = Server.getInstance().getPlayer(playerName);

            if (iPlayer == null) {
                player.sendMessage(TextFormat.colorize("&aThis player is not online"));
                return;
            }

            region.getBlockedPlayers().add(playerName);

            player.sendMessage(TextFormat.colorize(
                    "&aYou successfully blocked the player &e" + iPlayer.getName() + "&a from this region"
            ));
        });

        form.sendTo(player);
    }

    private static void removeBlockPlayer(Player player, Region region) {
        CustomWindowForm form = new CustomWindowForm("Remove Blocked Player");

        form.addDropdown("blockedPlayer", "Player Name", region.getBlockedPlayers());

        form.addHandler((e) -> {
            Dropdown dropdown = e.getForm().getElement("blockedPlayer");
            if (dropdown == null) {
                return;
            }

            String playerName = region.getBlockedPlayers().get(dropdown.getValue());
            if (playerName == null) {
                player.sendMessage(TextFormat.colorize("&6This player is not blocked"));
                return;
            }

            region.getBlockedPlayers().remove(dropdown.getValue());

            player.sendMessage(TextFormat.colorize(
                    "&aYou successfully removed the player &e" + playerName + "&a from blocked players"
            ));
        });

        form.sendTo(player);
    }
}