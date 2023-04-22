package fr.thejordan.epsi.commands;

import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnCommand implements CommandExecutor, TabCompleter {

    public void register(JavaPlugin plugin) {
        plugin.getCommand("spawn").setExecutor(this);
        plugin.getCommand("spawn").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        player.teleportAsync(Bukkit.getWorlds().get(0).getSpawnLocation());
        
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }

    
}
