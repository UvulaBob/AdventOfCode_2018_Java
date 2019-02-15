package Operations;

import java.util.ArrayList;

public class Borr implements Operations.Operation {

        private static Borr single_instance = null;

    public static Borr getInstance() {
        if (single_instance == null) {
            single_instance = new Borr();
        }

        return single_instance;
    }

    private Borr() {
    }

    public ArrayList<Long> compute(ArrayList<Long> registers, ArrayList<Long> parameters) {
        ArrayList<Long> copyOfRegisters = new ArrayList<>(registers);
        Long actualValueA = parameters.get(1);
        Long actualValueB = parameters.get(2);
        Long outputRegister = parameters.get(3);

        Long valueOfRegisterA = registers.get(actualValueA.intValue());
        Long valueOfRegisterB = registers.get(actualValueB.intValue());

        copyOfRegisters.set(outputRegister.intValue(), valueOfRegisterA | valueOfRegisterB);

        return copyOfRegisters;
    }

}
