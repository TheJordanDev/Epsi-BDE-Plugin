package fr.thejordan.epsi.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.entity.Player;

import lombok.Getter;

public class TpaManager {
    
    private static TpaManager instance;
    public static TpaManager instance() { return instance; }

    @Getter
    private final List<TpaRequest> tpaRequests = new ArrayList<>();

    public TpaManager() {
        instance = this;
    }
    
    //Utils
    public Optional<TpaRequest> getFirstIfSent(UUID from) {
        return getTpaRequests()
            .stream()
            .filter((r)->r.getFrom() == from)
            .findFirst();
    }
    public Optional<TpaRequest> getLastReceived(UUID to) {
        return getTpaRequests()
            .stream()
            .filter((r)->r.getTo() == to)
            .sorted((a,b)->-1)
            .findFirst();
    }


    public TpaRequest removeRequest(Player from) { return removeRequest(from.getUniqueId()); }
    public TpaRequest removeRequest(UUID from) {
        Optional<TpaRequest> request = getFirstIfSent(from);
        if (request.isPresent()) getTpaRequests().remove(request.get());
        return request.orElse(null);
    }

    public boolean canSend(Player from) { return canSend(from.getUniqueId()); }
    public boolean canSend(UUID from) {
        Optional<TpaRequest> request = getFirstIfSent(from);
        return request.isEmpty();
    }

    public Optional<TpaRequest> hasReceivedFrom(Player to, Player from) { return hasReceivedFrom(to.getUniqueId(), from.getUniqueId()); }
    public Optional<TpaRequest> hasReceivedFrom(UUID to, UUID from) {
        return getTpaRequests()
            .stream()
            .filter((r)->r.getTo() == to && r.getFrom() == from)
            .findAny();
    }

    public static TpaRequest request(Player player, Player to) {
        TpaRequest request = new TpaRequest(player, to);
        instance.getTpaRequests().add(request);
        request.request();
        return request;
    }

    public static enum TpaAction {
        NONE,
        ACCEPT,
        DENY,
        IGNORE;

        public static TpaAction which(String arg) {
            return switch(arg.toLowerCase()) {
                default -> NONE;
                case "accept" -> ACCEPT;
                case "deny" -> DENY;
                case "ignore" -> IGNORE;
            };
        }

    }

}
