package fr.thejordan.epsi.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import fr.thejordan.epsi.config.Griefing;
import fr.thejordan.epsi.helpers.Utils;

public class GriefingTimeCheckerScheduler extends BukkitRunnable {

    public static boolean day = false;

    @Override
    public void run() {
        if (!Griefing.instance().enabled) return;
        long time = Bukkit.getWorlds().get(0).getTime();
        if (time >= 13188 && time <= 22812) { //NUIT
            if (day) {
                day = false;
                Utils.toggleGriefing(day);
            }
        } else { //JOUR
            if (!day) {
                day = true;
                Utils.toggleGriefing(day);
            }
        }
    }
    
}
