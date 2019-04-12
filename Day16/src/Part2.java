import Operations.Addr;
import Operations.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part2 {
    private static ArrayList<Process> processes = new ArrayList<>();
    private static ArrayList<Operation> operations = new ArrayList<>();
    private static HashMap<Operation, ArrayList<Integer>> possibleOpCodes = new HashMap<>();
    private static HashMap<Integer, Operation> operationMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(new File("").getAbsolutePath() + "\\src\\input1.txt"));
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
            for (Operation operation : operations) {
                ArrayList<Integer> actualAfter = operation.compute(process.before, process.instructions);
                if (process.expectedAfter.equals(actualAfter)) {
                    int opCode = process.instructions.get(0);
                    ArrayList<Integer> opCodes = possibleOpCodes.get(operation);
                    if (!opCodes.contains(opCode)) {
                        opCodes.add(opCode);
                    }
                }
            }
        }

        // Use deduction to generate the list of OpCodes and their Operations.
        while (possibleOpCodes.size() > 0) {
            Iterator<Map.Entry<Operation, ArrayList<Integer>>> iterator = possibleOpCodes.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Operation, ArrayList<Integer>> entry = iterator.next();
                ArrayList<Integer> opCodes = entry.getValue();
                if (opCodes.size() == 1) {
                    int opCode = opCodes.get(0);
                    Operation operation = entry.getKey();
                    operationMap.put(opCode, operation);
                    for (ArrayList<Integer> otherOperationOpCodes : possibleOpCodes.values()) {
                        otherOperationOpCodes.remove(Integer.valueOf(opCode));
                    }
                    iterator.remove();
                }
            }
        }

        ArrayList<ArrayList<Integer>> instructions = new ArrayList<>();
        lines = Files.readAllLines(Paths.get(new File("").getAbsolutePath() + "\\src\\input2.txt"));

        for (String line : lines) {
            ArrayList<Integer> newInstructions = new ArrayList<>();
            String[] instructionStringArray = line.split(" ");
            for (String instruction : instructionStringArray) {
                newInstructions.add(Integer.parseInt(instruction));
            }
            instructions.add(newInstructions);
        }

        // Work through the program from Input 2, and spit out the value of register 0.
        ArrayList<Integer> registers = new ArrayList<>(Arrays.asList(0, 0, 0, 0));
        for (ArrayList<Integer> instruction : instructions) {
            int opCode = instruction.get(0);
            Operation operation = operationMap.get(opCode);
            registers = operation.compute(registers, instruction);
        }

        System.out.println(registers.toString());
        System.out.println("Done!");

    }
}