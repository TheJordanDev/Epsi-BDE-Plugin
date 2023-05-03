package fr.thejordan.noflicker;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import net.kyori.adventure.text.Component;

import java.util.HashMap;
import java.util.Map;

public abstract class CScoreboard {

    public final Player player;

    private final Scoreboard board;
    public Scoreboard board() { return board; }

    private final Objective objective;
    public Objective objective() { return objective; }


    public static String online_players = "online_players";

    private final ChatColorRandomizer colorRandomizer = new ChatColorRandomizer();

    public Map<Integer,String> staticTexts = new HashMap<Integer, String>();

    public CScoreboard(String key,String title,Player player) {
        this.player = player;
        this.board = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = (board.getObjective(key) == null) ? board.registerNewObjective(key,Criteria.DUMMY,Component.text(title)) : board.getObjective(key);
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        setup();
    }

    public abstract void setup();

    public void setStatics(String text, int number) {
        Score money = objective.getScore(text);
        money.setScore(number);
    }

    public void setVariable(String key, String text,int number) {
        Team variable = board.registerNewTeam(key);
        String empty = colorRandomizer.generate();
        variable.addEntry(empty);
        variable.prefix(Component.text(text));
        objective.getScore(empty).setScore(number);
    }

    public void setEmpty(int number) {
        setStatics(colorRandomizer.generate(), number);
    }

    public void updateVariable(String key,String var) {
        Scoreboard pBoard = player.getScoreboard();
        pBoard.getTeam(key).prefix(Component.text(var));
    }

    public void _onlinePlayer() {
        Scoreboard pBoard = player.getScoreboard();
        pBoard.getTeam(online_players).prefix(Component.text("§e§l"+Bukkit.getOnlinePlayers().size() + " §r§7/ §a§l"+Bukkit.getMaxPlayers()));
    }

    public abstract void refresh();

}
