package fr.thejordan.epsi.helpers;

import fr.thejordan.epsi.config.Griefing;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class Utils {

    public static String formatTemps(Player player) {
        int secondes = player.getStatistic(Statistic.TOTAL_WORLD_TIME)/20;
        long jours = TimeUnit.SECONDS.toDays(secondes);
        long heures = TimeUnit.SECONDS.toHours(secondes) - (jours * 24);
        long minutes = TimeUnit.SECONDS.toMinutes(secondes) - (jours * 24 * 60) - (heures * 60);
        long secondesRestantes = secondes - (jours * 24 * 60 * 60) - (heures * 60 * 60) - (minutes * 60);
        String resultat = "";
        if (jours > 0) resultat += jours + "J ";
        if (heures > 0) resultat += heures + "H ";
        if (minutes > 0) resultat += minutes + "M ";
        if (secondesRestantes > 0 || resultat.isEmpty()) resultat += secondesRestantes + "S";
        return resultat;
    }
    

    public static int getRandomNumberInRange(int min, int max) {
        if (min >= max) throw new IllegalArgumentException("max must be greater than min");
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static Optional<Player> getPlayer(String name) {
        return Optional.ofNullable(Bukkit.getPlayer(name));
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

    public static Integer intFromString(String num) {
        try { return Integer.parseInt(num); }
        catch (Exception e) { return 0; }
    }

    public static Double doubleFromString(String num) {
        try { return Double.parseDouble(num); }
        catch (Exception e) { return 0D; }
    }

    public static Float floatFromString(String num) {
        try { return Float.parseFloat(num); }
        catch (Exception e) { return 0F; }
    }

    public static String locationToString(Location location) {
        StringJoiner joiner = new StringJoiner("|")
            .add(String.valueOf(location.getX()))
            .add(String.valueOf(location.getY()))
            .add(String.valueOf(location.getZ()))
            .add(String.valueOf(location.getYaw()))
            .add(String.valueOf(location.getPitch()))
            .add(location.getWorld().getName());
        return joiner.toString();
    }

    public static Location stringToLocation(String location) {
        String[] parts = location.split(Pattern.quote("|"));
        if (parts.length != 6) return Bukkit.getWorlds().get(0).getSpawnLocation();
        double x = doubleFromString(parts[0]);
        double y = doubleFromString(parts[1]);
        double z = doubleFromString(parts[2]);
        float yaw = Math.round(floatFromString(parts[3]));
        float pitch = Math.round(floatFromString(parts[4]));
        String world = parts[5];
        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    public static void toggleGriefing(boolean status) {
        broadcastExcept(MessageFactory.griefingStatus(status), Griefing.instance().hideMessage);
        Bukkit.getWorlds().get(0).setGameRule(GameRule.MOB_GRIEFING, status);
    }

    public static void broadcastExcept(Component message, List<UUID> except) {
        Bukkit.getOnlinePlayers()
            .stream().filter((p)->!except.contains(p.getUniqueId()))
            .forEach((p)->p.sendMessage(message));
    }

}