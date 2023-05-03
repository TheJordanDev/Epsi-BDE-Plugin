package fr.thejordan.epsi;

import fr.thejordan.epsi.commands.EpsiCommand;
import fr.thejordan.epsi.commands.GriefingCommand;
import fr.thejordan.epsi.commands.HomeCommand;
import fr.thejordan.epsi.commands.RtpCommand;
import fr.thejordan.epsi.commands.SpawnCommand;
import fr.thejordan.epsi.commands.TpaCommand;
import fr.thejordan.epsi.commands.VanishCommand;
import fr.thejordan.epsi.config.Config;
import fr.thejordan.epsi.config.Griefing;
import fr.thejordan.epsi.config.Config.MainConfig;
import fr.thejordan.epsi.config.Griefing.GriefingConfig;
import fr.thejordan.epsi.helpers.Keys;
import fr.thejordan.epsi.listeners.PlayerListener;
import fr.thejordan.epsi.object.HomeManager;
import fr.thejordan.epsi.object.MainScoreboard;
import fr.thejordan.epsi.object.TpaManager;
import fr.thejordan.epsi.object.VanishManager;
import fr.thejordan.epsi.scheduler.GriefingTimeCheckerScheduler;
import fr.thejordan.epsi.scheduler.TpaExpireScheduler;
import fr.thejordan.noflicker.CScoreboardManager;
import lombok.Getter;

import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class Epsi extends JavaPlugin {

    private static Epsi instance;
    public static Epsi instance() { return instance; }

    public static void log(Object msg) { instance.getLogger().log(Level.INFO, msg.toString()); }
    public static void wrn(Object msg) { instance.getLogger().log(Level.WARNING, msg.toString()); }
    public static void err(Object msg) { instance.getLogger().log(Level.SEVERE, msg.toString()); }

    @Getter private MainConfig configuration;
    @Getter private GriefingConfig griefingConfig;

    @Getter private TpaManager tpaManager;
    @Getter private VanishManager vanishManager;
    @Getter private HomeManager homeManager;

    @Getter private TpaExpireScheduler expireScheduler;
    @Getter private GriefingTimeCheckerScheduler gTimeCheckerScheduler;

    @Override
    public void onEnable() {
        instance = this;
        new Keys(this);
        this.configuration = new MainConfig(this);
        this.griefingConfig = new GriefingConfig(this);
        new CScoreboardManager(this);
        this.tpaManager = new TpaManager();
        this.vanishManager = new VanishManager();
        this.homeManager = new HomeManager();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getPersistentDataContainer().has(Keys.isVanished)) {
                VanishManager.instance().vanish(player, true);
                player.getPersistentDataContainer().remove(Keys.isVanished);
            }
            CScoreboardManager.instance.send(new MainScoreboard(player));
        }
        loadConfig();
        this.expireScheduler = new TpaExpireScheduler();
        this.expireScheduler.runTaskTimer(this, 0L, 20L);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (MainScoreboard.currentTitle + 1 > MainScoreboard.titles.length-1)
                    MainScoreboard.currentTitle = 0;
                else
                    MainScoreboard.currentTitle++;
            }
        }.runTaskTimer(this, 0L, 10L);

        this.gTimeCheckerScheduler = new GriefingTimeCheckerScheduler();
        this.gTimeCheckerScheduler.runTaskTimer(this, 0L, 40L);
        
        new EpsiCommand().register(this);
        new SpawnCommand().register(this);
        new TpaCommand().register(this);
        new RtpCommand().register(this);
        new VanishCommand().register(this);
        new HomeCommand().register(this);
        new GriefingCommand().register(this);
        
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public void loadConfig() {
        configuration.load();
        griefingConfig.load();
    }

    @Override
    public void onDisable() {
        configuration.save(Config.instance());
        griefingConfig.save(Griefing.instance());
        VanishManager.instance().getConfig().save(VanishManager.instance().getAutoVanished());
        for (UUID uuid : VanishManager.instance().getVanished()) {
            Player player = Bukkit.getPlayer(uuid);
            player.getPersistentDataContainer().set(Keys.isVanished, PersistentDataType.BYTE, (byte)0);
        }
    }

}
