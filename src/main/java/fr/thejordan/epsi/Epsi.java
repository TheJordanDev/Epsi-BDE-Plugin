package fr.thejordan.epsi;

import fr.thejordan.epsi.commands.RTpCommand;
import fr.thejordan.epsi.commands.TpaCommand;
import fr.thejordan.epsi.commands.VanishCommand;
import fr.thejordan.epsi.helpers.Utils;
import fr.thejordan.epsi.listeners.PlayerListener;
import fr.thejordan.epsi.object.TpaManager;
import fr.thejordan.epsi.object.VanishManager;
import fr.thejordan.epsi.scheduler.TpaExpireScheduler;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public final class Epsi extends JavaPlugin {

    private static Epsi instance;
    public static Epsi instance() { return instance; }

    @Getter private TpaManager tpaManager;
    @Getter private VanishManager vanishManager;

    @Getter private TpaExpireScheduler expireScheduler;

    @Getter private Integer tpaExpiry;
    @Getter private Location spawnCenter;
    @Getter private Integer rtpRay;

    @Override
    public void onEnable() {
        instance = this;
        
        this.tpaManager = new TpaManager();
        this.vanishManager = new VanishManager();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getPersistentDataContainer().has(new NamespacedKey(this, "isVanished"))) {
                VanishManager.instance().vanish(player, true);
                player.getPersistentDataContainer().remove(new NamespacedKey(this, "isVanished"));
            }
        }
        loadConfig();
        this.expireScheduler = new TpaExpireScheduler();
        this.expireScheduler.runTaskTimer(this, 0L, 20L);
        
        new TpaCommand().register(this);
        new RTpCommand().register(this);
        new VanishCommand().register(this);
        
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public void loadConfig() {
        spawnCenter = new Location(
            Bukkit.getWorld(getConfig().getString("spawnCenter.world","world")),
            getConfig().getInt("spawnCenter.x",0),
            getConfig().getInt("spawnCenter.y",0),
            getConfig().getInt("spawnCenter.z",0)
        );
        rtpRay = getConfig().getInt("rtp.ray",500);
        tpaExpiry = getConfig().getInt("tpa.expire", 30);
        if (getConfig().isList("autoVanish")) {
            List<UUID> uuids = getConfig().getStringList("autoVanish")
                .stream().filter(Utils::isValidUUID).map((su)->UUID.fromString(su)).toList();
            VanishManager.instance().getAutoVanished().addAll(uuids);
        }
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        for (UUID uuid : VanishManager.instance().getVanished()) {
            Player player = Bukkit.getPlayer(uuid);
            player.getPersistentDataContainer().set(new NamespacedKey(this, "isVanished"), PersistentDataType.BYTE, (byte)0);
        }
        getConfig().set("autoVanish", VanishManager.instance().getAutoVanished().stream().map((u)->u.toString()));
        saveConfig();
    }
}
