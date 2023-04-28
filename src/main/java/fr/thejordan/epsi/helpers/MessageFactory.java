package fr.thejordan.epsi.helpers;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MessageFactory {

    public static TextComponent tpaNotification(Player origin) {
        TextComponent wantsToTp = Messages.PLAYER_WANTS_TO_TP(origin).getMessage();
        TextComponent accept = Component
                .text("[ACCEPTER]")
                .color(TextColor.color(0, 153, 0))
                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND,"/tpa accept "+origin.getName()))
                .hoverEvent(HoverEvent.showText(Component.text("La personne seras notifié du refus.")));
        TextComponent deny = Component
                .text("[REFUSER]")
                .color(TextColor.color(153, 0, 0))
                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND,"/tpa deny "+origin.getName()))
                .hoverEvent(HoverEvent.showText(Component.text("La personne seras notifié du refus.")));
        TextComponent ignore = Component
                .text("[IGNORER]")
                .color(TextColor.color(112, 112, 112))
                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND,"/tpa ignore "+origin.getName()))
                .hoverEvent(HoverEvent.showText(Component.text("La personne ne seras pas notifié du refus.")));
        return Component.empty()
                .append(wantsToTp)
                .append(Component.newline())
                .append(accept).append(Component.space()).append(deny).append(Component.space()).append(ignore);
    }

    public static TextComponent joinMessage(Player player) {
        return Messages.PLAYER_JOINED(player).getMessage();
    }

    public static TextComponent leaveMessage(Player player) {
        return Messages.PLAYER_LEFT(player).getMessage();
    }

    public static TextComponent tpaHelper() {
        return Component.newline()
            .append(Component.text("§7§l-=-=-=-=-=-=-=-")).append(Component.newline())
            .append(Component.text("§6/tpa §3<player>").hoverEvent(HoverEvent.showText(Component.text("§eEnvoie une demande à <player>")))).append(Component.newline())
            .append(Component.text("§6/tpa §3<accept/deny/ignore>").hoverEvent(HoverEvent.showText(Component.text("§eAccepte/Refuse/Ignore la dernière demande.")))).append(Component.newline())
            .append(Component.text("§6/tpa §3<accept/deny/ignore> <player>").hoverEvent(HoverEvent.showText(Component.text("§eAccepte/Refuse/Ignore la demande de <player>")))).append(Component.newline())
            .append(Component.text("§7§l-=-=-=-=-=-=-=-")).append(Component.newline());

    }

    public static TextComponent vanishHelper() {
        return Component.newline()
            .append(Component.text("§7§l-=-=-=-=-=-=-=-§r")).append(Component.newline())
            .append(Component.text("§6/vanish").hoverEvent(HoverEvent.showText(Component.text("§eAlterne ton état de vanish de manière silencieuse")))).append(Component.newline())
            .append(Component.text("§6/vanish <silent/announce>").hoverEvent(HoverEvent.showText(Component.text("§eAlterne ton état de vanish de manière silencieuse ou non")))).append(Component.newline())
            .append(Component.text("§6/vanish auto").hoverEvent(HoverEvent.showText(Component.text("§eAlterne l'état de vanish que tu as à chaques connexions.")))).append(Component.newline())
            .append(Component.text("§7§l-=-=-=-=-=-=-=-")).append(Component.newline());
    }

    public static TextComponent deathLocation(Location location) {
        return Component.empty()
            .append(Component.text(location.getBlockX())).append(Component.space())
            .append(Component.text(location.getBlockY())).append(Component.space())
            .append(Component.text(location.getBlockZ()));
    }

    public static TextComponent griefingToggleExplain() {
        return Component.newline()
            .append(Component.text("§7§l-=-=-=-=-=-=-=-§r")).append(Component.newline())
            .append(Component.text("§6/griefing toggle").hoverEvent(HoverEvent.showText(Component.text("§eAlterne l'état du griefing toggle")))).append(Component.newline())
            .append(Component.text("§7§l-=-=-=-=-=-=-=-")).append(Component.newline());
    }

    public static TextComponent griefingStatus(boolean status) {
        TextComponent component;
        if (status) {
            component = Component
                .text("§c§lATTENTION: Le mob griefing est activé !").append(Component.newline())
                .append(Component.text("§r§7§oLes creepers feront donc des dégats de terrains. Il sera redésactivé la nuit."));
        } else {
            component = Component
                .text("§a§lLe mob griefing est désactivé.").append(Component.newline())
                .append(Component.text("§r§7§oLes creepers ne feront plus de dégats de terrain. Il sera réactivé le jour."));
        }
        return component.append(Component.space())
            .append(Component.text("§7§l§n[CACHER]")
                .hoverEvent(HoverEvent.showText(Component.text("§7Cacher le message pour les prochaines fois")))
                .clickEvent(ClickEvent.runCommand("/griefing hideMessage"))
            );
    }


}
