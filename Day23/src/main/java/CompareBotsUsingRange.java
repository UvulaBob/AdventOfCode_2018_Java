import java.util.Comparator;

public class CompareBotsUsingRange implements Comparator<Bot> {

    public int compare(Bot a, Bot b) {
        return Integer.compare(b.range, a.range);
    }
}
