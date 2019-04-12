import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part2 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(new File("").getAbsolutePath() + "\\src\\input.txt"));
        HashMap<String, Guard> guards = new HashMap<>();

        lines.sort(String.CASE_INSENSITIVE_ORDER);

        Guard currentGuard = null;
        int asleepMinute = 0;

        for (String line : lines) {
            String pattern = "(\\[.*\\])\\s(.*)";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(line);
            String dateTime = "";
            String action = "";

            if (m.find()) {
                dateTime = m.group(1).replaceAll("\\[|\\]", "");
                action = m.group(2);
            }

            if (action.contains("begins")) {
                String guardId = action.split(" ")[1].split("#")[1];
                guards.putIfAbsent(guardId, new Guard(guardId));
                currentGuard = guards.get(guardId);

            }

            if (action.contains("asleep")) {
                asleepMinute = Integer.parseInt(dateTime.split(" ")[1].split(":")[1]);
            }

            if (action.contains("wakes")) {
                int awakeMinute = Integer.parseInt(dateTime.split(" ")[1].split(":")[1]);
                for (int i = asleepMinute; i < awakeMinute; i++){
                    currentGuard.addAsleepMinute(i);
                }
            }
        }
        Guard guardWithMostFrequentAsleepMinute = (Guard) guards.values().toArray()[0];
        for (Guard guard : guards.values()) {
            if (guard.getSleepiestMinute().getValue() > guardWithMostFrequentAsleepMinute.getSleepiestMinute().getValue()) {
                guardWithMostFrequentAsleepMinute = guard;
            }
        }

        System.out.println("Guard With Most Frequent Asleep Minute: " + guardWithMostFrequentAsleepMinute.id);
        System.out.println("Guard's Most Asleep Minute: " + guardWithMostFrequentAsleepMinute.getSleepiestMinute().getKey());
        System.out.println("Answer: " + Integer.parseInt(guardWithMostFrequentAsleepMinute.id) * guardWithMostFrequentAsleepMinute.getSleepiestMinute().getKey());

    }
}
