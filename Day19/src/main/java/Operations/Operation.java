package Operations;

import java.util.ArrayList;

public interface Operation {
    ArrayList<Long> compute(ArrayList<Long> registers, ArrayList<Long> instructions);
}
