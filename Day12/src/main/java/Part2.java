import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Part2 {
    private static HashMap<String, String> transitionConditions = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("C:\\workspace\\AoC2018\\Java\\Day12\\src\\main\\resources\\input.txt"));
        long maxGeneration = 50000000000L;

        String currentState = lines.get(0).replace("initial state: ", "");
        lines.remove(0);
        lines.remove(0);

        for (String line : lines) {
            String[] stringArray = line.split(" => ");
            String condition = stringArray[0];
            String newPotValue = stringArray[1];
            transitionConditions.put(condition, newPotValue);
        }

        long potOffset = 0;
        long generation = 0;
        long potOffsetIncreasePerGenerationAfterConvergence = 0;
        String lastState ="";
        long lastPotOffset = 0;
        while (generation < maxGeneration) {
            System.out.println(String.format("%3s: %s [%s]", generation, currentState, potOffset));
            if (currentState.equals(lastState)) {
                System.out.println("Convergence!");
                potOffsetIncreasePerGenerationAfterConvergence = potOffset - lastPotOffset;
                break;
            }
            lastState = currentState;
            lastPotOffset = potOffset;
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
            generation++;
        }


        potOffset += (potOffsetIncreasePerGenerationAfterConvergence *  (maxGeneration - generation));

        long total = 0;
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
