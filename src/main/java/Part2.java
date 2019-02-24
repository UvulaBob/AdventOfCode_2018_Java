import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Part2 {

    public static void main(String[] args) throws IOException {
        String input = Files.readAllLines(Paths.get("C:\\Users\\UvulaBob\\IdeaProjects\\AoC2018\\Java\\Day5\\src\\main\\resources\\input.txt")).get(0);

        HashMap<String, Integer> letters = new HashMap<>();
        for (String letter : "abcdefghijklmnopqrstuvwxyz".split("")) {
            letters.put(letter, null);
        }

        for (String letter : letters.keySet()) {
            System.out.println(String.format("Removing '%s/%s'", letter.toUpperCase(), letter.toLowerCase()));
            String modifiedInput = input;
            modifiedInput = modifiedInput.replace(letter.toUpperCase(), "");
            modifiedInput = modifiedInput.replace(letter.toLowerCase(), "");

            ArrayList<String> characters = new ArrayList<>(Arrays.asList(modifiedInput.split("")));

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

            letters.put(letter, sb.length());

        }

        System.out.println("Character Counts: " + letters.toString());

        Pair<String, Integer> shortestLetter = new Pair<>("a", 99999999);
        for (String letter : letters.keySet()) {
            int shortestLetterValue = shortestLetter.getValue();
            int currentLetterValue =  letters.get(letter);
            if (currentLetterValue < shortestLetterValue){
                shortestLetter = new Pair<>(letter, currentLetterValue);
            }
        }

        System.out.println("Shortest Polymer Length: " + shortestLetter.getValue());
        System.out.println("Done!");
    }

}
