package Operations;

import java.util.ArrayList;

public class Eqri implements Operations.Operation {

        private static Eqri single_instance = null;

    public static Eqri getInstance() {
        if (single_instance == null) {
            single_instance = new Eqri();
        }

        return single_instance;
    }

    private Eqri() {
    }

    public ArrayList<Integer> compute(ArrayList<Integer> registers, ArrayList<Integer> instructions) {
        ArrayList<Integer> copyOfRegisters = new ArrayList<>(registers);
        int actualValueA = instructions.get(1);
        int actualValueB = instructions.get(2);
        int outputRegister = instructions.get(3);

        int valueOfRegisterA = registers.get(actualValueA);
        boolean areTheyEqual = valueOfRegisterA == actualValueB;

        copyOfRegisters.set(outputRegister, (areTheyEqual) ? 1 : 0);

        return copyOfRegisters;
    }

}
