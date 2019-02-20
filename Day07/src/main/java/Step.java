import java.util.ArrayList;

class Step implements Comparable<Step>{
    String name;
    ArrayList<String> prerequisites = new ArrayList<>();
    int timeLeft;

    public int compareTo(Step step) {
        return name.compareTo(step.name);
    }
}
