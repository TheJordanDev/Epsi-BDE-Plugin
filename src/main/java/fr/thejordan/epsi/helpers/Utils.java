package fr.thejordan.epsi.helpers;

import java.util.Optional;
import java.util.Random;

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

}