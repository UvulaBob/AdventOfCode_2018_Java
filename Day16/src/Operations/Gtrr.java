package Operations;

import java.util.ArrayList;

public class Gtrr implements Operations.Operation {

        private static Gtrr single_instance = null;

    public static Gtrr getInstance() {
        if (single_instance == null) {
            single_instance = new Gtrr();
        }

        return single_instance;
    }

    private Gtrr() {
    }

    public ArrayList<Integer> compute(ArrayList<Integer> registers, ArrayList<Integer> instructions) {
        ArrayList<Integer> copyOfRegisters = new ArrayList<>(registers);
        int actualValueA = instructions.get(1);
        int actualValueB = instructions.get(2);
        int outputRegister = instructions.get(3);

        int valueOfRegisterA = registers.get(actualValueA);
        int valueOfRegisterB = registers.get(actualValueB);
        boolean isAgreaterThanB = valueOfRegisterA > valueOfRegisterB;

        copyOfRegisters.set(outputRegister, (isAgreaterThanB) ? 1 : 0);

        return copyOfRegisters;
    }

}
