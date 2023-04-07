package fr.thejordan.epsi;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import fr.thejordan.epsi.commands.RTpCommand;
import fr.thejordan.epsi.commands.TpaCommand;
import fr.thejordan.epsi.listeners.PlayerListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Epsi extends JavaPlugin {

    private static Epsi instance;
    public static Epsi instance() { return instance; }

    @Getter
    private Location spawnCenter;

    @Getter
    private Integer rtpRay;

    @Getter
    private final Map<UUID, List<UUID>> tpaRequests = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!tpaRequests.containsKey(player.getUniqueId()))
                tpaRequests.put(player.getUniqueId(),new ArrayList<>());
        }
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
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {

    }
}
