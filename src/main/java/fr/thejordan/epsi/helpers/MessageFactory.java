package fr.thejordan.epsi.helpers;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

public class MessageFactory {

    public static TextComponent tpaNotification(Player origin) {
        TextComponent wantsToTp = Component.text(Messages.PLAYER_WANTS_TO_TP(origin));
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
        return Component.text(Messages.PLAYER_JOINED(player));
    }

    public static TextComponent leaveMessage(Player player) {
        return Component.text(Messages.PLAYER_LEFT(player));
    }

    public static TextComponent tpaHelper() {
        TextComponent header = Component.text("§7§l-=-=-=-=-=-=-=-");
        TextComponent one = Component.text("§6/tpa §3<player>").hoverEvent(HoverEvent.showText(Component.text("§eEnvoie une demande à <player>")));
        TextComponent two = Component.text("§6/tpa §3<accept/deny/ignore>").hoverEvent(HoverEvent.showText(Component.text("§eAccepte/Refuse/Ignore la dernière demande.")));
        TextComponent three = Component.text("§6/tpa §3<accept/deny/ignore> <player>").hoverEvent(HoverEvent.showText(Component.text("§eAccepte/Refuse/Ignore la demande de <player>")));
        TextComponent footer = Component.text("§7§l-=-=-=-=-=-=-=-");
        return Component.newline()
                .append(header).append(Component.newline())
                .append(one).append(Component.newline())
                .append(two).append(Component.newline())
                .append(three).append(Component.newline())
                .append(footer).append(Component.newline());
    }


}
