package fr.thejordan.epsi.commands;

import fr.thejordan.epsi.helpers.MessageFactory;
import fr.thejordan.epsi.helpers.Messages;
import fr.thejordan.epsi.helpers.Utils;
import fr.thejordan.epsi.object.TpaManager;
import fr.thejordan.epsi.object.TpaRequest;
import fr.thejordan.epsi.object.TpaManager.TpaAction;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
            if (actions.contains(args[0].toLowerCase())) action(player, args);
            else if (Bukkit.getPlayer(args[0]) != null) request(player, args);
        } else {
            player.sendMessage(MessageFactory.tpaHelper());
        }
        return false;
    }

    private void request(Player player, String[] args) {
        if (args.length >= 1) {
            Optional<Player> target = Utils.getPlayer(args[0]);
            if (target.isEmpty()) { Messages.PLAYER_NEVER_SENT_INVITE(player, args[1]).send(player); return;}
            if (!TpaManager.instance().canSend(player)) { Messages.PLEASE_WAIT_FOR_EXPIRY.send(player); return;}
            TpaManager.request(player, target.get());
        } else
            Messages.SPECIFY_PLAYER.send(player);
    }

    private void action(Player player, String[] args) {
        TpaAction tpaAction = TpaAction.which(args[0]);
        TpaRequest request = null;
        if (args.length > 1) {
            Optional<Player> _target = Utils.getPlayer(args[1]);
            if (_target.isEmpty()) { Messages.PLAYER_NEVER_SENT_INVITE(null, args[1]).send(player); return; }
            Optional<TpaRequest> lastRequest = TpaManager.instance().hasReceivedFrom(player, _target.get());
            if (lastRequest.isEmpty()) { Messages.PLAYER_NEVER_SENT_INVITE(_target.get(), "").send(player); return; }
            request = lastRequest.get();
        } else {
            Optional<TpaRequest> lastRequest = TpaManager.instance().getLastReceived(player.getUniqueId());
            if (lastRequest.isEmpty()) { Messages.NO_RECENT_REQUEST.send(player); return; }
            request = lastRequest.get();
        }
        request.action(tpaAction);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        return null;
    }
}
