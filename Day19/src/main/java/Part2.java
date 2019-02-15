import Operations.Addr;
import Operations.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part2 {

    // OldPart2.txt has the inefficient "assembunny" code, with a target of 10551374.
    public static void main(String[] args) throws IOException {
        int target = 10551374;
        ArrayList<Integer> factors = new ArrayList<>();


        for (int i = 1; i <= target; i++) {
            if (target % i == 0) {
                factors.add(i);
            }
        }

        int total = 0;
        for (int factor : factors) {
            total += factor;
        }



        System.out.println("Result: " + total);
        System.out.println("Done!");
    }
}