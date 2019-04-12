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

    public ArrayList<Long> compute(ArrayList<Long> registers, ArrayList<Long> parameters) {
        ArrayList<Long> copyOfRegisters = new ArrayList<>(registers);
        Long actualValueA = parameters.get(1);
        Long actualValueB = parameters.get(2);
        Long outputRegister = parameters.get(3);

        Long valueOfRegisterA = registers.get(actualValueA.intValue());

        copyOfRegisters.set(outputRegister.intValue(), valueOfRegisterA | actualValueB);

        return copyOfRegisters;
    }

}
