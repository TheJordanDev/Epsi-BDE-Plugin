package fr.thejordan.epsi.helpers;

import fr.thejordan.epsi.Epsi;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;
import java.util.UUID;

public class TpaUtils {

    public static void acceptRequest(Player player, UUID from) {
        Epsi.instance().getTpaRequests().get(player.getUniqueId()).remove(from);
        Player target = Bukkit.getPlayer(from);
        if (target != null) {
            player.sendPlainMessage(Messages.YOU_ACCEPTED_REQUEST);

            target.sendPlainMessage(Messages.REQUEST_ACCEPTED(player));
            target.sendPlainMessage(Messages.TELEPORTED_SOON);
            new BukkitRunnable() {
                @Override
                public void run() { target.teleport(player); }
            }.runTaskLater(Epsi.instance(),20*5);
        }

    }

    public static void denyRequest(Player player, UUID from) {
        Epsi.instance().getTpaRequests().get(player.getUniqueId()).remove(from);
        Player target = Bukkit.getPlayer(from);
        if (target != null) {
            player.sendPlainMessage(Messages.YOU_DENIED_REQUEST);
            target.sendPlainMessage(Messages.REQUEST_DENIED(player));
        }
    }

    public static void ignoreRequest(Player player, UUID from) {
        Epsi.instance().getTpaRequests().get(player.getUniqueId()).remove(from);
        Player target = Bukkit.getPlayer(from);
        if (target != null) {
            target.sendPlainMessage(Messages.YOU_IGNORED_REQUEST);
        }
    }

    public static void request(Player player, UUID to) {
        Epsi.instance().getTpaRequests().get(to).add(0,player.getUniqueId());
        Player target = Bukkit.getPlayer(to);
        if (target != null) {
            target.sendMessage(MessageFactory.tpaNotification(player));
        }
    }

    public static int getRandomNumberInRange(int min, int max) {
        if (min >= max) throw new IllegalArgumentException("max must be greater than min");
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
