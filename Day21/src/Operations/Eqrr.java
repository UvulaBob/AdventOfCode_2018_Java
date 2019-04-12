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

    public ArrayList<Long> compute(ArrayList<Long> registers, ArrayList<Long> parameters) {
        ArrayList<Long> copyOfRegisters = new ArrayList<>(registers);
        Long actualValueA = parameters.get(1);
        Long actualValueB = parameters.get(2);
        Long outputRegister = parameters.get(3);

        Long valueOfRegisterA = registers.get(actualValueA.intValue());
        Long valueOfRegisterB = registers.get(actualValueB.intValue());

        copyOfRegisters.set(outputRegister.intValue(), (valueOfRegisterA.equals(valueOfRegisterB)) ? 1L: 0L);

        return copyOfRegisters;
    }

}
