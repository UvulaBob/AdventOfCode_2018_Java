import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part1 {
    private static ArrayList<Star> stars = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(new File("").getAbsolutePath() + "\\src\\input.txt"));

        for (String line : lines) {
            String[] splitLine = line.split(",");
            Star newStar = new Star();
            newStar.name = String.valueOf(stars.size());
            newStar.a = Integer.parseInt(splitLine[0]);
            newStar.b = Integer.parseInt(splitLine[1]);
            newStar.c = Integer.parseInt(splitLine[2]);
            newStar.d = Integer.parseInt(splitLine[3]);
            stars.add(newStar);
        }

        ArrayList<Constellation> constellations = new ArrayList<>();
        for (Star currentStar : stars) {
            boolean hasCloseStar = false;
            for (Constellation constellation : constellations) {
                for (Star constellationStar : constellation.memberStars) {
                    if (currentStar.isCloseTo(constellationStar)) {
                        hasCloseStar = true;
                        constellation.memberStars.add(currentStar);
                    }
                    if (hasCloseStar) {
                        break;
                    }
                }
                if (hasCloseStar) {
                    break;
                }
            }
            if (!hasCloseStar) {
                Constellation newConstellation = new Constellation();
                newConstellation.memberStars.add(currentStar);
                constellations.add(newConstellation);
            }
        }

        for (int currentConstellationIndex = 0; currentConstellationIndex < constellations.size(); currentConstellationIndex++) {
            Constellation currentConstellation = constellations.get(currentConstellationIndex);
            for (int currentStarIndex = 0; currentStarIndex < currentConstellation.memberStars.size(); currentStarIndex++) {
                Star currentStar = currentConstellation.memberStars.get(currentStarIndex);
                for (int targetConstellationIndex = 0; targetConstellationIndex < constellations.size(); targetConstellationIndex++) {
                    if (currentConstellationIndex == targetConstellationIndex) {
                        continue;
                    }
                    Constellation targetConstellation = constellations.get(targetConstellationIndex);
                    for (Star targetStar : targetConstellation.memberStars) {
                        if (currentStar.isCloseTo(targetStar)) {
                            currentConstellation.memberStars.addAll(targetConstellation.memberStars);
                            constellations.remove(targetConstellationIndex);
                            targetConstellationIndex--;
                            break;
                        }
                    }
                }
            }
        }

        System.out.println("Constellations: " + constellations.size());
        System.out.println("Done!");


    }
}
