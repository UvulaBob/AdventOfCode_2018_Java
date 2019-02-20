import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part2 {

    public static void main(String[] args) throws IOException {
        int total = 0;
        HashSet<Integer> totals = new HashSet<>();
        totals.add(0);
        List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\UvulaBob\\IdeaProjects\\AoC2018\\Java\\Day1\\src\\main\\resources\\input.txt"));

        boolean foundIt = false;
        while (!foundIt) {
            for (String line : lines) {
                total += Integer.parseInt(line);
                if (totals.contains(total)) {
                    foundIt = true;
                    break;
                }
                totals.add(total);
            }
        }

        System.out.println("Found it! " + total);
        System.out.println("Done!");
    }
}
