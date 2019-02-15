import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part2 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("C:\\workspace\\AoC2018\\Java\\Day3\\src\\main\\resources\\input.txt"));

        ArrayList<Claim> claims = new ArrayList<>();
        Pattern pattern = Pattern.compile("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)");


        for (String line : lines)
        {
            Claim newClaim = new Claim();
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                newClaim.id = Integer.parseInt(matcher.group(1));
                newClaim.row = Integer.parseInt(matcher.group(3));
                newClaim.column = Integer.parseInt(matcher.group(2));
                newClaim.width = Integer.parseInt(matcher.group(4));
                newClaim.height= Integer.parseInt(matcher.group(5));
                newClaim.generateCoverage();
                claims.add(newClaim);
            }
        }

        Collections.sort(claims);

        for (Claim currentClaim : claims) {
            if (currentClaim.overlaps) {
                continue;
            }

            System.out.println("Current Claim: " + currentClaim);
            for (Claim otherClaim : claims) {
                if (currentClaim.equals(otherClaim)) {
                    continue;
                }

                for (String point : currentClaim.coverage) {
                    if (otherClaim.coverage.contains(point)) {
                        System.out.println("Overlap found with claim " + otherClaim + " at: " + point);
                        currentClaim.overlaps = true;
                        break;
                    }
                }

                if (currentClaim.overlaps) {
                    break;
                }
            }
        }



        HashSet<Integer> goodClaims = new HashSet<>();

        for (Claim claim : claims) {
            if (!claim.overlaps) {
                goodClaims.add(claim.id);
            }
        }

        System.out.println(goodClaims.toString());
        System.out.println("Done!");

    }
}
