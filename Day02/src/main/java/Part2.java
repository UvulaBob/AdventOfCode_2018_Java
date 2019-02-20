import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part2 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\UvulaBob\\IdeaProjects\\AoC2018\\Java\\Day2\\src\\main\\resources\\input.txt"));
        StringBuilder commonLetters = new StringBuilder();

        for (int currentWordIndex = 0; currentWordIndex < lines.size(); currentWordIndex++) {
            String currentWord = lines.get(currentWordIndex);

            for (int otherWordIndex = lines.indexOf(currentWord) + 1; otherWordIndex < lines.size(); otherWordIndex++) {
                ArrayList<Integer> nonMatches = new ArrayList<>();
                String otherWord = lines.get(otherWordIndex);
                for (int characterIndex = 0; characterIndex < currentWord.length(); characterIndex++) {
                    if (currentWord.charAt(characterIndex) != otherWord.charAt(characterIndex)) {
                        nonMatches.add(characterIndex);
                        if (nonMatches.size() > 1) {
                            break;
                        }
                    }
                }

                if (nonMatches.size() == 1) {
                    commonLetters = new StringBuilder(currentWord);
                    commonLetters.deleteCharAt(nonMatches.get(0));
                }
            }
        }

        System.out.println("Found it! ");
        System.out.println(commonLetters.toString());
        System.out.println("Done!");
    }
}
