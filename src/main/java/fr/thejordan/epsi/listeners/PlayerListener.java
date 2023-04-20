package fr.thejordan.epsi.listeners;

import fr.thejordan.epsi.helpers.MessageFactory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        event.joinMessage(MessageFactory.joinMessage(event.getPlayer()));
    }

    @EventHandler
    public void playerLeaveEvent(PlayerQuitEvent event) {
        event.quitMessage(MessageFactory.leaveMessage(event.getPlayer()));
    }

}
