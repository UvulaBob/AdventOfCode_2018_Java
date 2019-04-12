import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {

    public static void main(String[] args) throws IOException{
        List<String> lines = Files.readAllLines(Paths.get(new File("").getAbsolutePath() + "\\src\\input.txt"));
        ArrayList<Light> lights = new ArrayList<>();
        StringBuilder grid = new StringBuilder();

        for (String line : lines) {
            Light newLight = new Light();
            Pattern pattern = Pattern.compile("position=<([0-9* -][0-9]*), ([0-9* -][0-9]*)> velocity=<([0-9* -][0-9]*), ([0-9* -][0-9]*)");
            Matcher m = pattern.matcher(line);
            if (m.find()) {
                newLight.x = Long.parseLong(m.group(1).trim());
                newLight.y = Long.parseLong(m.group(2).trim());
                newLight.xVelocity = Long.parseLong(m.group(3).trim());
                newLight.yVelocity = Long.parseLong(m.group(4).trim());
            }
            lights.add(newLight);
        }

        long highestX = Long.MIN_VALUE;
        long highestY = Long.MIN_VALUE;
        long lowestX = Long.MAX_VALUE;
        long lowestY = Long.MAX_VALUE;

        for (Light light : lights) {
            if (light.x > highestX) {
                highestX = light.x;
            }

            if (light.x < lowestX) {
                lowestX = light.x;
            }

            if (light.y > highestY) {
                highestY = light.y;
            }

            if (light.y < lowestY) {
                lowestY = light.y;
            }
        }


        long lastArea = Math.abs(highestX - lowestX + 1) * Math.abs(highestY - lowestY + 1);

        while (true) {

            // Adjust point locations
            for (Light light : lights) {
                light.x += light.xVelocity;
                light.y += light.yVelocity;
            }

            // Calculate grid bounds
            highestX = Long.MIN_VALUE;
            highestY = Long.MIN_VALUE;
            lowestX = Long.MAX_VALUE;
            lowestY = Long.MAX_VALUE;

            for (Light light : lights) {
                if (light.x > highestX) {
                    highestX = light.x;
                }
                if (light.x < lowestX) {
                    lowestX = light.x;
                }
                if (light.y > highestY) {
                    highestY = light.y;
                }
                if (light.y < lowestY) {
                    lowestY = light.y;
                }
            }

            long differenceX = Math.abs(highestX - lowestX + 1L);
            long differenceY = Math.abs(highestY - lowestY + 1L);

            long area = differenceX * differenceY;

            if (area > lastArea) {
                for (Light light : lights) {
                    light.x -= light.xVelocity;
                    light.y -= light.yVelocity;
                }

                // Calculate grid bounds
                highestX = Long.MIN_VALUE;
                highestY = Long.MIN_VALUE;
                lowestX = Long.MAX_VALUE;
                lowestY = Long.MAX_VALUE;

                for (Light light : lights) {
                    if (light.x > highestX) {
                        highestX = light.x;
                    }
                    if (light.x < lowestX) {
                        lowestX = light.x;
                    }
                    if (light.y > highestY) {
                        highestY = light.y;
                    }
                    if (light.y < lowestY) {
                        lowestY = light.y;
                    }
                }

                for (long y = lowestY; y <= highestY; y++) {
                    for (long x = lowestX; x <= highestX; x++) {
                        boolean spotOccupied = false;
                        for (Light light : lights) {
                            if (light.x == x && light.y == y) {
                                spotOccupied = true;
                                break;
                            }
                        }

                        if (spotOccupied) {
                            grid.append("#");
                        } else {
                            grid.append(".");
                        }
                    }
                    grid.append("\r\n");
                }
                break;

            } else {
                lastArea = area;
            }
        }

        System.out.println(grid.toString());
        System.out.println("Done!");
    }
}
