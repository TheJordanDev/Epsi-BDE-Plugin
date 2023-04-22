package fr.thejordan.epsi.helpers;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class Keys {
    
    public static NamespacedKey deathCompass; 
    public static NamespacedKey isDeathChest; 
    public static NamespacedKey savedXP; 
    public static NamespacedKey isVanished; 
    public static NamespacedKey homeLocation; 
    
    public Keys(JavaPlugin plugin) {
        deathCompass = new NamespacedKey(plugin, "deathCompass");
        isDeathChest = new NamespacedKey(plugin, "isDeathChest");
        savedXP = new NamespacedKey(plugin, "savedXP");
        isVanished = new NamespacedKey(plugin, "isVanished");
        homeLocation = new NamespacedKey(plugin, "homeLocation");
    }

}
