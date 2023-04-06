package fr.thejordan.epsi;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import fr.thejordan.epsi.commands.RTpCommand;
import fr.thejordan.epsi.commands.TpaCommand;
import fr.thejordan.epsi.listeners.PlayerListener;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class Epsi extends JavaPlugin {

    private static Epsi instance;
    public static Epsi instance() { return instance; }

    @Getter
    private final Map<UUID, List<UUID>> tpaRequests = new HashMap<>();

    @Override
    public void onEnable() {
        new TpaCommand().register(this);
        new RTpCommand().register(this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() {

    }
}
