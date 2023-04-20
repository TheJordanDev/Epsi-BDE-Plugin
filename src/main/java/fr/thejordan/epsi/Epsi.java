package fr.thejordan.epsi;

import fr.thejordan.epsi.commands.RTpCommand;
import fr.thejordan.epsi.commands.TpaCommand;
import fr.thejordan.epsi.listeners.PlayerListener;
import fr.thejordan.epsi.object.TpaManager;
import fr.thejordan.epsi.scheduler.TpaExpireScheduler;
import lombok.Getter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public final class Epsi extends JavaPlugin {

    private static Epsi instance;
    public static Epsi instance() { return instance; }

    @Getter private TpaManager tpaManager;

    @Getter private TpaExpireScheduler expireScheduler;

    @Getter private Integer tpaExpiry;
    @Getter private Location spawnCenter;
    @Getter private Integer rtpRay;

    @Override
    public void onEnable() {
        instance = this;
        this.tpaManager = new TpaManager();
        this.expireScheduler = new TpaExpireScheduler();
        this.expireScheduler.runTaskTimer(this, 0L, 20L);
        new TpaCommand().register(this);
        new RTpCommand().register(this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        spawnCenter = new Location(
                Bukkit.getWorld(getConfig().getString("spawnCenter.world","world")),
                getConfig().getInt("spawnCenter.x",0),
                getConfig().getInt("spawnCenter.y",0),
                getConfig().getInt("spawnCenter.z",0)
        );
        rtpRay = getConfig().getInt("rtp.ray",500);
        tpaExpiry = getConfig().getInt("tpa.expire", 30);
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {

    }
}
