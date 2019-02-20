public class Part1 {
    private static final int gridSerialNumber = 3463;

    public static void main(String[] args) {
        int gridSize = 300;
        int[][] grid = new int[gridSize][gridSize];

        StringBuilder gridOutput = new StringBuilder();
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                int powerLevel = calculatePowerLevelForSingleCell(x + 1, y + 1);
                grid[y][x] = powerLevel;
                gridOutput.append(String.format("%1s", powerLevel));
                gridOutput.append("\t");
            }
            gridOutput.append("\r\n");
        }

        System.out.println(gridOutput.toString());



        int highestBlockPowerLevel = (-5 * 3);
        String pointWithHighestPoweredBlock = "0,0";
        for (int y = 0; y < gridSize - 2; y++) {
            for (int x = 0; x < gridSize - 2; x++) {
                int powerLevel = 0;
                for (int blockY = y; blockY <= y + 2; blockY++) {
                    for (int blockX = x; blockX <= x + 2; blockX++) {
                        powerLevel += grid[blockY][blockX];
                    }
                }

                if (powerLevel > highestBlockPowerLevel) {
                    pointWithHighestPoweredBlock = (x + 1) + "," + (y + 1);
                    highestBlockPowerLevel = powerLevel;
                }
            }
        }

        System.out.println(pointWithHighestPoweredBlock);
        System.out.println(highestBlockPowerLevel);
        System.out.println("Done!");

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
