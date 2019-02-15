package Operations;

import java.util.ArrayList;

public class Bani implements Operations.Operation {

        private static Bani single_instance = null;

    public static Bani getInstance() {
        if (single_instance == null) {
            single_instance = new Bani();
        }

        return single_instance;
    }

    private Bani() {
    }

    public ArrayList<Long> compute(ArrayList<Long> registers, ArrayList<Long> parameters) {
        ArrayList<Long> copyOfRegisters = new ArrayList<>(registers);
        Long actualValueA = parameters.get(1);
        Long actualValueB = parameters.get(2);
        Long outputRegister = parameters.get(3);

        Long valueOfRegisterA = registers.get(actualValueA.intValue());

        copyOfRegisters.set(outputRegister.intValue(), valueOfRegisterA & actualValueB);

        return copyOfRegisters;
    }

}
