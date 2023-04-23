package fr.thejordan.epsi.config;

import java.io.File;
import java.util.function.Consumer;
import java.util.function.Function;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import fr.thejordan.epsi.helpers.AbstractConfigFile;
import fr.thejordan.epsi.object.Config;

public class MainConfig extends AbstractConfigFile<Config> {

    public MainConfig(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public File file() {
        return new File(getPlugin().getDataFolder(), "config.yml");
    }

    @Override
    public String name() {
        return "Main Config";
    }

    @Override
    public Function<YamlConfiguration, Config> loadProcess() {
        return (config) -> {
            Config conf = new Config();
            conf.setRtpRay(config.getInt("rtp.ray", 500));
            conf.setSpawnCenter(new Location(
                Bukkit.getWorld(config.getString("spawnCenter.world","world")),
                config.getInt("spawnCenter.x",0),
                config.getInt("spawnCenter.y",0),
                config.getInt("spawnCenter.z",0)
            ));
            conf.setTpaExpiry(config.getInt("tpa.expire", 30));
            return conf;
        };
    }

    @Override
    public Consumer<Config> saveProcess(YamlConfiguration configuration) {
        return (config) -> {};
    }

}