import java.util.ArrayList;

class Node {
    int pendingChildNodes= 0;
    int pendingDataElements = 0;
    ArrayList<Node> childNodes = new ArrayList<>();
    ArrayList<Integer> dataElements = new ArrayList<>();

    int getDataElementSum () {
        int total = 0;
        for (int dataElement : dataElements) {
            total += dataElement;
        }

        return total;
    }

}
