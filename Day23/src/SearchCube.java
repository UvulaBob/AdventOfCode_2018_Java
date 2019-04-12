
public class SearchCube{

    int x;
    int y;
    int z;
    int size;
    int botCount;
    int distance;

    SearchCube (int x, int y, int z, int size) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;
    }

    public String toString() {
        return String.format("[%s, %s, %s] ", x, y, z) +
                String.format("[Size: %s] ", size) +
                String.format("[Distance: %s] ", distance) +
                String.format("[Bot Count: %s] ", botCount);
    }
}
