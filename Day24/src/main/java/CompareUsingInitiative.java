import java.util.Comparator;

public class CompareUsingInitiative implements Comparator<Group> {

    public int compare(Group g1, Group g2) {
        return Integer.compare(g2.initiative, g1.initiative);
    }
}
