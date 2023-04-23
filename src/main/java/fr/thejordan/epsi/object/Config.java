package fr.thejordan.epsi.object;

import org.bukkit.Location;

import lombok.Getter;
import lombok.Setter;

public class Config {
    
    private static Config instance;
    public static Config instance() { return instance; }
    public static void instance(Config edit) { 
        instance.setTpaExpiry(edit.tpaExpiry); 
        instance.setSpawnCenter(edit.spawnCenter); 
        instance.setRtpRay(edit.rtpRay); 
    }

    @Getter @Setter private Integer tpaExpiry;
    @Getter @Setter private Location spawnCenter;
    @Getter @Setter private Integer rtpRay;

    public Config() {
        instance = this;
    }

}
