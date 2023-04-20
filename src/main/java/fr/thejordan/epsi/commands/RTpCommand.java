package fr.thejordan.epsi.commands;

import fr.thejordan.epsi.Epsi;
import fr.thejordan.epsi.helpers.Utils;
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

public class RTpCommand implements CommandExecutor, TabCompleter {

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
        Location destination = Epsi.instance().getSpawnCenter().clone();
        int x = Utils.getRandomNumberInRange(Epsi.instance().getRtpRay()*-1,Epsi.instance().getRtpRay());
        int z = Utils.getRandomNumberInRange(Epsi.instance().getRtpRay()*-1,Epsi.instance().getRtpRay());
        int highest = destination.getWorld().getHighestBlockAt(x,z).getY();
        destination.add(x, highest+100, z);
        destination.setYaw(player.getLocation().getYaw());
        destination.setPitch(player.getLocation().getPitch());
        player.teleport(destination);
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        return null;
    }
}
