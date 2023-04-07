package fr.thejordan.epsi.commands;

import fr.thejordan.epsi.Epsi;
import fr.thejordan.epsi.helpers.MessageFactory;
import fr.thejordan.epsi.helpers.Messages;
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

import java.util.Arrays;
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
            List<String> actions = Arrays.asList("accept","deny","ignore");
            if (actions.contains(args[0].toLowerCase())) acceptDenyIgnore(player, args);
            else if (Bukkit.getPlayer(args[0]) != null) request(player, args);
        } else {
            player.sendMessage(MessageFactory.tpaHelper());
        }
        return false;
    }

    private void acceptDenyIgnore(Player player, String[] args) {
        List<UUID> requests = Epsi.instance().getTpaRequests().get(player.getUniqueId());
        Player target = null;

        if (args.length == 2) target = Bukkit.getPlayer(args[1]);
        else {
            if (requests.size() >= 1) target = Bukkit.getPlayer(requests.get(0));
            else player.sendPlainMessage(Messages.NO_RECENT_REQUEST);
        }

        if (target == null || !requests.contains(target.getUniqueId()))
            player.sendPlainMessage(Messages.PLAYER_NEVER_SENT_INVITE(target,args[1]));
        if (target != null) action(player, target.getUniqueId(), args[0].toLowerCase());
    }

    private void request(Player player, String[] args) {
        if (args.length >= 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                List<UUID> requests = Epsi.instance().getTpaRequests().get(target.getUniqueId());
                if (requests.contains(player.getUniqueId()))
                    player.sendPlainMessage(Messages.ALREADY_ASKED(player));
                else
                    TpaUtils.request(player,target.getUniqueId());
            } else
                player.sendPlainMessage(Messages.PLAYER_NEVER_SENT_INVITE(null,args[0]));
        } else
            player.sendPlainMessage(Messages.SPECIFY_PLAYER);
    }

    private void action(Player player, UUID target, String action) {
        switch (action.toLowerCase()) {
            case "accept" -> TpaUtils.acceptRequest(player, target);
            case "deny" -> TpaUtils.denyRequest(player, target);
            case "ignore" -> TpaUtils.ignoreRequest(player, target);
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        return null;
    }
}
