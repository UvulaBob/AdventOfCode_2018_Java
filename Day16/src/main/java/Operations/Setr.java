package Operations;

import java.util.ArrayList;

public class Setr implements Operations.Operation {

        private static Setr single_instance = null;

    public static Setr getInstance() {
        if (single_instance == null) {
            single_instance = new Setr();
        }

        return single_instance;
    }

    private Setr() {
    }

    public ArrayList<Integer> compute(ArrayList<Integer> registers, ArrayList<Integer> instructions) {
        ArrayList<Integer> copyOfRegisters = new ArrayList<>(registers);
        int actualValueA = instructions.get(1);
        int valueOfRegisterA = registers.get(actualValueA);
        int outputRegister = instructions.get(3);

        copyOfRegisters.set(outputRegister, valueOfRegisterA);
        return copyOfRegisters;
    }

}
