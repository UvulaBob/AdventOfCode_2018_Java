public class ImportantPoint implements Comparable<ImportantPoint>{
    String point;
    int distance;


    ImportantPoint(String point, int distance) {
        this.point = point;
        this.distance = distance;
    }
    public int compareTo(ImportantPoint ip) {
        return Integer.compare(distance, ip.distance);
    }
}
