import java.util.HashMap;

public class Part2 {
    private static final int gridSerialNumber = 3463;
    private static int gridSize = 300;
    private static int[][] grid = new int[gridSize][gridSize];

    public static void main(String[] args) {

        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                int powerLevel = calculatePowerLevelForSingleCell(x + 1, y + 1);
                grid[y][x] = powerLevel;
            }
        }

        int highestPowerLevel = (-5 * gridSize) - 1;
        String coordinatesWithHighestPowerLevel = "0,0,0";

        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y <= gridSize; y++) {
                int maximumBoxSize = calculateMaximumBoxSize(x, y);
                int highestPowerLevelOfAnySizedSquare = -6;
                int squareSizeWithHighestPowerLevelAtCurrentPoint = 0;
                int powerLevelOfSquareAtCurrentSize = 0;

                System.out.println("Calculating power levels for all possible squares in " + x + "," + y);
                for (int currentSize = 1; currentSize <= maximumBoxSize; currentSize++) {
                    powerLevelOfSquareAtCurrentSize += calculatePowerLevelForBordersOfSquare(x, y, currentSize);
                    if (powerLevelOfSquareAtCurrentSize > highestPowerLevelOfAnySizedSquare) {
                        highestPowerLevelOfAnySizedSquare = powerLevelOfSquareAtCurrentSize;
                        squareSizeWithHighestPowerLevelAtCurrentPoint = currentSize;
                    }
                }

                if (highestPowerLevelOfAnySizedSquare > highestPowerLevel) {
                    highestPowerLevel = highestPowerLevelOfAnySizedSquare;
                    coordinatesWithHighestPowerLevel = (x + 1) + "," + (y + 1) + "," + squareSizeWithHighestPowerLevelAtCurrentPoint;
                }
            }
        }

        System.out.println(coordinatesWithHighestPowerLevel);
        System.out.println(highestPowerLevel);
        System.out.println("Done!");

    }


    private static int calculatePowerLevelForBordersOfSquare(int x, int y, int size) {

        int totalPowerLevel = 0;
        int upperX = (x + size);
        int upperY = (y + size);

        while (x < upperX) {
            totalPowerLevel += grid[upperY - 1][x];
            x++;
        }

        while (y < (upperY - 1)) {
            totalPowerLevel += grid[y][upperX - 1];
            y++;
        }

        return totalPowerLevel;
    }

    private static int calculateMaximumBoxSize(int x, int y) {
        int distanceToXBorder = gridSize - x;
        int distanceToYBorder = gridSize - y;
        if (distanceToXBorder < distanceToYBorder) {
            return distanceToXBorder;
        } else {
            return distanceToYBorder;
        }
    }

    private static int calculatePowerLevelForSingleCell(int x, int y) {
        int rackId = x + 10;
        int powerLevel = rackId * y;
        powerLevel += gridSerialNumber;
        powerLevel *= rackId;

        int hundredsDigit = 0;
        if (powerLevel > 99) {
            StringBuilder sb = new StringBuilder(Integer.toString(powerLevel));
            sb.reverse();
            String hundredsDigitCharacter = sb.toString().substring(2, 3);
            hundredsDigit = Integer.parseInt(hundredsDigitCharacter);
        }
        return hundredsDigit - 5;
    }

}
