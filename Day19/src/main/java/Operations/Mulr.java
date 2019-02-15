package Operations;

import java.util.ArrayList;

public class Mulr implements Operations.Operation {

        private static Mulr single_instance = null;

    public static Mulr getInstance() {
        if (single_instance == null) {
            single_instance = new Mulr();
        }

        return single_instance;
    }

    private Mulr() {
    }

    public ArrayList<Long> compute(ArrayList<Long> registers, ArrayList<Long> parameters) {
        ArrayList<Long> copyOfRegisters = new ArrayList<>(registers);
        Long actualValueA = parameters.get(1);
        Long actualValueB = parameters.get(2);
        Long outputRegister = parameters.get(3);

        Long valueOfRegisterA = registers.get(actualValueA.intValue());
        Long valueOfRegisterB = registers.get(actualValueB.intValue());

        Long total =  valueOfRegisterA * valueOfRegisterB;

        copyOfRegisters.set(outputRegister.intValue(), total);
        return copyOfRegisters;
    }

}
