package Operations;

import java.util.ArrayList;

public class Banr implements Operations.Operation {

        private static Banr single_instance = null;

    public static Banr getInstance() {
        if (single_instance == null) {
            single_instance = new Banr();
        }

        return single_instance;
    }

    private Banr() {
    }

    public ArrayList<Integer> compute(ArrayList<Integer> registers, ArrayList<Integer> instructions) {
        ArrayList<Integer> copyOfRegisters = new ArrayList<>(registers);
        int actualValueA = instructions.get(1);
        int actualValueB = instructions.get(2);
        int outputRegister = instructions.get(3);

        int valueOfRegisterA = registers.get(actualValueA);
        int valueOfRegisterB = registers.get(actualValueB);

        copyOfRegisters.set(outputRegister, valueOfRegisterA & valueOfRegisterB);

        return copyOfRegisters;
    }

}
