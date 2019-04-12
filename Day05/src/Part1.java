import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Part1 {

    public static void main(String[] args) throws IOException {
        String input = Files.readAllLines(Paths.get(new File("").getAbsolutePath() + "\\src\\input.txt")).get(0);
        ArrayList<String> characters = new ArrayList<>(Arrays.asList(input.split("")));

        for (int i = 0; i < characters.size() - 1; i++) {
            String currentCharacter = characters.get(i);
            String nextCharacter = characters.get(i + 1);
            if (currentCharacter.equalsIgnoreCase(nextCharacter) && !currentCharacter.equals(nextCharacter)) {
                characters.remove(i + 1);
                characters.remove(i);
                i -= 3;

                if (i < 0) {
                    i = -1;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (String character : characters) {
            sb.append(character);
        }

        System.out.println("Polymer: " + sb.toString());
        System.out.println("Character Count: " + sb.length());
        System.out.println("Done!");
    }
}
