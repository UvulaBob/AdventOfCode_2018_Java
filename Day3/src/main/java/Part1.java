import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("C:\\workspace\\AoC2018\\Java\\Day3\\src\\main\\resources\\input.txt"));

        HashMap<String, Integer> occupiedPoints = new HashMap<>();
        Pattern pattern = Pattern.compile("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)");


        for (String line : lines)
        {
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                int row = Integer.parseInt(matcher.group(3));
                int column = Integer.parseInt(matcher.group(2));
                int width = Integer.parseInt(matcher.group(4));
                int height= Integer.parseInt(matcher.group(5));
                for (String point : generateCoverage(row, column, width, height)) {
                    occupiedPoints.putIfAbsent(point, 0);
                    occupiedPoints.put(point, occupiedPoints.get(point) + 1);
                }
            }
        }

        int overlapCount = 0;
        for (int row = 0; row < 1000; row ++) {
            for (int column = 0; column < 1000; column++){
                String point = row + "," + column;
                if (occupiedPoints.keySet().contains(point) && occupiedPoints.get(point) > 1) {
                    overlapCount++;
                }
            }
        }

        System.out.println("Overlap Count: " + overlapCount);
        System.out.println("Done!");
    }

    private static HashSet<String> generateCoverage(int row, int column, int width, int height) {
        HashSet<String> coverage = new HashSet<>();
        for (int i = column; i < column + width; i++) {
            for (int j = row; j < row + height; j++) {
                coverage.add(j + "," + i);
            }
        }
        return coverage;
    }

}
