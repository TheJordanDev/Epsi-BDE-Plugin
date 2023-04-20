package fr.thejordan.epsi.listeners;

import fr.thejordan.epsi.Epsi;
import fr.thejordan.epsi.helpers.MessageFactory;
import fr.thejordan.epsi.object.VanishManager;
import net.kyori.adventure.text.Component;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (VanishManager.isAutoVanish(player))
            VanishManager.instance().vanish(player, true);

        if (VanishManager.isVanished(player))
            event.joinMessage(Component.empty());
        else
            event.joinMessage(MessageFactory.joinMessage(player));
        
        if (player.isOp()) return;
        VanishManager.instance().getVanished().forEach(
            (vU)->player.hidePlayer(Epsi.instance(),Bukkit.getPlayer(vU))
        );
    }

    @EventHandler
    public void playerLeaveEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (VanishManager.isVanished(player)) {
            event.quitMessage(Component.empty());
            VanishManager.instance().unVanish(player, true);
        } else
            event.quitMessage(MessageFactory.leaveMessage(event.getPlayer()));
    }
    
    @EventHandler
    public void serverPinEvent(ServerListPingEvent event) {
        if (event instanceof PaperServerListPingEvent psl) {
            psl.getPlayerSample().removeIf((pp)->VanishManager.isVanished(pp.getId()));
        }
    }

    @EventHandler
    public void tabComplete(PlayerCommandSendEvent event) {
        event.getCommands().removeIf((s) -> { 
            return 
                s.contains("epsi:") || 
                (!event.getPlayer().hasPermission("epsi.vanish") && s.contains("vanish")); 
        });
    }


}