import java.util.HashSet;

public class Part2 {

    public static void main(String[] args) {
        long r0 = 0L;
        long r1 = 0L;
        long r2 = 0L;
        long r3 = 0L;
        final long r4 = 0L;
        long r5 = 0L;

        HashSet<Long> pastTargetNumbers = new HashSet<>();

        long lastR1 = 0;
        r3 = r1 | 65536L; //#6
        r1 = 6780005L; //#7

        while (true) {
            r2 = r3 & 255L; //#8
            r1 = r1 + r2; //#9
            r1 = r1 & 16777215L; //#10
            r1 = r1 * 65899L; //#11
            r1 = r1 & 16777215L; //#12

            if (r3 < 256) {  //#13 - 17
                if (pastTargetNumbers.contains(r1)) { // #28
                    break;
                } else {
                    lastR1 = r1;
                    pastTargetNumbers.add(r1);
                    r3 = r1 | 65536L; //#6
                    r1 = 6780005L; //#7
                    continue;
                }
            }

            r2 = r3 / 256L; //#18 - 25
            r3 = r2;  //#26 - 27
        }

        System.out.println("Repeat found. Last r1 value: " + lastR1);
        System.out.println("Done!");
    }


}