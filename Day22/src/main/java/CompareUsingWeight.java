import java.util.Comparator;
import java.util.HashMap;

public class CompareUsingWeight implements Comparator<String> {
    private HashMap<String, Integer> d;

    public CompareUsingWeight(HashMap<String, Integer> dist) {
        d = dist;
    }

    @Override
    public int compare(String a, String b) {
        if (d.get(a) < d.get(b)) {
            return -1;
        }

        return 1;
    }

}