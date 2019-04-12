import java.util.HashSet;

public class Group implements Comparable<Group>{
    String name;
    int startingUnits;
    int currentUnits;
    int hitPointsPerUnit;
    int attackDamagePerUnit;
    int initiative;
    Loyalty loyalty;
    String damageType;
    int effectivePower;

    HashSet<String> immunities = new HashSet<>();
    HashSet<String> weaknesses = new HashSet<>();

    public int effectivePower() {
        effectivePower = currentUnits * attackDamagePerUnit;
        return effectivePower;
    }

    public int compareTo(Group g) {
        int result = Integer.compare(g.effectivePower(), this.effectivePower());
        if (result == 0) {
            result = Integer.compare(g.initiative, this.initiative);
        }
        return result;
    }
}
