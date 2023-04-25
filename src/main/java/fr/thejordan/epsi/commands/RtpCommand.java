package fr.thejordan.epsi.commands;

import fr.thejordan.epsi.helpers.Utils;
import fr.thejordan.epsi.object.Config;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RtpCommand implements CommandExecutor, TabCompleter {

    public void register(JavaPlugin plugin) {
        plugin.getCommand("rtp").setExecutor(this);
        plugin.getCommand("rtp").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) return false;
        player.addPotionEffect(
                new PotionEffect(
                        PotionEffectType.DAMAGE_RESISTANCE,
                        300,999,
                        false,false,false
                )
        );
        Location destination = Config.instance().getSpawnCenter().clone();
        int x = Utils.getRandomNumberInRange(Config.instance().getRtpRay()*-1, Config.instance().getRtpRay());
        int z = Utils.getRandomNumberInRange(Config.instance().getRtpRay()*-1, Config.instance().getRtpRay());
        int highest = destination.getWorld().getHighestBlockAt(x,z).getY();
        destination.add(x, highest+100, z);
        destination.setYaw(player.getLocation().getYaw());
        destination.setPitch(player.getLocation().getPitch());
        player.teleportAsync(destination);
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        return null;
    }
}
