public class Bot {
    int x;
    int y;
    int z;
    int range;

    Bot(int x, int y, int z, int range) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.range = range;
    }

    public String toString() {
        return String.format("[%s, %s, %s] ", x, y, z) +
                String.format("[Range: %s] ", range);
    }}
