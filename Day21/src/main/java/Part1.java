import Operations.Addr;
import Operations.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part1 {
    private static HashMap<String, Operation> operations = new HashMap<>();
    private static ArrayList<Instruction> instructions = new ArrayList<>();
    private static Long ipRegister;


    public static void main(String[] args) throws IOException {
        operations.put("addi", Addi.getInstance());
        operations.put("addr", Addr.getInstance());
        operations.put("bani", Bani.getInstance());
        operations.put("banr", Banr.getInstance());
        operations.put("bori", Bori.getInstance());
        operations.put("borr", Borr.getInstance());
        operations.put("eqir", Eqir.getInstance());
        operations.put("eqri", Eqri.getInstance());
        operations.put("eqrr", Eqrr.getInstance());
        operations.put("gtir", Gtir.getInstance());
        operations.put("gtri", Gtri.getInstance());
        operations.put("gtrr", Gtrr.getInstance());
        operations.put("muli", Muli.getInstance());
        operations.put("mulr", Mulr.getInstance());
        operations.put("seti", Seti.getInstance());
        operations.put("setr", Setr.getInstance());

        List<String> lines = Files.readAllLines(Paths.get("C:\\workspace\\AoC2018\\Java\\Day21\\src\\main\\resources\\input.txt"));

        for (String line : lines){
            if (line.contains("#")) {
                ipRegister = Long.parseLong(line.split("#ip ")[1]);
            } else {
                Instruction newInstruction = new Instruction();
                String[] splitInstruction = line.split(" ");
                newInstruction.operation = operations.get(splitInstruction[0]);
                newInstruction.parameters.add(0L);
                newInstruction.parameters.add(Long.parseLong(splitInstruction[1]));
                newInstruction.parameters.add(Long.parseLong(splitInstruction[2]));
                newInstruction.parameters.add(Long.parseLong(splitInstruction[3]));
                instructions.add(newInstruction);
            }
        }


        Long instructionNumberToExecute = 0L;
        ArrayList<Long> registers = new ArrayList<>(Arrays.asList(0L, 0L, 0L, 0L, 0L, 0L));
        while (true) {
            StringBuilder sb = new StringBuilder();
            if (instructionNumberToExecute >= instructions.size()) {
                break;
            }
            Instruction instructionToExecute = instructions.get(instructionNumberToExecute.intValue());
            sb.append(String.format("#%s, %s, %s, ", instructionNumberToExecute, registers, instructionToExecute));
            registers = instructionToExecute.operation.compute(registers, instructionToExecute.parameters);
            Long nextInstructionNumberToExecute = registers.get(ipRegister.intValue()) + 1;
            registers.set(ipRegister.intValue(), nextInstructionNumberToExecute);
            sb.append(String.format("%s", registers));
            System.out.println(sb);
            instructionNumberToExecute = nextInstructionNumberToExecute;
        }

        System.out.println("Final register values: " + registers.toString());
        System.out.println("Done!");
    }
}