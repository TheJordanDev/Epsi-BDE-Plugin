package fr.thejordan.epsi.object;

import org.bukkit.Location;

import lombok.Getter;
import lombok.Setter;

public class Config {
    
    private static Config instance;
    public static Config instance() { return instance; }

    @Getter @Setter private Integer tpaExpiry;
    @Getter @Setter private Location spawnCenter;
    @Getter @Setter private Integer rtpRay;

    public Config() {
        instance = this;
    }

}
