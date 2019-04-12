package Operations;

import java.util.ArrayList;

public class Addr implements Operations.Operation {

    private static Addr single_instance = null;

    public static Addr getInstance() {
        if (single_instance == null) {
            single_instance = new Addr();
        }

        return single_instance;
    }

    private Addr() {
    }

    public ArrayList<Long> compute(ArrayList<Long> registers, ArrayList<Long> parameters) {
        ArrayList<Long> copyOfRegisters = new ArrayList<>(registers);
        Long actualValueA = parameters.get(1);
        Long actualValueB = parameters.get(2);
        Long outputRegister = parameters.get(3);

        Long valueOfRegisterA = registers.get(actualValueA.intValue());
        Long valueOfRegisterB = registers.get(actualValueB.intValue());

        Long total = valueOfRegisterA + valueOfRegisterB;
        copyOfRegisters.set(outputRegister.intValue(), total);

        return copyOfRegisters;
    }

}
