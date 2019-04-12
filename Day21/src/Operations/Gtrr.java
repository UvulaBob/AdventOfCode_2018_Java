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

    public ArrayList<Long> compute(ArrayList<Long> registers, ArrayList<Long> parameters) {
        ArrayList<Long> copyOfRegisters = new ArrayList<>(registers);
        Long actualValueA = parameters.get(1);
        Long actualValueB = parameters.get(2);
        Long outputRegister = parameters.get(3);

        Long valueOfRegisterA = registers.get(actualValueA.intValue());
        Long valueOfRegisterB = registers.get(actualValueB.intValue());

        copyOfRegisters.set(outputRegister.intValue(), (valueOfRegisterA > valueOfRegisterB) ? 1L : 0L);

        return copyOfRegisters;
    }

}
