import java.util.Comparator;
import java.util.HashMap;

public class CompareUsingDistanceHashMap implements Comparator<String> {
    private HashMap<String, Integer> distances;

    public CompareUsingDistanceHashMap(HashMap<String, Integer> distances) {
        this.distances = distances;
    }

    public int compare(String a, String b) {
        return Integer.compare(distances.get(a), distances.get(b));
    }
}
