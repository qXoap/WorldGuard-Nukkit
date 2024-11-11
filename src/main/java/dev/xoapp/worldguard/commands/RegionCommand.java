package dev.xoapp.worldguard.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import dev.xoapp.worldguard.forms.FormManager;

public class RegionCommand extends Command {

    public RegionCommand() {
        super("region");

        setAliases(new String[]{"wg", "worldguard"});

        setPermission("worldguard.command");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        FormManager.openOptions(sender.asPlayer());
        return true;
    }
}
