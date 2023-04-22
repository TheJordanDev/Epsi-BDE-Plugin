package fr.thejordan.epsi.object;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.thejordan.epsi.Epsi;
import fr.thejordan.epsi.helpers.AbstractConfigFile;
import fr.thejordan.epsi.helpers.MessageFactory;
import fr.thejordan.epsi.helpers.Messages;
import lombok.Getter;

public class VanishManager {
    
    private static VanishManager instance;
    public static VanishManager instance() { return instance; }

    @Getter private List<UUID> autoVanished = new ArrayList<>();
    @Getter private List<UUID> vanished = new ArrayList<>();

    public VanishManager() {
        instance = this;
    }

    public static boolean isVanished(Player player) { return isVanished(player.getUniqueId()); }
    public static boolean isVanished(UUID uuid) { return instance.vanished.contains(uuid); }

    public static void toggleVanish(Player player, boolean silent) { 
        if (isVanished(player)) instance.unVanish(player, silent);
        else instance.vanish(player, silent);
    }

    public static void toggleAutoVanish(Player player) { 
        if (isVanished(player)) instance.autoUnVanish(player);
        else instance.autoVanish(player);
    }

    public void autoVanish(Player player) {
        Messages.VANISH_AUTO_ENABLED.send(player);
        autoVanished.add(player.getUniqueId());
    }

    public void autoUnVanish(Player player) {
        Messages.VANISH_AUTO_DISABLED.send(player);
        autoVanished.remove(player.getUniqueId());
    }

    public static boolean isAutoVanish(Player player) {
        return instance.autoVanished.contains(player.getUniqueId());
    }

    public void unVanish(Player player, boolean silent) {
        vanished.remove(player.getUniqueId());
        Bukkit.getOnlinePlayers().forEach((p)->p.showPlayer(Epsi.instance(), player));
        Messages.UNVANISHED.send(player);
        if (!silent)
            Bukkit.broadcast(MessageFactory.joinMessage(player));
    }

    public void vanish(Player player, boolean silent) {
        vanished.add(player.getUniqueId());
        Bukkit.getOnlinePlayers().forEach((p)->p.hidePlayer(Epsi.instance(), player));
        Messages.VANISHED.send(player);
        if (!silent)
            Bukkit.broadcast(MessageFactory.leaveMessage(player));
    }

    public static class VanishConfig extends AbstractConfigFile<List<UUID>> {

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
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'loadProcess'");
        }

        @Override
        public Consumer<List<UUID>> saveProcess(YamlConfiguration configuration) {
            return (uuids) -> {
                List<String> _ids = uuids.stream().map((uuid)->uuid.toString()).toList();
                configuration.set("auto", _ids);
            };
        }
        
    }

}
