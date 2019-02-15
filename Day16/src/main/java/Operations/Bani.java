package Operations;

import java.util.ArrayList;

public class Bani implements Operations.Operation {

        private static Bani single_instance = null;

    public static Bani getInstance() {
        if (single_instance == null) {
            single_instance = new Bani();
        }

        return single_instance;
    }

    private Bani() {
    }

    public ArrayList<Integer> compute(ArrayList<Integer> registers, ArrayList<Integer> instructions) {
        ArrayList<Integer> copyOfRegisters = new ArrayList<>(registers);
        int actualValueA = instructions.get(1);
        int actualValueB = instructions.get(2);
        int outputRegister = instructions.get(3);

        int valueOfRegisterA = registers.get(actualValueA);

        copyOfRegisters.set(outputRegister, valueOfRegisterA & actualValueB);

        return copyOfRegisters;
    }

}
