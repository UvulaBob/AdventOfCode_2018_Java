import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

class Guard {
    String id;
    public HashMap<Integer, Integer> asleepMinutes = new HashMap<>();

    Guard(String id) {
        this.id = id;
    }

    void addAsleepMinute (int minute) {
        asleepMinutes.putIfAbsent(minute, 0);
        asleepMinutes.put(minute, asleepMinutes.get(minute) + 1);
    }

    int getTotalSleepMinutes() {
        int totalSleepMinutes = 0;
        for (int sleepMinute : asleepMinutes.values()) {
            totalSleepMinutes += sleepMinute;
        }
        return totalSleepMinutes;
    }

    Pair<Integer, Integer> getSleepiestMinute() {
        Pair<Integer, Integer> sleepiestMinute = new Pair<>(0, 0);
        for (Map.Entry<Integer, Integer> entry : asleepMinutes.entrySet()) {
            int minute = entry.getKey();
            int timesAsleep = entry.getValue();

            if (timesAsleep > sleepiestMinute.getValue()) {
                sleepiestMinute = new Pair<>(minute, timesAsleep);
            }
        }

        return sleepiestMinute;
    }
}
