package Operations;

import java.util.ArrayList;

public class Eqir implements Operations.Operation {

        private static Eqir single_instance = null;

    public static Eqir getInstance() {
        if (single_instance == null) {
            single_instance = new Eqir();
        }

        return single_instance;
    }

    private Eqir() {
    }

    public ArrayList<Integer> compute(ArrayList<Integer> registers, ArrayList<Integer> instructions) {
        ArrayList<Integer> copyOfRegisters = new ArrayList<>(registers);
        int actualValueA = instructions.get(1);
        int actualValueB = instructions.get(2);
        int outputRegister = instructions.get(3);

        int valueOfRegisterB = registers.get(actualValueB);
        boolean areTheyEqual = actualValueA == valueOfRegisterB;

        copyOfRegisters.set(outputRegister, (areTheyEqual) ? 1 : 0);

        return copyOfRegisters;
    }

}
