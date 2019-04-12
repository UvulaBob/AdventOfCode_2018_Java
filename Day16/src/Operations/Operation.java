package Operations;

import java.util.ArrayList;

public interface Operation {
    ArrayList<Integer> compute(ArrayList<Integer> registers, ArrayList<Integer> instructions);
}
