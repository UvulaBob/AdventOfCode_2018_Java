import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part1 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\UvulaBob\\IdeaProjects\\AoC2018\\Java\\Day2\\src\\main\\resources\\input.txt"));
        Integer twos = 0;
        Integer threes = 0;

        for (String line : lines) {
            HashMap<String, Integer> letterCounts = new HashMap<>();
            ArrayList<String> letters = new ArrayList<>(Arrays.asList(line.split("")));

            for (String letter : letters){
                letterCounts.putIfAbsent(letter, 0);
                letterCounts.put(letter, letterCounts.get(letter) + 1);
            }

            if (letterCounts.containsValue(2)) {
                twos += 1;
            }

            if (letterCounts.containsValue(3)) {
                threes += 1;
            }

        }

        System.out.println("Twos: " + twos);
        System.out.println("Threes: " + threes);
        System.out.println("Checksum: " + twos * threes);
        System.out.println("Done!");

    }
}
