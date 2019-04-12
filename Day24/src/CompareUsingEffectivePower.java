import java.util.Comparator;

public class CompareUsingEffectivePower implements Comparator<Group> {

    public int compare(Group g1, Group g2) {
        return Integer.compare(g2.effectivePower(), g1.effectivePower());
    }
}
