package Operations;

import java.util.ArrayList;

public class Addi implements Operations.Operation {

    private static Addi single_instance = null;

    public static Addi getInstance() {
        if (single_instance == null) {
            single_instance = new Addi();
        }

        return single_instance;
    }

    private Addi() {
    }

    public ArrayList<Long> compute(ArrayList<Long> registers, ArrayList<Long> parameters) {
        ArrayList<Long> copyOfRegisters = new ArrayList<>(registers);
        Long actualValueA = parameters.get(1);
        Long actualValueB = parameters.get(2);
        Long outputRegister = parameters.get(3);

        Long valueOfRegisterA = registers.get(actualValueA.intValue());
        Long total =  valueOfRegisterA + actualValueB;

        copyOfRegisters.set(outputRegister.intValue(), total);
        return copyOfRegisters;
    }

}
