package Operations;

import java.util.ArrayList;

public class Setr implements Operations.Operation {

        private static Setr single_instance = null;

    public static Setr getInstance() {
        if (single_instance == null) {
            single_instance = new Setr();
        }

        return single_instance;
    }

    private Setr() {
    }

    public ArrayList<Long> compute(ArrayList<Long> registers, ArrayList<Long> parameters) {
        ArrayList<Long> copyOfRegisters = new ArrayList<>(registers);
        Long actualValueA = parameters.get(1);
        Long valueOfRegisterA = registers.get(actualValueA.intValue());
        Long outputRegister = parameters.get(3);

        copyOfRegisters.set(outputRegister.intValue(), valueOfRegisterA);
        return copyOfRegisters;
    }

}
