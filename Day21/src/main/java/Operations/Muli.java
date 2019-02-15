package Operations;

import java.util.ArrayList;

public class Muli implements Operations.Operation {

        private static Muli single_instance = null;

    public static Muli getInstance() {
        if (single_instance == null) {
            single_instance = new Muli();
        }

        return single_instance;
    }

    private Muli() {
    }

    public ArrayList<Long> compute(ArrayList<Long> registers, ArrayList<Long> parameters) {
        ArrayList<Long> copyOfRegisters = new ArrayList<>(registers);
        Long actualValueA = parameters.get(1);
        Long actualValueB = parameters.get(2);
        Long outputRegister = parameters.get(3);

        Long valueOfRegisterA = registers.get(actualValueA.intValue());

        Long total =  valueOfRegisterA * actualValueB;

        copyOfRegisters.set(outputRegister.intValue(), total);
        return copyOfRegisters;
    }

}
