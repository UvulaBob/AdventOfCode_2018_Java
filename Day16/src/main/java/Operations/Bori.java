package Operations;

import java.util.ArrayList;

public class Bori implements Operations.Operation {

        private static Bori single_instance = null;

    public static Bori getInstance() {
        if (single_instance == null) {
            single_instance = new Bori();
        }

        return single_instance;
    }

    private Bori() {
    }

    public ArrayList<Integer> compute(ArrayList<Integer> registers, ArrayList<Integer> instructions) {
        ArrayList<Integer> copyOfRegisters = new ArrayList<>(registers);
        int actualValueA = instructions.get(1);
        int actualValueB = instructions.get(2);
        int outputRegister = instructions.get(3);

        int valueOfRegisterA = registers.get(actualValueA);

        copyOfRegisters.set(outputRegister, valueOfRegisterA | actualValueB);

        return copyOfRegisters;
    }

}
