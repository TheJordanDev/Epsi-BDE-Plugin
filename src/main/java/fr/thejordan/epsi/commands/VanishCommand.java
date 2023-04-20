package fr.thejordan.epsi.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.thejordan.epsi.helpers.MessageFactory;
import fr.thejordan.epsi.helpers.Messages;
import fr.thejordan.epsi.helpers.Utils;
import fr.thejordan.epsi.object.VanishManager;

public class VanishCommand implements CommandExecutor, TabCompleter {

    public void register(JavaPlugin plugin) {
        plugin.getCommand("vanish").setExecutor(this);
        plugin.getCommand("vanish").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (args.length == 0) VanishManager.toggleVanish(player, true);
        else if (args.length == 1) {
            String action = args[0].toLowerCase();
            if (action.equals("toggle")) VanishManager.toggleVanish(player, true);
            else if (action.equals("auto")) VanishManager.toggleAutoVanish(player);
            else if (action.equals("help")) player.sendMessage(MessageFactory.vanishHelper());
        } else if (args.length == 2) {
            String action = args[0].toLowerCase();
            Optional<Boolean> status = Utils.stringToBool(args[1]);
            if (status.isEmpty()) { Messages.VALID_BOOL.send(player); return false; }
            if (action.equals("toggle"))
                VanishManager.toggleVanish(player, status.get());
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) return Utils.autocomplete(args[0], Arrays.asList("toggle","auto"));
        else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("toggle")) {
                return Utils.autocomplete(args[0], Arrays.asList("true","false"));
            }
        }
        return Collections.emptyList();
    }
    
}
