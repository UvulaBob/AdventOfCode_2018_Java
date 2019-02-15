package Operations;

import java.util.ArrayList;

public class Seti implements Operations.Operation {

        private static Seti single_instance = null;

    public static Seti getInstance() {
        if (single_instance == null) {
            single_instance = new Seti();
        }

        return single_instance;
    }

    private Seti() {
    }

    public ArrayList<Integer> compute(ArrayList<Integer> registers, ArrayList<Integer> instructions) {
        ArrayList<Integer> copyOfRegisters = new ArrayList<>(registers);
        int actualValueA = instructions.get(1);
        int outputRegister = instructions.get(3);

        copyOfRegisters.set(outputRegister, actualValueA);
        return copyOfRegisters;
    }

}
