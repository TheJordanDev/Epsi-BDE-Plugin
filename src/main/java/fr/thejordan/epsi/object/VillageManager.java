package fr.thejordan.epsi.object;

import fr.thejordan.epsi.config.Config;
import fr.thejordan.epsi.helpers.Messages;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class VillageManager {

    private static VillageManager instance;
    public static VillageManager instance() { return instance; }

    public VillageManager() {
        instance = this;
    }

    public void setVillage(Player player) {
        Config.instance().setVillage(player.getLocation());
        Messages.VILLAGE_SET.send(player);
    }

    public void tpVillage(Player player) {
        if (Config.instance().getVillage() == null) {
            Messages.NO_VILLAGE_SET.send(player);
            return;
        }
        player.teleportAsync(Config.instance().getVillage().add(0.5,0,0.5));
        Messages.VILLAGE_TELEPORT.send(player);
    }

}
