import java.io.File;
import java.io.IOException;


public class Part1 {

    public static void main(String[] args) throws IOException {
        String mapInput = new File("").getAbsolutePath() + "\\src\\input.txt";

        Simulator simulator = Simulator.getInstance();
        int elfPower = 3;
        simulator.generateWorldMap(mapInput);
        System.out.println(String.format("Elf Power Level: %s", elfPower));
        simulator.powerUpElves(elfPower);
        simulator.runSimulation();
    }
}

