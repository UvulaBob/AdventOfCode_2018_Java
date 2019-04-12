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

    public ArrayList<Long> compute(ArrayList<Long> registers, ArrayList<Long> parameters) {
        ArrayList<Long> copyOfRegisters = new ArrayList<>(registers);
        Long actualValueA = parameters.get(1);
        Long actualValueB = parameters.get(2);
        Long outputRegister = parameters.get(3);

        Long valueOfRegisterA = registers.get(actualValueA.intValue());

        copyOfRegisters.set(outputRegister.intValue(), (valueOfRegisterA > actualValueB) ? 1L : 0L);

        return copyOfRegisters;
    }

}
