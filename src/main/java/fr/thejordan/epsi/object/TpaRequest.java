package fr.thejordan.epsi.object;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.thejordan.epsi.Epsi;
import fr.thejordan.epsi.helpers.MessageFactory;
import fr.thejordan.epsi.helpers.Messages;
import fr.thejordan.epsi.object.TpaManager.TpaAction;
import lombok.Getter;

public class TpaRequest {
    
    @Getter private final UUID from;
    @Getter private final UUID to;
    @Getter private final long when;

    public TpaRequest(Player from, Player to) { this(from.getUniqueId(), to.getUniqueId()); }
    public TpaRequest(UUID from, UUID to) {
        this.from = from;
        this.to = to;
        this.when = Calendar.getInstance().getTimeInMillis();   
    }

    public void expire() {
        TpaManager.instance().getTpaRequests().remove(this);
    }

    public long fromNow() {
        return Calendar.getInstance().getTimeInMillis() - when;
    }

    public Optional<Player> from() {
        return Optional.ofNullable(Bukkit.getPlayer(getFrom()));
    }

    public Optional<Player> to() {
        return Optional.ofNullable(Bukkit.getPlayer(getTo()));
    }

    public void request() {
        if (from().isEmpty() || to().isEmpty()) return;
        Messages.REQUEST_SENT.send(from().get());
        to().get().sendMessage(MessageFactory.tpaNotification(from().get()));
    }

    public void action(TpaAction action) {
        TpaManager.instance().getTpaRequests().remove(this);
        if (from().isEmpty() || to().isEmpty()) return;
        if (action == TpaAction.ACCEPT) Messages.YOU_ACCEPTED_REQUEST.send(to().get());
        else if (action == TpaAction.DENY) Messages.YOU_DENIED_REQUEST.send(to().get());
        else Messages.YOU_IGNORED_REQUEST.send(to().get());

        if (action != TpaAction.IGNORE) {
            if (action == TpaAction.ACCEPT) {
                Messages.REQUEST_ACCEPTED(to().get()).send(from().get());
                new BukkitRunnable() {
                    @Override
                    public void run() { from().get().teleport(to().get()); }
                }.runTaskLater(Epsi.instance(),20*5);
            } else
                Messages.REQUEST_DENIED(to().get()).send(from().get());
        }

    }

}
