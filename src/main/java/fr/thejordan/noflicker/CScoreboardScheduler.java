package fr.thejordan.noflicker;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CScoreboardScheduler extends BukkitRunnable {

    public boolean running = false;

    public CScoreboardScheduler() { }

    public void run() {
        running = true;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (CScoreboardManager.instance.playersScoreboards.containsKey(player.getUniqueId())) {
                CScoreboardManager.instance.playersScoreboards.get(player.getUniqueId()).refresh();
            }
        }
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
        running = false;
    }
}
