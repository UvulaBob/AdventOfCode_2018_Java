import Operations.Addr;
import Operations.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {
    private static ArrayList<Process> processes = new ArrayList<>();
    private static ArrayList<Operation> operations = new ArrayList<>();
    private static HashMap<Operation, ArrayList<Integer>> possibleOpCodes = new HashMap<>();
    private static HashMap<Integer, Operation> operationMap = new HashMap<>();
    private static int processesWithThreePossibleOperations = 0;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("C:\\workspace\\AoC2018\\Java\\Day16\\src\\main\\resources\\input1.txt"));
        Pattern beforeLinePattern = Pattern.compile("Before: \\[([0-9]), ([0-9]), ([0-9]), ([0-4])]");
        Pattern afterLinePattern = Pattern.compile("After:  \\[([0-9]), ([0-9]), ([0-9]), ([0-4])]");

        for (int lineNumber = 0; lineNumber < lines.size(); lineNumber++) {
            String beforeLine = lines.get(lineNumber);
            Process newProcess = new Process();
            newProcess.before = new ArrayList<>();
            Matcher beforeLineMatcher = beforeLinePattern.matcher(beforeLine);
            if (beforeLineMatcher.find()) {
                newProcess.before.add(Integer.parseInt(beforeLineMatcher.group(1)));
                newProcess.before.add(Integer.parseInt(beforeLineMatcher.group(2)));
                newProcess.before.add(Integer.parseInt(beforeLineMatcher.group(3)));
                newProcess.before.add(Integer.parseInt(beforeLineMatcher.group(4)));
            }

            lineNumber++;
            String[] instructions = lines.get(lineNumber).split(" ");
            newProcess.instructions = new ArrayList<>();
            newProcess.instructions.add(Integer.parseInt(instructions[0]));
            newProcess.instructions.add(Integer.parseInt(instructions[1]));
            newProcess.instructions.add(Integer.parseInt(instructions[2]));
            newProcess.instructions.add(Integer.parseInt(instructions[3]));

            lineNumber++;
            String afterLine = lines.get(lineNumber);
            newProcess.expectedAfter = new ArrayList<>();
            Matcher afterLineMatcher = afterLinePattern.matcher(afterLine);
            if (afterLineMatcher.find()) {
                newProcess.expectedAfter.add(Integer.parseInt(afterLineMatcher.group(1)));
                newProcess.expectedAfter.add(Integer.parseInt(afterLineMatcher.group(2)));
                newProcess.expectedAfter.add(Integer.parseInt(afterLineMatcher.group(3)));
                newProcess.expectedAfter.add(Integer.parseInt(afterLineMatcher.group(4)));
            }

            processes.add(newProcess);
            lineNumber++;
        }

        operations.add(Addi.getInstance());
        operations.add(Addr.getInstance());
        operations.add(Bani.getInstance());
        operations.add(Banr.getInstance());
        operations.add(Bori.getInstance());
        operations.add(Borr.getInstance());
        operations.add(Eqir.getInstance());
        operations.add(Eqri.getInstance());
        operations.add(Eqrr.getInstance());
        operations.add(Gtir.getInstance());
        operations.add(Gtri.getInstance());
        operations.add(Gtrr.getInstance());
        operations.add(Muli.getInstance());
        operations.add(Mulr.getInstance());
        operations.add(Seti.getInstance());
        operations.add(Setr.getInstance());

        for (Operation operation : operations) {
            possibleOpCodes.put(operation, new ArrayList<>());
        }

        for (Process process : processes) {
            int numberOfCompatibleOperations = 0;
            for (Operation operation : operations) {
                ArrayList<Integer> actualAfter = operation.compute(process.before, process.instructions);
                if (process.expectedAfter.equals(actualAfter)) {
                    numberOfCompatibleOperations++;
                    int opCode = process.instructions.get(0);
                    ArrayList<Integer> opCodes = possibleOpCodes.get(operation);
                    if (!opCodes.contains(opCode)) {
                        opCodes.add(opCode);
                    }
                }
            }
            if (numberOfCompatibleOperations > 2) {
                processesWithThreePossibleOperations++;
            }
        }

        System.out.println("Processes with 3+ possible operations: " + processesWithThreePossibleOperations);
        System.out.println("Done!");
    }
}