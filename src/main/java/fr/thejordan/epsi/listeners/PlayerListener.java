package fr.thejordan.epsi.listeners;

import fr.thejordan.epsi.Epsi;
import fr.thejordan.epsi.helpers.MessageFactory;
import fr.thejordan.epsi.object.VanishManager;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
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
            psl.setNumPlayers(psl.getNumPlayers()-VanishManager.instance().getVanished().size());
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

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        if (event.getKeepInventory() == true) return;
        if (event.getDrops().size() == 0) return;

        List<ItemStack> items = List.copyOf(event.getDrops());
        event.getDrops().clear();

        List<ItemStack> firstChestI = new ArrayList<>();
        List<ItemStack> otherChestI = new ArrayList<>();

        if (items.size() <=27) firstChestI = items.subList(0, items.size());
        else {
            firstChestI = items.subList(0, 27);
            otherChestI = items.subList(27, items.size());
        }

        Location deathLocation = event.getEntity().getLocation();
        world.getBlockAt(deathLocation).setType(Material.CHEST);
        Chest inventoryChest = (Chest) world.getBlockAt(deathLocation).getState();
        inventoryChest.getInventory().setContents(firstChestI.toArray(new ItemStack[firstChestI.size()]));

        if (otherChestI.size() > 0) {
            Location otherChestlocation = event.getEntity().getLocation().clone().add(0, 1, 0);
            world.getBlockAt(otherChestlocation).setType(Material.CHEST);
            Chest otherChest = (Chest) world.getBlockAt(otherChestlocation).getState();
            otherChest.getInventory().setContents(otherChestI.toArray(new ItemStack[otherChestI.size()]));
        }
    }

    @EventHandler
    public void onRespawn(PlayerPostRespawnEvent event) {
        Location deathLocation = event.getPlayer().getLastDeathLocation();
        ItemStack compass = new ItemStack(Material.COMPASS);
        CompassMeta meta = (CompassMeta)compass.getItemMeta();
        meta.displayName(MessageFactory.deathLocation(deathLocation));
        meta.setLodestone(deathLocation);
        meta.setLodestoneTracked(false);
        compass.setItemMeta(meta);
        event.getPlayer().getInventory().addItem(compass);
    }

}