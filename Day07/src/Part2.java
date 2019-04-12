import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Part2 {

    public static void main(String[] args) throws IOException{
        List<String> requirements = Files.readAllLines(Paths.get(new File("").getAbsolutePath() + "\\src\\input.txt"));
        ArrayList<Step> steps= new ArrayList<>();
        HashMap<String, Integer> stepTimes = new HashMap<>();
        final int timeAddition = 60;
        final int totalWorkers = 5;
        ArrayList<Worker> workers = new ArrayList<>();
        int currentTime = 0;

        for (int i = 0; i < totalWorkers; i++) {
            workers.add(new Worker());
        }

        for (String letter : "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("")){
            stepTimes.put(letter, stepTimes.size() + 1 + timeAddition);
        }

        // Seed step list with steps that need to be completed before other steps, which means they must be performed.
        for (String requirement : requirements) {
            String[] splitString = requirement.split(" must be finished before step ");
            String stepName = splitString[0].replace("Step ", "");

            boolean stepFound = false;
            for (Step step : steps){
                if (step.name.equals(stepName)) {
                    stepFound = true;
                    break;
                }
            }
            if (!stepFound) {
                Step newStep = new Step();
                newStep.name = stepName;
                newStep.timeLeft = stepTimes.get(stepName);
                steps.add(newStep);
            }
        }

        // For every requirement, find the step that it requires, and add itself to that step.
        for (String requirement : requirements) {
            String[] splitString = requirement.split(" must be finished before step ");
            String stepName = splitString[1].replace(" can begin.", "");;
            String prerequisite = splitString[0].replace("Step ", "");

            boolean stepFound = false;
            for (Step step : steps){
                if (step.name.equals(stepName)) {
                    step.prerequisites.add(prerequisite);
                    stepFound = true;
                    break;
                }
            }
            if (!stepFound) {
                Step newStep = new Step();
                newStep.name = stepName;
                newStep.timeLeft = stepTimes.get(stepName);
                newStep.prerequisites.add(prerequisite);
                steps.add(newStep);
            }
        }


        while (steps.size() > 0) {
            for (Worker worker : workers) {
                if (worker.busy && worker.currentStep.timeLeft == 0) {
                    Step stepToRemove = worker.currentStep;
                    for (Step step : steps) {
                        step.prerequisites.remove(stepToRemove.name);
                    }
                    steps.remove(stepToRemove);
                    worker.busy = false;
                    worker.currentStep = null;
                }
            }

            ArrayList<Step> stepsBeingWorked = new ArrayList<>();
            for (Worker worker : workers) {
                if (worker.busy){
                    stepsBeingWorked.add(worker.currentStep);
                }
            }


            ArrayList<Step> stepsToPerform = new ArrayList<>();
            for (Step step : steps) {
                if (step.prerequisites.size() == 0 && !stepsToPerform.contains(step)) {
                    stepsToPerform.add(step);
                }
            }

            Collections.sort(stepsToPerform);

            for (Step stepToPerform : stepsToPerform) {
                if (stepsBeingWorked.contains(stepToPerform)) {
                    continue;
                }
                for (Worker worker : workers) {
                    if (!worker.busy) {
                        worker.currentStep = stepToPerform;
                        worker.busy = true;
                        break;
                    }
                }
            }

            for (Worker worker : workers) {
                if (worker.busy) {
                    worker.currentStep.timeLeft--;
                }
            }
            currentTime++;
        }



        System.out.println("Done!");
        System.out.println("Total Time: " + (currentTime - 1));

    }

}
