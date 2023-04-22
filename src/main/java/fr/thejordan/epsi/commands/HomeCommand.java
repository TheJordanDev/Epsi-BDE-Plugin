package fr.thejordan.epsi.commands;

import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.thejordan.epsi.object.HomeManager;

public class HomeCommand implements CommandExecutor, TabCompleter {

    public void register(JavaPlugin plugin) {
        plugin.getCommand("home").setExecutor(this);
        plugin.getCommand("home").setTabCompleter(this);
        plugin.getCommand("sethome").setExecutor(this);
        plugin.getCommand("sethome").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        switch (label) {
            case "sethome" -> HomeManager.instance().setHome(player);
            case "home" -> HomeManager.instance().tpHome(player);
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }

    
}
