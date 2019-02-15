package Operations;

import java.util.ArrayList;

public class Banr implements Operations.Operation {

        private static Banr single_instance = null;

    public static Banr getInstance() {
        if (single_instance == null) {
            single_instance = new Banr();
        }

        return single_instance;
    }

    private Banr() {
    }

    public ArrayList<Long> compute(ArrayList<Long> registers, ArrayList<Long> parameters) {
        ArrayList<Long> copyOfRegisters = new ArrayList<>(registers);
        Long actualValueA = parameters.get(1);
        Long actualValueB = parameters.get(2);
        Long outputRegister = parameters.get(3);

        Long valueOfRegisterA = registers.get(actualValueA.intValue());
        Long valueOfRegisterB = registers.get(actualValueB.intValue());

        copyOfRegisters.set(outputRegister.intValue(), valueOfRegisterA & valueOfRegisterB);

        return copyOfRegisters;
    }

}
