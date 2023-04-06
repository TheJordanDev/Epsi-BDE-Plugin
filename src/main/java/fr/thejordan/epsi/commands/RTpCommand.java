package fr.thejordan.epsi.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RTpCommand implements CommandExecutor, TabCompleter {

    public void register(JavaPlugin plugin) {
        plugin.getCommand("rtp").setExecutor(this);
        plugin.getCommand("rtp").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        return null;
    }
}
