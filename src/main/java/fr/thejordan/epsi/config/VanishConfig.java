package fr.thejordan.epsi.config;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import fr.thejordan.epsi.helpers.AbstractConfigFile;
import fr.thejordan.epsi.helpers.Utils;

public class VanishConfig extends AbstractConfigFile<List<UUID>> {

    public VanishConfig(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public File file() {
        return new File(getPlugin().getDataFolder(), "vanish.yml");
    }

    @Override
    public String name() {
        return "Vanish Config";
    }

    @Override
    public Function<YamlConfiguration, List<UUID>> loadProcess() {
        return (config)->{
            if (!config.isList("auto")) return Collections.emptyList();
            return config.getStringList("auto")
                .stream().filter((_uu)->Utils.isValidUUID(_uu))
                .map((_uu)->UUID.fromString(_uu))
                .toList();
        };
    }

    @Override
    public Consumer<List<UUID>> saveProcess(YamlConfiguration configuration) {
        return (uuids) -> {
            List<String> _ids = uuids.stream().map((uuid)->uuid.toString()).toList();
            configuration.set("auto", _ids);
        };
    }

}