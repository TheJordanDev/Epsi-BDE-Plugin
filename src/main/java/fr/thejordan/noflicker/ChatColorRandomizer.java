package fr.thejordan.noflicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.md_5.bungee.api.ChatColor;

public class ChatColorRandomizer {

    public static String colors = "0123456789abcdefklmno";
    public int finished = 1;
    public List<String> usedCombinations = new ArrayList<String>();
    public ArrayList<Character> usedColors = new ArrayList<Character>();
    public Random random = new Random();

    public String generate() {
        StringBuilder builder = new StringBuilder();
        while (builder.toString().equals("") || usedCombinations.contains(builder.toString())) {
            builder = new StringBuilder();
            for (int _c = 0; _c < finished; _c++) {
                
                int index = random.nextInt(colors.split("").length);
                if (!usedColors.isEmpty()) {
                    while (usedColors.get(usedColors.size() - 1).equals(colors.charAt(index))) {
                        index = random.nextInt(colors.split("").length);
                    }
                }
                builder.append(ChatColor.getByChar(colors.charAt(index)));
                usedColors.add(colors.charAt(index));
            }
        }
        usedCombinations.add(builder.toString());
        if (usedCombinations.size() == colors.split("").length*finished) finished++;
        return builder.toString();
    }

}
