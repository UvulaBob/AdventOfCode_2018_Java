import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part2 {
    private static final int GRID_SIZE = 50;
    private static char[][] worldMap = new char[GRID_SIZE][GRID_SIZE];
    private static TreeMap<Integer, char[][]> previousWorldMaps = new TreeMap<>();
    private static final int minutes = 1000000000;


    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("C:\\workspace\\AoC2018\\Java\\Day18\\src\\main\\resources\\input.txt"));
        int column = 0;
        for (String line : lines) {
            worldMap[column]=line.toCharArray();
            column++;
        }

        previousWorldMaps.put(0, worldMap);
        runTheCycles();
        printMap(worldMap);
        int result = doTheMath(worldMap);
        System.out.println("Total resource value: " + result);
    }

    private static int doTheMath(char[][] worldMap) {
        int treeCount = 0;
        int lumberYardCount = 0;
        for (char[] arrayRow : worldMap) {
            for (char character : arrayRow) {
                switch (character) {
                    case '|':
                        treeCount++;
                        break;
                    case '#':
                        lumberYardCount++;
                        break;
                }
            }
        }
        return treeCount * lumberYardCount;
    }

    private static void printMap(char[][] worldMap) {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (char character : worldMap[row]) {
                sb.append(character);
            }
            sb.append("\r\n");
        }
        System.out.println(sb.toString());
        System.out.println();
        System.out.println();
    }

    private static void runTheCycles() {
        boolean checkingForRepeats = true;
        for (int currentMinute = 1; currentMinute <= minutes; currentMinute++) {
            char[][] newWorldMap = new char[GRID_SIZE][GRID_SIZE];
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int column = 0; column < GRID_SIZE; column++) {
                    char character = worldMap[row][column];
                    HashMap<Character, Integer> adjacentCharacterCounts = new HashMap<>();
                    ArrayList<Character> adjacentCharacters = getSurroundings(worldMap, row, column);
                    for (Character adjacentCharacter : adjacentCharacters) {
                        adjacentCharacterCounts.merge(adjacentCharacter, 1, Integer::sum);
                    }
                    switch (character) {
                        case '.':
                            newWorldMap[row][column] = (adjacentCharacterCounts.containsKey('|') && adjacentCharacterCounts.get('|') > 2) ? '|' : '.';
                            break;
                        case '|':
                            newWorldMap[row][column] = (adjacentCharacterCounts.containsKey('#') && adjacentCharacterCounts.get('#') > 2) ? '#' : '|';
                            break;
                        case '#':
                            newWorldMap[row][column] = (adjacentCharacterCounts.containsKey('#') && adjacentCharacterCounts.containsKey('|')) ? '#' : '.';
                            break;
                    }
                }
            }
            worldMap = newWorldMap;
            if (checkingForRepeats) {
                for (int pastMinute = 1; pastMinute < currentMinute; pastMinute++){
                    boolean mapMatches = true;
                    char[][] previousWorldMap = previousWorldMaps.get(pastMinute);
                    for (int row = 0; row < GRID_SIZE; row++) {
                        if (!Arrays.equals(previousWorldMap[row], worldMap[row])) {
                            mapMatches = false;
                            break;
                        }
                    }

                    if (mapMatches) {
                        int minutesRemaining = minutes - currentMinute;
                        int minutesPerCycle = currentMinute - pastMinute;
                        int fullCyclesRemaining = (minutesRemaining / minutesPerCycle);
                        currentMinute += (fullCyclesRemaining * minutesPerCycle);
                        checkingForRepeats = false;
                        break;
                    }
                }
                previousWorldMaps.put(currentMinute, worldMap);
            }
        }
    }

    private static ArrayList<Character> getSurroundings(char[][] matrix, int row, int column){
        int[][] directions = new int[][]{{-1,-1}, {-1,0}, {-1,1},  {0,1}, {1,1},  {1,0},  {1,-1},  {0, -1}};
        ArrayList<Character> results = new ArrayList<>();
        for (int[] direction : directions) {
            int cRow = row + direction[0];
            int cColumn = column + direction[1];
            if(cColumn >=0 && cColumn < matrix.length)
                if(cRow >= 0 && cRow < matrix[cColumn].length)
                    results.add(matrix[cRow][cColumn]);
        }
        return results;
    }
}
