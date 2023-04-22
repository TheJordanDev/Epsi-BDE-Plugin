package fr.thejordan.epsi.object;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import fr.thejordan.epsi.helpers.Keys;
import fr.thejordan.epsi.helpers.Messages;
import fr.thejordan.epsi.helpers.Utils;
import lombok.Getter;

public class HomeManager {
    
    private static HomeManager instance;
    public static HomeManager instance() { return instance; }

    @Getter
    private final Map<UUID, Location> homes = new HashMap<>();

    public HomeManager() {
        instance = this;
    }

    public static void saveHome(Player player) {
        player.getPersistentDataContainer().set(Keys.homeLocation, PersistentDataType.STRING, Utils.locationToString( player.getLocation()));
    }

    public static Optional<Location> loadHome(Player player) {
        if (!player.getPersistentDataContainer().has(Keys.homeLocation, PersistentDataType.STRING)) return Optional.empty();
        String _loc = player.getPersistentDataContainer().get(Keys.homeLocation, PersistentDataType.STRING);
        return Optional.of(Utils.stringToLocation(_loc));
        //return Optional.ofNullable(getHomes().getOrDefault(player.getUniqueId(), null));
    }

    public void setHome(Player player) {
        saveHome(player);
        Messages.HOME_SET.send(player);
    }

    public void tpHome(Player player) {
        Optional<Location> loc = loadHome(player);
        if (loc.isEmpty()) {
            Messages.NO_HOME_SET.send(player);
            return;
        }
        Messages.HOME_TELEPORT.send(player);
        player.teleportAsync(loc.get());
    }

}
