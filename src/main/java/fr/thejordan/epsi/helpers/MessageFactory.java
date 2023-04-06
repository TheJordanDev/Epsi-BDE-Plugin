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
                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND,"tpa accept"))
                .hoverEvent(HoverEvent.showText(Component.text("La personne seras notifié du refus.")));
        TextComponent deny = Component
                .text("[REFUSER]")
                .color(TextColor.color(153, 0, 0))
                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND,"tpa accept"))
                .hoverEvent(HoverEvent.showText(Component.text("La personne seras notifié du refus.")));
        TextComponent ignore = Component
                .text("[IGNORER]")
                .color(TextColor.color(112, 112, 112))
                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND,"tpa ignore"))
                .hoverEvent(HoverEvent.showText(Component.text("La personne ne seras pas notifié du refus.")));
        return Component.empty()
                .append(wantsToTp)
                .append(Component.newline())
                .append(accept).append(deny).append(ignore);
    }

    public static TextComponent joinMessage(Player player) {
        return Component.text(Messages.PLAYER_JOINED(player));
    }

    public static TextComponent leaveMessage(Player player) {
        return Component.text(Messages.PLAYER_LEFT(player));
    }

}
