package Operations;

import java.util.ArrayList;

public class Seti implements Operations.Operation {

        private static Seti single_instance = null;

    public static Seti getInstance() {
        if (single_instance == null) {
            single_instance = new Seti();
        }

        return single_instance;
    }

    private Seti() {
    }

    public ArrayList<Long> compute(ArrayList<Long> registers, ArrayList<Long> parameters) {
        ArrayList<Long> copyOfRegisters = new ArrayList<>(registers);
        Long actualValueA = parameters.get(1);
        Long outputRegister = parameters.get(3);

        copyOfRegisters.set(outputRegister.intValue(), actualValueA);
        return copyOfRegisters;
    }

}
