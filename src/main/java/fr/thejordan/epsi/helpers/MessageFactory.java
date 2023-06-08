package fr.thejordan.epsi.helpers;


import fr.thejordan.epsi.object.DeathStat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

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

    public static TextComponent deathTop(List<DeathStat> players, int _page) {
        int MAX_PAGE = (int) Math.ceil((double) Bukkit.getOfflinePlayers().length/5);
        int page = Math.min(_page, MAX_PAGE);
        if (page < 1) page = 1;
        TextComponent message = Component.text("§7§l-=-=-=-=- §e§lTOP MORTS §7§l-=-=-=-=-").append(Component.newline());
        for (int i = 1; i<=players.size(); i++) {
            DeathStat stat = players.get(i-1);
            int top = ((page-1)*5)+i;
            TextComponent playerLine = Component.text(top+". ");
            if (top == 1) playerLine = playerLine.color(TextColor.color(255,202,9));
            else if (top == 2) playerLine = playerLine.color(TextColor.color(182,181,184));
            else if (top == 3) playerLine = playerLine.color(TextColor.color(186,164,69));
            else playerLine = playerLine.color(TextColor.color(131,130,133));
            message = message
                    .append(playerLine)
                    .append(Component.text(stat.displayName+" "+stat.deaths))
                    .append(Component.newline());
        }
        TextComponent footer = Component.text("§7§l-=-=-=-=-=-§r").append(Component.space());

        TextComponent backBtn = Component.text("≪");
        if (page == 1) backBtn = backBtn.color(TextColor.color(105,105,105));
        else {
            backBtn = backBtn
                    .color(TextColor.color(255,215,0))
                    .clickEvent(ClickEvent.runCommand("topdeaths "+(page-1)));
        }

        TextComponent pages = Component
                .empty().color(TextColor.color(64,64,64))
                .append(Component.text(page))
                .append(Component.text("/"))
                .append(Component.text(MAX_PAGE));

        TextComponent nextBtn = Component.text("≫");
        if (page >= MAX_PAGE) nextBtn = nextBtn.color(TextColor.color(105,105,105));
        else {
            nextBtn = nextBtn
                    .color(TextColor.color(255,215,0))
                    .clickEvent(ClickEvent.runCommand("topdeaths "+(page+1)));
        }

        message = message.append(
                footer
                        .append(backBtn).append(Component.space())
                        .append(pages).append(Component.space())
                        .append(nextBtn).append(Component.space())
                        .append(Component.text("§7§l-=-=-=-=-=-§r"))
        );
        return message;
    }

}
