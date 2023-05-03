package fr.thejordan.epsi.object;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.thejordan.epsi.helpers.Utils;
import fr.thejordan.noflicker.CScoreboard;
import net.kyori.adventure.text.Component;

public class MainScoreboard extends CScoreboard {

    public static int currentTitle = 0;
    public static String[] titles = {
        " §6> §9ScoreBoard §6< ",
        "§a>§6> §bScoreBoard §6<§a<"
    };

    public MainScoreboard(Player player) {
        super("epsi_scoreboard", "", player);
    }

    @Override
    public void setup() {
        int health = Double.valueOf(player.getHealth()).intValue();
        String healthColor = 
            (health <= 20 && health > 12) ? "§a" : 
            (health <= 13 && health > 7) ? "§6" : 
            "§c";
        Location location = player.getLocation();
        
        this.setVariable("player_health", player.getName()+healthColor+"❤"+health, 6);
        this.setStatics("§7Location:", 5);
        this.setVariable("player_position", "X:"+location.getBlockX()+" Y:"+location.getBlockY()+" Z:"+location.getBlockZ(), 4);
        this.setStatics("§7Temp de jeu:", 3);
        this.setVariable("player_time", Utils.formatTemps(player), 2);
        this.setStatics("§7TPS:", 1);

        this.setVariable("player_ping", player.getPing()+"ms", 0);
        objective().displayName(Component.text(titles[currentTitle]));
    }

    @Override
    public void refresh() {
        int health = Double.valueOf(player.getHealth()).intValue();
        String healthColor = 
            (health <= 20 && health > 12) ? "§a" : 
            (health <= 13 && health > 7) ? "§6" : 
            "§c";
        Location location = player.getLocation();
        
        this.updateVariable("player_health", healthColor+"❤"+health+" HP");
        this.updateVariable("player_position", "X:"+location.getBlockX()+" Y:"+location.getBlockY()+" Z:"+location.getBlockZ());
        this.updateVariable("player_time", Utils.formatTemps(player));
        this.updateVariable("player_ping", player.getPing()+"ms");
        objective().displayName(Component.text(titles[currentTitle]));
    }
    
}
