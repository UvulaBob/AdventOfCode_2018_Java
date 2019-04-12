package Operations;

import java.util.ArrayList;

public class Gtri implements Operations.Operation {

        private static Gtri single_instance = null;

    public static Gtri getInstance() {
        if (single_instance == null) {
            single_instance = new Gtri();
        }

        return single_instance;
    }

    private Gtri() {
    }

    public ArrayList<Integer> compute(ArrayList<Integer> registers, ArrayList<Integer> instructions) {
        ArrayList<Integer> copyOfRegisters = new ArrayList<>(registers);
        int actualValueA = instructions.get(1);
        int actualValueB = instructions.get(2);
        int outputRegister = instructions.get(3);

        int valueOfRegisterA = registers.get(actualValueA);
        boolean isAgreaterThanB = valueOfRegisterA > actualValueB;

        copyOfRegisters.set(outputRegister, (isAgreaterThanB) ? 1 : 0);

        return copyOfRegisters;
    }

}
