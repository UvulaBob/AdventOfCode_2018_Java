import java.util.HashSet;

class Claim implements Comparable<Claim> {
    int id, row, column, height, width;
    HashSet<String> coverage = new HashSet<>();
    boolean overlaps = false;

    void generateCoverage() {
        for (int i = column; i < column + width; i++) {
            for (int j = row; j < row + height; j++) {
                coverage.add(j + "," + i);
            }
        }
    }

    public String toString() {
        return String.valueOf(id);
    }

    public int compareTo(Claim c) {
        return Integer.compare(c.coverage.size(), coverage.size());
    }

}

