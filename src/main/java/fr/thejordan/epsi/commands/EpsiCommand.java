package fr.thejordan.epsi.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.kyori.adventure.text.Component;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.thejordan.epsi.Epsi;
import fr.thejordan.epsi.config.Config;
import fr.thejordan.epsi.helpers.Utils;

public class EpsiCommand implements CommandExecutor, TabCompleter {

    public void register(JavaPlugin plugin) {
        plugin.getCommand("epsi").setExecutor(this);
        plugin.getCommand("epsi").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ((sender instanceof ConsoleCommandSender) ||
                ((sender instanceof Player player) && player.hasPermission("epsi.reload"))) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                Config.instance(Epsi.instance().getConfiguration().load());
                sender.sendMessage(Component.text("Config Reload avec succès, de rien Lulu <3"));
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) return Utils.autocomplete(args[0], Arrays.asList("reload"));
        return Collections.emptyList();
    }
    
}
