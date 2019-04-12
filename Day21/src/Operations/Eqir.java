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

    public ArrayList<Long> compute(ArrayList<Long> registers, ArrayList<Long> parameters) {
        ArrayList<Long> copyOfRegisters = new ArrayList<>(registers);
        Long actualValueA = parameters.get(1);
        Long actualValueB = parameters.get(2);
        Long outputRegister = parameters.get(3);

        Long valueOfRegisterB = registers.get(actualValueB.intValue());
        copyOfRegisters.set(outputRegister.intValue(), (actualValueA.equals(valueOfRegisterB)) ? 1L : 0L);

        return copyOfRegisters;
    }

}
