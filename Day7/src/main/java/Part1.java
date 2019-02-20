import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Part1 {

    public static void main(String[] args) throws IOException{
        List<String> requirements = Files.readAllLines(Paths.get("C:\\Users\\UvulaBob\\IdeaProjects\\AoC2018\\Java\\Day7\\src\\main\\resources\\input.txt"));
        ArrayList<Step> steps = new ArrayList<>();

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
                newStep.prerequisites.add(prerequisite);
                steps.add(newStep);
            }
        }

        Collections.sort(steps);


        StringBuilder completedSteps = new StringBuilder();
        while (steps.size() > 0) {
            for (int currentStepNumber = 0; currentStepNumber < steps.size(); currentStepNumber++) {
                Step currentStep = steps.get(currentStepNumber);
                if (currentStep.prerequisites.size() > 0) {
                    continue;
                }

                for (int otherStepNumber = 0; otherStepNumber < steps.size(); otherStepNumber++) {
                    if (otherStepNumber == currentStepNumber) {
                        continue;
                    }

                    Step otherStep = steps.get(otherStepNumber);
                    otherStep.prerequisites.remove(currentStep.name);
                }

                completedSteps.append(currentStep.name);
                steps.remove(currentStep);
                break;
            }
        }


        System.out.println(completedSteps.toString());
        System.out.println("Done!");

    }

}
