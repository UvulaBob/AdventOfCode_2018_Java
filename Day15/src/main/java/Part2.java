import java.io.File;
import java.io.IOException;


public class Part2 {

    public static void main(String[] args) throws IOException {
        String mapInput = new File("").getAbsolutePath() + "\\src\\main\\resources\\input.txt";

        Simulator simulator = Simulator.getInstance();
        simulator.part2 = true;
        int elfPower = 3;
        boolean goblinsWinning = true;
        while (goblinsWinning) {
            try {
                simulator.generateWorldMap(mapInput);
                System.out.println(String.format("Elf Power Level: %s", elfPower));
                simulator.powerUpElves(elfPower);
                simulator.runSimulation();
                goblinsWinning = false;
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                simulator.reset();
                elfPower++;
            }
        }
    }
}

