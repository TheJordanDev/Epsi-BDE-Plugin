package fr.thejordan.epsi.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import fr.thejordan.epsi.helpers.AbstractConfigFile;
import fr.thejordan.epsi.helpers.Utils;

public class Griefing {

    private static Griefing instance;
    public static Griefing instance() { return instance; }

    public List<UUID> hideMessage = new ArrayList<>();
    public boolean enabled = true;

    public Griefing() {
        instance = this;
    }

    public static  class GriefingConfig extends AbstractConfigFile<Griefing> {

        public GriefingConfig(JavaPlugin plugin) {
            super(plugin);
        }
    
        @Override
        public File file() {
            return new File(getPlugin().getDataFolder(), "griefing.yml");
        }
    
        @Override
        public String name() {
            return "Griefing Config";
        }
    
        @Override
        public Function<YamlConfiguration, Griefing> loadProcess() {
            return (config) -> {
                Griefing conf = new Griefing();
                if (config.isList("hideMessage"))
                    conf.hideMessage = config.getStringList("hideMessage")
                        .stream().filter(Utils::isValidUUID)
                        .map((s)->UUID.fromString(s))
                        .collect(Collectors.toList());
                conf.enabled = config.getBoolean("enabled", true);
                return conf;
            };
        }
    
        @Override
        public Consumer<Griefing> saveProcess(YamlConfiguration configuration) {
            return (config) -> {
                configuration.set("enabled", config.enabled);
                configuration.set("hideMessage", config.hideMessage.stream().map((u)->u.toString()).toList());
            };
        }
    
    }

}