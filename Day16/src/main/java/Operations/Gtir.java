package Operations;

import java.util.ArrayList;

public class Gtir implements Operations.Operation {

        private static Gtir single_instance = null;

    public static Gtir getInstance() {
        if (single_instance == null) {
            single_instance = new Gtir();
        }

        return single_instance;
    }

    private Gtir() {
    }

    public ArrayList<Integer> compute(ArrayList<Integer> registers, ArrayList<Integer> instructions) {
        ArrayList<Integer> copyOfRegisters = new ArrayList<>(registers);
        int actualValueA = instructions.get(1);
        int actualValueB = instructions.get(2);
        int outputRegister = instructions.get(3);

        int valueOfRegisterB = registers.get(actualValueB);
        boolean isAgreaterThanB = actualValueA > valueOfRegisterB;

        copyOfRegisters.set(outputRegister, (isAgreaterThanB) ? 1 : 0);

        return copyOfRegisters;
    }

}
