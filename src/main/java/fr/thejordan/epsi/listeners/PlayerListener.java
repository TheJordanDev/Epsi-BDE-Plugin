package fr.thejordan.epsi.listeners;

import fr.thejordan.epsi.Epsi;
import fr.thejordan.epsi.helpers.Keys;
import fr.thejordan.epsi.helpers.MessageFactory;
import fr.thejordan.epsi.object.VanishManager;
import net.kyori.adventure.text.Component;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.persistence.PersistentDataType;

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

        Location deathLocation = player.getLastDeathLocation();
        ItemStack compass = new ItemStack(Material.COMPASS);
        CompassMeta meta = (CompassMeta)compass.getItemMeta();
        meta.displayName(MessageFactory.deathLocation(deathLocation));
        meta.setLodestone(player.getLocation());
        meta.setLodestoneTracked(true);
        meta.getPersistentDataContainer().set(Keys.deathCompass, PersistentDataType.BYTE, (byte)0);
        compass.setItemMeta(meta);
        event.getItemsToKeep().add(compass);

        int level = player.getTotalExperience();
        List<ItemStack> items = event.getDrops();
        event.setShouldDropExperience(false);

        world.getBlockAt(deathLocation).setType(Material.CHEST);
        Chest chest = (Chest) world.getBlockAt(deathLocation);
        chest.getInventory().setContents(items.toArray(new ItemStack[items.size()]));
        chest.getPersistentDataContainer().set(Keys.isDeathChest, PersistentDataType.BYTE, (byte)0);
        chest.getPersistentDataContainer().set(Keys.savedXP, PersistentDataType.INTEGER, level);
    }

    @EventHandler
    public void onDeathChestOpen(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock().getType() != Material.CHEST) return;
        Chest chest = (Chest) event.getClickedBlock();
        if (!chest.getPersistentDataContainer().has(Keys.isDeathChest, PersistentDataType.BYTE)) return;
        int level = chest.getPersistentDataContainer().get(Keys.savedXP, PersistentDataType.INTEGER);
        event.getPlayer().setTotalExperience(event.getPlayer().getTotalExperience()+level);
        chest.getPersistentDataContainer().set(Keys.savedXP, PersistentDataType.INTEGER, 0);
    }

}