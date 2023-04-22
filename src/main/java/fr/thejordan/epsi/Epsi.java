package fr.thejordan.epsi;

import fr.thejordan.epsi.commands.HomeCommand;
import fr.thejordan.epsi.commands.RTpCommand;
import fr.thejordan.epsi.commands.SpawnCommand;
import fr.thejordan.epsi.commands.TpaCommand;
import fr.thejordan.epsi.commands.VanishCommand;
import fr.thejordan.epsi.config.MainConfig;
import fr.thejordan.epsi.helpers.Keys;
import fr.thejordan.epsi.listeners.PlayerListener;
import fr.thejordan.epsi.object.Config;
import fr.thejordan.epsi.object.HomeManager;
import fr.thejordan.epsi.object.TpaManager;
import fr.thejordan.epsi.object.VanishManager;
import fr.thejordan.epsi.scheduler.TpaExpireScheduler;
import lombok.Getter;

import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public final class Epsi extends JavaPlugin {

    private static Epsi instance;
    public static Epsi instance() { return instance; }

    public static void log(Object msg) { instance.getLogger().log(Level.INFO, msg.toString()); }
    public static void wrn(Object msg) { instance.getLogger().log(Level.WARNING, msg.toString()); }
    public static void err(Object msg) { instance.getLogger().log(Level.SEVERE, msg.toString()); }

    @Getter private MainConfig configuration;

    @Getter private TpaManager tpaManager;
    @Getter private VanishManager vanishManager;
    @Getter private HomeManager homeManager;

    @Getter private TpaExpireScheduler expireScheduler;

    @Override
    public void onEnable() {
        instance = this;
        new Keys(this);
        configuration = new MainConfig(this);
        this.tpaManager = new TpaManager();
        this.vanishManager = new VanishManager();
        this.homeManager = new HomeManager();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getPersistentDataContainer().has(Keys.isVanished)) {
                VanishManager.instance().vanish(player, true);
                player.getPersistentDataContainer().remove(Keys.isVanished);
            }
        }
        loadConfig();
        this.expireScheduler = new TpaExpireScheduler();
        this.expireScheduler.runTaskTimer(this, 0L, 20L);
        
        new SpawnCommand().register(this);
        new TpaCommand().register(this);
        new RTpCommand().register(this);
        new VanishCommand().register(this);
        new HomeCommand().register(this);
        
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public void loadConfig() {
        configuration.load();
    }

    @Override
    public void onDisable() {
        configuration.save(Config.instance());
        VanishManager.instance().getConfig().save(VanishManager.instance().getAutoVanished());
        for (UUID uuid : VanishManager.instance().getVanished()) {
            Player player = Bukkit.getPlayer(uuid);
            player.getPersistentDataContainer().set(Keys.isVanished, PersistentDataType.BYTE, (byte)0);
        }
        getConfig().set("autoVanish", VanishManager.instance().getAutoVanished().stream().map((u)->u.toString()).toList());
        
    }

}
