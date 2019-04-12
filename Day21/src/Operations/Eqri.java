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

    public ArrayList<Long> compute(ArrayList<Long> registers, ArrayList<Long> parameters) {
        ArrayList<Long> copyOfRegisters = new ArrayList<>(registers);
        Long actualValueA = parameters.get(1);
        Long actualValueB = parameters.get(2);
        Long outputRegister = parameters.get(3);

        Long valueOfRegisterA = registers.get(actualValueA.intValue());
        copyOfRegisters.set(outputRegister.intValue(), (valueOfRegisterA.equals(actualValueB)) ? 1L : 0L);

        return copyOfRegisters;
    }

}
