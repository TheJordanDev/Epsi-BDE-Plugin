package fr.thejordan.epsi.object;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.thejordan.epsi.Epsi;
import fr.thejordan.epsi.helpers.MessageFactory;
import lombok.Getter;

public class VanishManager {
    
    private static VanishManager instance;
    public static VanishManager instance() { return instance; }

    @Getter private List<UUID> autoVanished = new ArrayList<>();
    @Getter private List<UUID> vanished = new ArrayList<>();

    public VanishManager() {
        instance = this;
    }

    public static boolean isVanished(Player player) { return isVanished(player.getUniqueId()); }
    public static boolean isVanished(UUID uuid) { return instance.vanished.contains(uuid); }

    public static void toggleVanish(Player player, boolean silent) { 
        if (isVanished(player)) instance.unVanish(player, silent);
        else instance.vanish(player, silent);
    }

    public static void toggleAutoVanish(Player player) { 
        if (isVanished(player)) instance.autoUnVanish(player);
        else instance.autoVanish(player);
    }

    public void autoVanish(Player player) {
        autoVanished.add(player.getUniqueId());
    }

    public void autoUnVanish(Player player) {
        autoVanished.remove(player.getUniqueId());
    }

    public static boolean isAutoVanish(Player player) {
        return instance.autoVanished.contains(player.getUniqueId());
    }

    public void unVanish(Player player, boolean silent) {
        vanished.remove(player.getUniqueId());
        Bukkit.getOnlinePlayers().forEach((p)->p.showPlayer(Epsi.instance(), player));
        if (!silent)
            Bukkit.broadcast(MessageFactory.joinMessage(player));
    }

    public void vanish(Player player, boolean silent) {
        vanished.add(player.getUniqueId());
        Bukkit.getOnlinePlayers().forEach((p)->p.hidePlayer(Epsi.instance(), player));
        if (!silent)
            Bukkit.broadcast(MessageFactory.leaveMessage(player));
    }

}
