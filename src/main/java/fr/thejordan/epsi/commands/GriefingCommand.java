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

import fr.thejordan.epsi.helpers.MessageFactory;
import fr.thejordan.epsi.helpers.Utils;

public class GriefingCommand implements CommandExecutor, TabCompleter {

    public void register(JavaPlugin plugin) {
        plugin.getCommand("griefing").setExecutor(this);
        plugin.getCommand("griefing").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (args.length < 2) {
            player.sendMessage(MessageFactory.griefingToggleExplain());
            return false;
        } else if (args[0].equalsIgnoreCase("toggle")) {
            if (args[1].equalsIgnoreCase("messages"))
                System.out.println("");
            else if (args[1].equalsIgnoreCase("gamerule"))
                System.out.println("");

        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1)
            return Utils.autocomplete(args[0], Arrays.asList("toggle"));
        else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("toggle"))
                return Utils.autocomplete(args[1], Arrays.asList("message","gamerule"));
        }
        return Collections.emptyList();
    }
    
}