import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part2 {
    private static ArrayList<Bot> bots = new ArrayList<>();
    private static Queue<SearchCube> queue = new PriorityQueue<>(new SearchCubeComparator());


    public static void main (String[] args) throws IOException{
        List<String> lines = Files.readAllLines(Paths.get("C:\\workspace\\AoC2018\\Java\\Day23\\src\\main\\resources\\input.txt"));

        for (String line: lines) {
            line = line.replace("pos=<", "");
            line = line.replace(">", "");
            line = line.replace(" r=", "");
            String[] array = line.split(",");

            int x = Integer.parseInt(array[0]);
            int y = Integer.parseInt(array[1]);
            int z = Integer.parseInt(array[2]);
            int range = Integer.parseInt(array[3]);

            Bot newNano = new Bot(x, y, z, range);

            bots.add(newNano);
        }

        int minBotX = bots.get(0).x;
        int minBotY = bots.get(0).y;
        int minBotZ = bots.get(0).z;

        int maxBotX = bots.get(0).x;
        int maxBotY = bots.get(0).y;
        int maxBotZ = bots.get(0).z;

        for (Bot bot : bots) {
            minBotX = (bot.x < minBotX) ? bot.x : minBotX;
            minBotY = (bot.y < minBotY) ? bot.y : minBotY;
            minBotZ = (bot.z < minBotZ) ? bot.z : minBotZ;
            maxBotX = (bot.x > maxBotX) ? bot.x : maxBotX;
            maxBotY = (bot.y > maxBotY) ? bot.y : maxBotY;
            maxBotZ = (bot.z > maxBotZ) ? bot.z : maxBotZ;
        }

        int botCubeSize = Math.abs(maxBotX - minBotX) * Math.abs(maxBotY - minBotY) * Math.abs(maxBotZ - minBotZ);
        int firstSearchCubeSize = 0;
        int exponent = 1;
        while (firstSearchCubeSize < botCubeSize) {
            firstSearchCubeSize = (int) Math.pow(2, exponent);
            exponent++;
        }

        int startingPoint = (firstSearchCubeSize / 2 ) * -1;
        SearchCube firstSearchCube = new SearchCube(startingPoint, startingPoint, startingPoint, firstSearchCubeSize);
        firstSearchCube.distance = 0;
        for (Bot bot : bots) {
            if (botInsideCube(firstSearchCube, bot)) {
                firstSearchCube.botCount++;
            } else if (cubeInRangeOfBot(firstSearchCube, bot)) {
                firstSearchCube.botCount++;
            }
        }

        queue.add(firstSearchCube);

        findClosestPointInRangeOfMostBots();
        System.out.println("Done!");
    }

    private static void findClosestPointInRangeOfMostBots() {
        while (true) {
            SearchCube searchCube = queue.poll();
            if (searchCube != null) {
                if (searchCube.size == 0) {
                    System.out.println(searchCube);
                    return;
                } else {
                    queue.addAll(divideSearchCube(searchCube));
                }
            }
        }
    }


    private static ArrayList<SearchCube> divideSearchCube(SearchCube bigCube){

        ArrayList<SearchCube> searchCubes = new ArrayList<>(8);

        int newSize = bigCube.size / 2;

        searchCubes.add(new SearchCube(bigCube.x, bigCube.y, bigCube.z, newSize));
        searchCubes.add(new SearchCube(bigCube.x, bigCube.y, bigCube.z + newSize, newSize));
        searchCubes.add(new SearchCube(bigCube.x, bigCube.y + newSize, bigCube.z, newSize));
        searchCubes.add(new SearchCube(bigCube.x, bigCube.y + newSize, bigCube.z + newSize, newSize));
        searchCubes.add(new SearchCube(bigCube.x + newSize, bigCube.y, bigCube.z, newSize));
        searchCubes.add(new SearchCube(bigCube.x + newSize, bigCube.y, bigCube.z + newSize, newSize));
        searchCubes.add(new SearchCube(bigCube.x + newSize, bigCube.y + newSize, bigCube.z, newSize));
        searchCubes.add(new SearchCube(bigCube.x + newSize, bigCube.y + newSize, bigCube.z + newSize, newSize));

        for (int i = 0; i < searchCubes.size(); i++) {
            SearchCube searchCube = searchCubes.get(i);
                searchCube.distance = Math.abs(searchCube.x) + Math.abs(searchCube.y) + Math.abs(searchCube.z);

                int botsInRangeOfCube = 0;
                for (Bot bot : bots) {
                    if (botInsideCube(searchCube, bot)) {
                        botsInRangeOfCube++;
                    } else if (cubeInRangeOfBot(searchCube, bot)) {
                        botsInRangeOfCube++;
                    }
                }
                if (botsInRangeOfCube > 0) {
                    searchCube.botCount = botsInRangeOfCube;
                } else {
                    searchCubes.remove(i);
                    i--;
                }
            }

        return searchCubes;
    }

    private static boolean botInsideCube(SearchCube cube, Bot bot) {
        if (bot.x >= cube.x && bot.x < cube.x + cube.size) {
            if (bot.y >= cube.y && bot.y < cube.y + cube.size) {
                return bot.z >= cube.z && bot.z < cube.z + cube.size;
            }
        }

        return false;
    }

    private static boolean cubeInRangeOfBot (SearchCube cube, Bot bot) {
        int closestX, closestY, closestZ;

        if (bot.x > cube.x + cube.size) {
            closestX = cube.x + cube.size;
        } else if (bot.x < cube.x) {
            closestX = cube.x;
        } else {
            closestX = bot.x;
        }

        if (bot.y > cube.y + cube.size) {
            closestY = cube.y + cube.size;
        } else if (bot.y < cube.y) {
            closestY = cube.y;
        } else {
            closestY = bot.y;
        }

        if (bot.z > cube.z + cube.size) {
            closestZ = cube.z + cube.size;
        } else if (bot.z < cube.z) {
            closestZ = cube.z;
        } else {
            closestZ = bot.z;
        }

        int deltaX = Math.abs(bot.x - closestX);
        int deltaY = Math.abs(bot.y - closestY);
        int deltaZ = Math.abs(bot.z - closestZ);
        int deltaSum = deltaX + deltaY + deltaZ;

        return bot.range >= deltaSum;

    }
}
