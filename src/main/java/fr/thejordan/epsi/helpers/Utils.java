package fr.thejordan.epsi.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Utils {

    public static int getRandomNumberInRange(int min, int max) {
        if (min >= max) throw new IllegalArgumentException("max must be greater than min");
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static Optional<Player> getPlayer(String name) {
        return Optional.ofNullable(Bukkit.getPlayer(name));
    }

    public static Optional<Boolean> stringToBool(String string) {
        try {
            return Optional.of(Boolean.parseBoolean(string.toLowerCase()));
        } catch (Exception e) {
            return Optional.ofNullable(null);
        }
    }

    public static List<String> autocomplete(String written, List<String> words) {
        List<String> returned = new ArrayList<>();
        if (written.isBlank()) return words;
        else {
            for (String word : words) {
                if (word.toUpperCase().startsWith(written.toUpperCase())) returned.add(word);
            }
        }
        return returned;
    }

    public static boolean isValidUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}