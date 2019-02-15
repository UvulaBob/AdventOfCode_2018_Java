import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Part1 {
    private static HashMap<String, String> transitionConditions = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("C:\\workspace\\AoC2018\\Java\\Day12\\src\\main\\resources\\input.txt"));
        int maxGeneration = 20;

        String currentState = lines.get(0).replace("initial state: ", "");
        lines.remove(0);
        lines.remove(0);

        for (String line : lines) {
            String[] stringArray = line.split(" => ");
            String condition = stringArray[0];
            String newPotValue = stringArray[1];
            transitionConditions.put(condition, newPotValue);
        }

        int potOffset = 0;
        for (int generation = 0; generation < maxGeneration; generation++) {
            System.out.println(String.format("%2s: %s", generation, currentState));
            currentState = "...." + currentState + "....";
            StringBuilder futureState = new StringBuilder("..");
            potOffset -= 4;

            for (int potPointer = 2; potPointer < currentState.length() - 2; potPointer++) {
                String currentPotLayout = currentState.substring(potPointer - 2, potPointer + 3);
                String futurePot = transitionConditions.get(currentPotLayout);
                futureState.append(futurePot);
            }

            currentState = futureState.toString();
            String[] splitCurrentState = currentState.split("#", 2);
            potOffset += splitCurrentState[0].length();
            currentState = "#" + splitCurrentState[1];
            currentState = currentState.substring(0, currentState.lastIndexOf("#") + 1);

        }

        System.out.println(String.format("%2s: %s", maxGeneration, currentState));

        int total = 0;
        for (String pot : currentState.split("")) {
            if (pot.equals("#")) {
                total += potOffset;
            }
            potOffset++;
        }

        System.out.println(total);
        System.out.println("Done!");
    }

}
