package fr.thejordan.epsi.object;

import org.bukkit.Location;

import fr.thejordan.epsi.Epsi;
import fr.thejordan.epsi.Epsi.MainConfig;
import lombok.Getter;

public class Config {
    
    private static Config instance;
    public static Config instance() { return instance; }

    private final MainConfig file;

    @Getter private Integer tpaExpiry;
    @Getter private Location spawnCenter;
    @Getter private Integer rtpRay;

    public Config() {
        instance = this;
        file = new MainConfig(Epsi.instance());
    }

}
