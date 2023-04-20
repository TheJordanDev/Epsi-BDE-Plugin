package fr.thejordan.epsi.scheduler;

import org.bukkit.scheduler.BukkitRunnable;

import fr.thejordan.epsi.Epsi;
import fr.thejordan.epsi.object.TpaManager;
import fr.thejordan.epsi.object.TpaRequest;

public class TpaExpireScheduler extends BukkitRunnable {

    @Override
    public void run() {
        for (TpaRequest request : TpaManager.instance().getTpaRequests()) {
            if (request.fromNow() >= Epsi.instance().getTpaExpiry()*1000) {
                request.expire();
            }
        }
    }
    
}
