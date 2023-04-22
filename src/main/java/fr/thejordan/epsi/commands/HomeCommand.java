package fr.thejordan.epsi.commands;

import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import fr.thejordan.epsi.helpers.Keys;
import fr.thejordan.epsi.helpers.Utils;

public class HomeCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        switch (label) {
            case "sethome" -> setHome(player);
            case "home" -> home(player);
        }
        return false;
    }

    private void home(Player player) {

    }

    private void setHome(Player player) {
        String location = Utils.locationToString(player.getLocation());
        player.getPersistentDataContainer().set(Keys.homeLocation, PersistentDataType.STRING, location);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }

    
}
