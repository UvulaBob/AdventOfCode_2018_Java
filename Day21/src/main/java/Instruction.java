import Operations.Operation;

import java.util.ArrayList;

public class Instruction {

    Operation operation;
    ArrayList<Long> parameters = new ArrayList<>();

    public String toString() {
        return String.format("%s %s %s %s", operation.getClass().getSimpleName().toLowerCase(), parameters.get(1), parameters.get(2), parameters.get(3));
    }
}
