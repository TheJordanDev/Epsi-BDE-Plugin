package fr.thejordan.epsi.helpers;

import fr.thejordan.epsi.Epsi;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TpaUtils {

    public static void acceptRequest(Player player, UUID request) {
        Epsi.instance().getTpaRequests().get(player.getUniqueId()).remove(request);
    }

    public static void denyRequest(Player player, UUID request) {
        Epsi.instance().getTpaRequests().get(player.getUniqueId()).remove(request);
    }

    public static void ignoreRequest(Player player, UUID request) {
        Epsi.instance().getTpaRequests().get(player.getUniqueId()).remove(request);
    }

}
