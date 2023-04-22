package fr.thejordan.epsi.helpers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

import org.bukkit.entity.Player;

import lombok.Getter;

public class Messages {

    @Getter
    private final TextComponent message;

    public Messages(String message) {
        this(Component.text(message));
    }

    public Messages(TextComponent message) {
        this.message = message;
    }

    public void send(Player player) {
        player.sendMessage(message);
    }

    //SYSTEM
    public static Messages PLAYER_JOINED(Player player) {
        return new Messages("§7[§a+§7] §6§l"+player.getName());
    }
    public static Messages PLAYER_LEFT(Player player) {
        return new Messages("§7[§c-§7] §6§l"+player.getName());
    }
    public static Messages PLAYER_WANTS_TO_TP(Player player) {
        return new Messages("§3§l"+player.getName()+" §r§eveut ce téléporter à toi !");
    }

    //-=-=-=-=-=-= TPA =-=-=-=-=-=-

    //SENT
    public static Messages REQUEST_SENT = new Messages("§eVotre demande a été envoyée.");

    //ACCEPT
    public static Messages YOU_ACCEPTED_REQUEST = new Messages("§6Demande acceptée.");
    public static Messages REQUEST_ACCEPTED(Player player){
        return new Messages("§3§l"+player.getName()+" §r§aa accepter ta demande.§r\n§eVous allez être téléporté dans 5 secondes...");
    }

    //DENY
    public static Messages YOU_DENIED_REQUEST = new Messages("§6Demande refusée.");
    public static Messages REQUEST_DENIED(Player player){
        return new Messages("§3§l"+player.getName()+" §r§ca refuser ta demande.");
    }

    //IGNORE
    public static Messages YOU_IGNORED_REQUEST = new Messages("§6Demande ignorée.");

    //GLOBALE
    public static Messages NO_RECENT_REQUEST = new Messages("§cAucune demande récente.");
    public static Messages SPECIFY_PLAYER = new Messages("§6Veuillez préciser à quel joueur vous voulez vous téléporter.");
    public static Messages ALREADY_ASKED(Player target) {
        return new Messages("§cVous avez déjà demander à §3§l"+target.getName()+" §r§c!");
    }
    public static Messages PLAYER_NEVER_SENT_INVITE(Player player, String _name) {
        String name = (player != null) ? player.getName() : _name;
        return new Messages("§cLe joueur §3§l"+name+" §r§cn'éxiste pas ou ne t'a pas envoyer d'invitation !");
    }
    public static Messages PLEASE_WAIT_FOR_EXPIRY = new Messages("§cVeuillez attendre que votre dernière demande expire ou que la personne réponde !");

    // -=-=-=-=-=-= Vanish =-=-=-=-=-=-
    public static Messages VANISHED = new Messages("§aVous êtes maintenant vanish !");
    public static Messages UNVANISHED = new Messages("§cVous êtes maintenant dévanish !");

    public static Messages VANISH_AUTO_ENABLED = new Messages("§aL'auto vanish est activé, à votre prochaine connexion vous serez automatiquement en vanish !");
    public static Messages VANISH_AUTO_DISABLED = new Messages("§cVous êtes maintenant dévanish !");

    public static Messages VALID_BOOL = new Messages("§cVeuillez entrer un boolean valide <true/false>");

    // -=-=-=-=-=-= Home =-=-=-=-=-=-
    public static Messages HOME_SET = new Messages("§eHome définie");
    public static Messages NO_HOME_SET = new Messages("§cAucun home défini");
    public static Messages HOME_TELEPORT = new Messages("Téléportation en cours");

}
