import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
public class Part1 {

    public static void main(String[] args) throws IOException {
        String input = Files.readAllLines(Paths.get(new File("").getAbsolutePath() + "\\src\\input.txt")).get(0);

        ArrayList<Integer> values = new ArrayList<>();
        for (String value : input.split(" ")) {
            values.add(Integer.parseInt(value));
        }

        Node rootNode = createNode(values);

        System.out.println("Data Element Total: " + calculateBasicDataElementTotal(rootNode));
        System.out.println("Done!");

    }

    static private Node createNode(ArrayList<Integer> values) {
        Node newNode = new Node();
        newNode.pendingChildNodes = values.get(0);
        values.remove(0);
        newNode.pendingDataElements = values.get(0);
        values.remove(0);
        while (newNode.pendingChildNodes > 0) {
            newNode.childNodes.add(createNode(values));
            newNode.pendingChildNodes--;
        }

        while (newNode.pendingDataElements > 0) {
            newNode.dataElements.add(values.get(0));
            values.remove(0);
            newNode.pendingDataElements--;
        }

        return newNode;
    }

    static private int calculateBasicDataElementTotal(Node node){
        int total = 0;
        for (Node childNode : node.childNodes ) {
            total += calculateBasicDataElementTotal(childNode);
        }

        return total + node.getDataElementSum();
    }
}

