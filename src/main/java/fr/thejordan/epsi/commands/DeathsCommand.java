package fr.thejordan.epsi.commands;

import java.util.*;
import java.util.stream.Collectors;

import fr.thejordan.epsi.helpers.MessageFactory;
import fr.thejordan.epsi.object.DeathStat;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.thejordan.epsi.Epsi;
import fr.thejordan.epsi.config.Config;
import fr.thejordan.epsi.helpers.Utils;

public class DeathsCommand implements CommandExecutor, TabCompleter {

    public void register(JavaPlugin plugin) {
        plugin.getCommand("topdeath").setExecutor(this);
        plugin.getCommand("topdeath").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int MAX_PAGE = (int) Math.ceil((double) Bukkit.getOfflinePlayers().length/5);
        if (args.length == 0) {
            List<DeathStat> players = getTopPage(1);
            sender.sendMessage(MessageFactory.deathTop(players, 1, MAX_PAGE));
        } else if (args.length == 1) {
            int wanted = Utils.intFromString(args[0]);
            int page = (wanted < 1) ? 1 : (wanted > MAX_PAGE) ? MAX_PAGE : wanted;
            List<DeathStat> players = getTopPage(page);
            sender.sendMessage(MessageFactory.deathTop(players, page, MAX_PAGE));
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }

    private List<DeathStat> getTopPage(int page) {
        return Arrays.stream(Bukkit.getOfflinePlayers())
                .map((off)-> new DeathStat(off.getName(),off.getStatistic(Statistic.DEATHS)))
                .sorted((a,b)->b.deaths - a.deaths)
                .skip((page-1) * 5L)
                .limit(5)
                .toList();
    }

}
