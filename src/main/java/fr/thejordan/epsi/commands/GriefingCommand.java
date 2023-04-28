package fr.thejordan.epsi.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.thejordan.epsi.config.Griefing;
import fr.thejordan.epsi.helpers.MessageFactory;
import fr.thejordan.epsi.helpers.Messages;
import fr.thejordan.epsi.helpers.Utils;

public class GriefingCommand implements CommandExecutor, TabCompleter {

    public void register(JavaPlugin plugin) {
        plugin.getCommand("griefing").setExecutor(this);
        plugin.getCommand("griefing").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (args.length == 1 && args[0].equalsIgnoreCase("hideMessage")) {
            if (Griefing.instance().hideMessage.contains(player.getUniqueId()))
                Griefing.instance().hideMessage.remove(player.getUniqueId());
            else
                Griefing.instance().hideMessage.add(player.getUniqueId());
            return false;
        }
        if (!player.hasPermission("epsi.griefing")) return true;
        if (args.length == 1 && args[0].equalsIgnoreCase("toggle")) {
            Griefing.instance().enabled = !Griefing.instance().enabled;
            Messages.GRIEFING_STATE_CHANGE(Griefing.instance().enabled).send(player);
        } else {
            player.sendMessage(MessageFactory.griefingToggleExplain());
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1)
            return Utils.autocomplete(args[0], Arrays.asList("toggle"));
        return Collections.emptyList();
    }
    
}