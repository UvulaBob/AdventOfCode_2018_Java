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

    public ArrayList<Integer> compute(ArrayList<Integer> registers, ArrayList<Integer> instructions) {
        ArrayList<Integer> copyOfRegisters = new ArrayList<>(registers);
        int actualValueA = instructions.get(1);
        int actualValueB = instructions.get(2);
        int outputRegister = instructions.get(3);

        int valueOfRegisterA = registers.get(actualValueA);
        int total =  valueOfRegisterA + actualValueB;

        copyOfRegisters.set(outputRegister, total);
        return copyOfRegisters;
    }

}
