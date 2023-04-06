package fr.thejordan.epsi.commands;

import fr.thejordan.epsi.Epsi;
import fr.thejordan.epsi.helpers.TpaUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class TpaCommand implements CommandExecutor, TabCompleter {

    /*
        /tpa <player>
        /tpa accept (<player>)
        /tpa deny (<player>)
     */

    public void register(JavaPlugin plugin) {
        plugin.getCommand("tpa").setExecutor(this);
        plugin.getCommand("tpa").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("accept")) accept(player, args);
            else if (args[0].equalsIgnoreCase("deny")) deny(player, args);
            else if (args[0].equalsIgnoreCase("ignore")) ignore(player, args);
            else if (Bukkit.getPlayer(args[0]) != null) request(player, args);

        }
        return false;
    }

    private void accept(Player player, String[] args) {
        List<UUID> requests = Epsi.instance().getTpaRequests().get(player.getUniqueId());
        if (args.length == 1) {
            if (requests.size() == 0)
                player.sendPlainMessage("§cAucune demande récente.");
            else if (requests.size() > 1)
                player.sendPlainMessage("§6Veuillez préciser la demande de quel joueur vous voulez accepter.");
            else {
                player.sendPlainMessage("§2Demande accepté.");
                TpaUtils.acceptRequest(player, requests.get(0));
            }
        } else if (args.length == 2) {
            if (requests.size() > 1) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null && requests.contains(target.getUniqueId())) {
                    TpaUtils.acceptRequest(player, target.getUniqueId());
                } else {
                    player.sendPlainMessage("§cLe joueur §6"+args[1]+"§c n'éxiste pas ou ne ta pas envoyer d'inviation !");
                }
            }
        }
    }

    private void deny(Player player, String[] args) {
        List<UUID> requests = Epsi.instance().getTpaRequests().get(player.getUniqueId());
        if (args.length == 1) {
            if (requests.size() == 0)
                player.sendPlainMessage("§cAucune demande récente.");
            else if (requests.size() > 1)
                player.sendPlainMessage("§6Veuillez préciser la demande de quel joueur vous voulez refuser.");
            else {
                player.sendPlainMessage("§2Demande refusée.");
                TpaUtils.denyRequest(player, requests.get(0));
            }
        } else if (args.length == 2) {
            if (requests.size() > 1) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null && requests.contains(target.getUniqueId())) {
                    TpaUtils.denyRequest(player, target.getUniqueId());
                } else {
                    player.sendPlainMessage("§cLe joueur §6"+args[1]+"§c n'éxiste pas ou ne ta pas envoyer d'inviation !");
                }
            }
        }
    }

    private void ignore(Player player, String[] args) {

    }

    private void request(Player player, String[] args) {

    }
    @Override
    public @Nullable List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        return null;
    }
}
