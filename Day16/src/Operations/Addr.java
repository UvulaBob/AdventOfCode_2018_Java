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

    public ArrayList<Integer> compute(ArrayList<Integer> registers, ArrayList<Integer> instructions) {
        ArrayList<Integer> copyOfRegisters = new ArrayList<>(registers);
        int actualValueA = instructions.get(1);
        int actualValueB = instructions.get(2);
        int outputRegister = instructions.get(3);

        int valueOfRegisterA = registers.get(actualValueA);
        int valueOfRegisterB = registers.get(actualValueB);

        int total = valueOfRegisterA + valueOfRegisterB;
        copyOfRegisters.set(outputRegister, total);

        return copyOfRegisters;
    }

}
