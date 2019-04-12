import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part1 {
    private static ArrayList<Bot> bots = new ArrayList<>();
    private static Queue<SearchCube> queue = new PriorityQueue<>(new SearchCubeComparator());


    public static void main (String[] args) throws IOException{
        List<String> lines = Files.readAllLines(Paths.get(new File("").getAbsolutePath() + "\\src\\input.txt"));

        for (String line: lines) {
            line = line.replace("pos=<", "");
            line = line.replace(">", "");
            line = line.replace(" r=", "");
            String[] array = line.split(",");

            int x = Integer.parseInt(array[0]);
            int y = Integer.parseInt(array[1]);
            int z = Integer.parseInt(array[2]);
            int range = Integer.parseInt(array[3]);

            Bot newNano = new Bot(x, y, z, range);

            bots.add(newNano);
        }

        bots.sort(new CompareBotsUsingRange());
        Bot leadBot = bots.get(0);
        int botsInRangeOfLeadBot = 0;

        for (Bot bot : bots) {
            int distanceFromLeadBot = Math.abs(bot.x - leadBot.x) + Math.abs(bot.y - leadBot.y) + Math.abs(bot.z - leadBot.z);
            botsInRangeOfLeadBot += (distanceFromLeadBot <= leadBot.range) ? 1 : 0;
        }

        System.out.println("Bots in range of lead bot: " + botsInRangeOfLeadBot);
        System.out.println("Done!");
    }
}
