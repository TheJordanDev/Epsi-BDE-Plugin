package fr.thejordan.epsi.helpers;

import org.bukkit.entity.Player;

public class Messages {

    //SYSTEM
    public static String PLAYER_JOINED(Player player) {
        return "§7[§a+§7] §6§l"+player.getName();
    }
    public static String PLAYER_LEFT(Player player) {
        return "§7[§c-§7] §6§l"+player.getName();
    }
    public static String PLAYER_WANTS_TO_TP(Player player) {
        return "§3§l"+player.getName()+" §r§eveut ce téléporter à toi !";
    }

    //ACCEPT
    public static String YOU_ACCEPTED_REQUEST = "§6Demande accepté.";
    public static String REQUEST_ACCEPTED(Player player){
        return "§3§l"+player.getName()+" §r§aa accepter ta demande.";
    }

    //DENY
    public static String YOU_DENIED_REQUEST = "§6Demande refusée.";
    public static String REQUEST_DENIED(Player player){
        return "§3§l"+player.getName()+" §r§ca refuser ta demande.";
    }

    //IGNORE
    public static String YOU_IGNORED_REQUEST = "§6Demande ignorée.";


}
