import java.util.*;

// This doesn't work. I should try this day again.
public class Part1 {
    private static final int depth = 4845;
    private static final int targetX = 6;
    private static final int targetY = 770;
    private static final int originX = 0;
    private static final int originY = 0;
    private static HashMap<String, Integer> erosionLevels = new HashMap<>();

    public static void main(String[] args) {
        for (int y = originY; y <= targetY; y++) {
            for (int x = originX; x <= targetX; x++) {
                erosionLevels.put(formattedPoint(x, y), calculateErosionLevel(x, y));
            }
        }

        int totalRisk = 0;
        for (Integer erosionLevel : erosionLevels.values()) {
            switch (erosionLevel % 3) {
                case 2:
                    totalRisk += 2;
                    break;
                case 1:
                    totalRisk += 1;
                    break;
            }
        }

        System.out.println("Total Risk: " + totalRisk);
        System.out.println("Done!");


    }

    private static String formattedPoint(int x, int y) {
        return String.format("%s,%s", x, y);
    }

    private static int calculateErosionLevel(int x, int y) {
        int geoIndex = calculateGeologicIndex(x, y);
        return (geoIndex + depth) % 20183;
    }

    private static int calculateGeologicIndex(int x, int y) {
        if ((x == originX && y == originY) || (x == targetX && y == targetY)) {
            return 0;
        } else if (y == 0) {
            return x * 16807;
        } else if (x == 0) {
            return y * 48271;
        } else {
            int lastHorizontalRegionErosionLevel = erosionLevels.get(formattedPoint(x - 1, y));
            int lastVerticalRegionErosionLevel = erosionLevels.get(formattedPoint(x, y - 1));
            return lastHorizontalRegionErosionLevel * lastVerticalRegionErosionLevel;
        }
    }
}
