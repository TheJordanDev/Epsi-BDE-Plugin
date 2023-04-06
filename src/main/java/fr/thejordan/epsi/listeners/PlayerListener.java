package fr.thejordan.epsi.listeners;

import fr.thejordan.epsi.Epsi;
import fr.thejordan.epsi.helpers.MessageFactory;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class PlayerListener implements Listener {

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        event.joinMessage(MessageFactory.joinMessage(event.getPlayer()));
        Epsi.instance().getTpaRequests().put(event.getPlayer().getUniqueId(),new ArrayList<>());
    }

    @EventHandler
    public void playerLeaveEvent(PlayerQuitEvent event) {
        event.quitMessage(MessageFactory.leaveMessage(event.getPlayer()));
        Epsi.instance().getTpaRequests().remove(event.getPlayer().getUniqueId());
    }

}
