import java.util.Comparator;

public class SearchCubeComparator implements Comparator<SearchCube> {


    public int compare(SearchCube a, SearchCube b) {
        if (a.botCount > b.botCount) {
            return -1;
        } else if (a.botCount < b.botCount) {
            return 1;
        }

        if (a.distance < b.distance) {
            return -1;
        } else if (a.distance > b.distance) {
            return 1;
        }

        if (a.size < b.size) {
            return -1;
        } else if (a.size > b.size) {
            return 1;
        }

        return 0;
    }


}
