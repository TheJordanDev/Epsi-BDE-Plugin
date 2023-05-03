package fr.thejordan.noflicker;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CScoreboardManager implements Listener {

    public static CScoreboardManager instance;

    public final CScoreboardScheduler scheduler;
    public final Map<UUID, CScoreboard> playersScheduler = new HashMap<>();

    public CScoreboardManager(JavaPlugin plugin) {
        instance = this;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
        this.scheduler = new CScoreboardScheduler();
        scheduler.runTaskTimer(plugin,0L,5L);
    }

    public <T extends CScoreboard> void send(T scoreboard) {
        scoreboard.player.setScoreboard(scoreboard.board());
        playersScheduler.put(scoreboard.player.getUniqueId(),scoreboard);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        playersScheduler.remove(player.getUniqueId());
    }

}
