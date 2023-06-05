package fr.thejordan.epsi.commands;

import java.util.Collections;
import java.util.List;

import fr.thejordan.epsi.object.VillageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.thejordan.epsi.object.HomeManager;

public class VillageCommand implements CommandExecutor, TabCompleter {

    public void register(JavaPlugin plugin) {
        plugin.getCommand("village").setExecutor(this);
        plugin.getCommand("village").setTabCompleter(this);
        plugin.getCommand("setvillage").setExecutor(this);
        plugin.getCommand("setvillage").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        switch (label) {
            case "setvillage" -> VillageManager.instance().setVillage(player);
            case "village" -> VillageManager.instance().tpVillage(player);
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }

}
