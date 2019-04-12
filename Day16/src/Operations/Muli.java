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

    public ArrayList<Integer> compute(ArrayList<Integer> registers, ArrayList<Integer> instructions) {
        ArrayList<Integer> copyOfRegisters = new ArrayList<>(registers);
        int actualValueA = instructions.get(1);
        int actualValueB = instructions.get(2);
        int outputRegister = instructions.get(3);

        int valueOfRegisterA = registers.get(actualValueA);

        int total =  valueOfRegisterA * actualValueB;

        copyOfRegisters.set(outputRegister, total);
        return copyOfRegisters;
    }

}
