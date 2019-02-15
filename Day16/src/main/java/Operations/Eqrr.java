package Operations;

import java.util.ArrayList;

public class Eqrr implements Operations.Operation {

        private static Eqrr single_instance = null;

    public static Eqrr getInstance() {
        if (single_instance == null) {
            single_instance = new Eqrr();
        }

        return single_instance;
    }

    private Eqrr() {
    }

    public ArrayList<Integer> compute(ArrayList<Integer> registers, ArrayList<Integer> instructions) {
        ArrayList<Integer> copyOfRegisters = new ArrayList<>(registers);
        int actualValueA = instructions.get(1);
        int actualValueB = instructions.get(2);
        int outputRegister = instructions.get(3);

        int valueOfRegisterA = registers.get(actualValueA);
        int valueOfRegisterB = registers.get(actualValueB);
        boolean areTheyEqual = valueOfRegisterA == valueOfRegisterB;

        copyOfRegisters.set(outputRegister, (areTheyEqual) ? 1 : 0);

        return copyOfRegisters;
    }

}
