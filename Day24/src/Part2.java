import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part2 {
    private static ArrayList<Group> groups;
    private static HashMap<Group, Group> faceOffs;
    public static void main (String[] args) throws IOException{
        int boost = 0;
        boolean immunityWins = false;
        int remainingImmunityUnits = 0;

        while (!immunityWins) {
            initialize();
            for (Group group : groups) {
                if (group.loyalty == Loyalty.IMMUNITY) {
                    group.attackDamagePerUnit += boost;
                }
            }
            boolean stillFighting = true;
            int previousInfectionUnitTotal = 0;
            int previousImmunityUnitTotal = 0;
            boolean stalemate = false;
            while (stillFighting) {
                targetSelection();
                fightFightFight();
                int infectionUnitTotal = 0;
                int immunityUnitTotal = 0;
                for (Group group : groups) {
                    if (group.loyalty == Loyalty.IMMUNITY) {
                        immunityUnitTotal += group.currentUnits;
                    }
                    if (group.loyalty == Loyalty.INFECTION) {
                        infectionUnitTotal += group.currentUnits;
                    }
                }
                stillFighting = (infectionUnitTotal > 0 && immunityUnitTotal > 0);
                if (infectionUnitTotal == previousInfectionUnitTotal && immunityUnitTotal == previousImmunityUnitTotal) {
                    stillFighting = false;
                    stalemate = true;
                    continue;
                }
                previousImmunityUnitTotal = immunityUnitTotal;
                previousInfectionUnitTotal = infectionUnitTotal;

            }

            if (!stalemate && previousImmunityUnitTotal > previousInfectionUnitTotal) {
                immunityWins = true;
                remainingImmunityUnits = previousImmunityUnitTotal;
            } else {
                boost++;
            }

        }

        System.out.println("Immunity wins with a minimum boost of " + boost);
        System.out.println("Remaining Immunity units: " + remainingImmunityUnits);
        System.out.println("Done!");

    }

    private static void fightFightFight() {
        Queue<Group> queue = new PriorityQueue<>(new CompareUsingInitiative());
        queue.addAll(faceOffs.keySet());
        while (queue.size() > 0) {
            Group attacker = queue.poll();
            Group target = faceOffs.get(attacker);
            int targetHitPoints = target.currentUnits * target.hitPointsPerUnit;
            targetHitPoints -= attacker.effectivePower();
            if (target.weaknesses.contains(attacker.damageType)) {
                targetHitPoints -= attacker.effectivePower();
            }
            if (targetHitPoints <= 0) {
                queue.remove(target);
                groups.remove(target);
                continue;
            }
            int remainingUnits = targetHitPoints / target.hitPointsPerUnit;
            int remainder = targetHitPoints % target.hitPointsPerUnit;
            if (remainder != 0) {
                remainingUnits++;
            }
            target.currentUnits = remainingUnits;
        }
    }

    private static void targetSelection() {
        Collections.sort(groups);
        faceOffs = new HashMap<>();
        for (Group attacker : groups) {
            Group normalTarget = null;
            for (Group target : groups) {
                if (target.name.equals(attacker.name) || target.loyalty == attacker.loyalty || faceOffs.values().contains(target)) {
                    continue;
                }
                if (target.weaknesses.contains(attacker.damageType)) {
                    faceOffs.putIfAbsent(attacker, target);
                    if (target.effectivePower() > faceOffs.get(attacker).effectivePower()) {
                        faceOffs.put(attacker, target);
                    } else if (target.effectivePower() == faceOffs.get(attacker).effectivePower()) {
                        if (target.initiative > faceOffs.get(attacker).initiative) {
                            faceOffs.put(attacker, target);
                        }
                    }
                } else if (!target.immunities.contains(attacker.damageType)) {
                    if (normalTarget == null) {
                        normalTarget = target;
                    } else if (target.effectivePower() > normalTarget.effectivePower()) {
                            normalTarget = target;
                    } else if (target.effectivePower() > normalTarget.effectivePower()) {
                        if (target.initiative > normalTarget.initiative) {
                            normalTarget = target;
                        }
                    }
                }
            }
            if (normalTarget != null) {
                faceOffs.putIfAbsent(attacker, normalTarget);
            }
        }
    }

    private static void initialize() throws IOException {
        groups = new ArrayList<>();
        faceOffs = new HashMap<>();

        List<String> lines = Files.readAllLines(Paths.get(new File("").getAbsolutePath() + "\\src\\input.txt"));

        Loyalty currentLoyalty = Loyalty.IMMUNITY;
        int infectionGroup = 1;
        int immunityGroup = 1;
        Pattern mainPattern = Pattern.compile("([0-9]*).* units each with ([0-9]*) hit points.* with an attack that does ([0-9]*) (.*) damage at initiative ([0-9]*)");
        Pattern immunityPattern = Pattern.compile("immune to .*?(;|\\))");
        Pattern weaknessPattern = Pattern.compile("weak to .*?(;|\\))");

        for (String line : lines) {
            if (line.contains("Immune")) {
                currentLoyalty = Loyalty.IMMUNITY;
                continue;
            } else if (line.contains("Infection")) {
                currentLoyalty = Loyalty.INFECTION;
                continue;
            } else if (line.isEmpty()) {
                continue;
            }

            Group newGroup = new Group();
            newGroup.loyalty = currentLoyalty;
            if (newGroup.loyalty == Loyalty.IMMUNITY) {
                newGroup.name = "Immune group " + immunityGroup;
                immunityGroup++;
            } else {
                newGroup.name = "Infection group " + infectionGroup;
                infectionGroup++;
            }

            Matcher m = mainPattern.matcher(line);
            if (m.find()) {
                newGroup.startingUnits = Integer.parseInt(m.group(1));
                newGroup.currentUnits = Integer.parseInt(m.group(1));
                newGroup.hitPointsPerUnit = Integer.parseInt(m.group(2));
                newGroup.attackDamagePerUnit = Integer.parseInt(m.group(3));
                newGroup.damageType = m.group(4);
                newGroup.initiative = Integer.parseInt(m.group(5));
            }

            m = immunityPattern.matcher(line);

            if (m.find()) {
                String[] immunities = m.group(0).replace("immune to ", "").replace(";", "").replace(")", "").split(", ");
                newGroup.immunities.addAll(Arrays.asList(immunities));
            }

            m = weaknessPattern.matcher(line);

            if (m.find()) {
                String[] weaknesses = m.group(0).replace("weak to ", "").replace(";", "").replace(")", "").split(", ");
                newGroup.weaknesses.addAll(Arrays.asList(weaknesses));
            }

            groups.add(newGroup);
        }
    }
}
