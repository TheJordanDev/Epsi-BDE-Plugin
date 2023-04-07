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
    public static String YOU_ACCEPTED_REQUEST = "§6Demande acceptée.";
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

    //GLOBALE
    public static String NO_RECENT_REQUEST = "§cAucune demande récente.";
    public static String TELEPORTED_SOON = "§eVous allez être téléporté dans 5 secondes...";
    public static String SPECIFY_PLAYER = "§6Veuillez préciser la demande de quel joueur vous voulez accepter.";
    public static String ALREADY_ASKED(Player target) {
        return "§cVous avez déjà demander à §3§l"+target.getName()+" §r§c!";
    }
    public static String PLAYER_NEVER_SENT_INVITE(Player player, String _name) {
        String name = (player != null) ? player.getName() : _name;
        return "§cLe joueur §3§l"+name+" §r§cn'éxiste pas ou ne ta pas envoyer d'inviation !";
    }
}
