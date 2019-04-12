import javafx.util.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {
    private static HashMap<Point, Occupant> worldMap = new HashMap<>();
    private static int highestClayY, lowestClayY;
    //private static Point upperLeft, lowerRight;

    public static void main(String[] args) throws IOException{
        initialize();
        dropAndFill((new Point(500, 1)));

        int numberOfWaterTiles = 0;
        for (Map.Entry<Point, Occupant> entry : worldMap.entrySet()) {
            Point point = entry.getKey();
            Occupant occupant = entry.getValue();
            if (occupant instanceof Water && point.y >= lowestClayY) {
                numberOfWaterTiles++;
            }
        }


        //findRenderCorners();
        //Renderer renderer = Renderer.getInstance();
        //renderer.drawMap(worldMap, upperLeft, lowerRight);
        System.out.println("Done!");
        System.out.println(String.format("There are %s water tiles.", numberOfWaterTiles));


    }

    private static void dropAndFill(Point startingPoint) {
        System.out.println("Starting a drop from " + startingPoint.toString());
        Point stoppingPoint = dropUntilImpact(startingPoint);
        if (stoppingPoint != null) {
            Point rowSpreadPoint = stoppingPoint.copy();
            while (true) {
                Pair<Point, BorderType> leftBorder = getRowBorder(rowSpreadPoint, ScanDirection.LEFT);
                Point leftBorderPoint = leftBorder.getKey();
                BorderType leftBorderType = leftBorder.getValue();

                if (leftBorderType == BorderType.WATERFALL) {
                    System.out.println(String.format("Waterfall found to the left at %s. We're done with this row.", leftBorderPoint));
                    break;
                }

                Pair<Point, BorderType> rightBorder = getRowBorder(rowSpreadPoint, ScanDirection.RIGHT);
                Point rightBorderPoint = rightBorder.getKey();
                BorderType rightBorderType = rightBorder.getValue();

                if (rightBorderType == BorderType.WATERFALL) {
                    System.out.println(String.format("Waterfall found to the right at %s. We're done with this row.", rightBorderPoint));
                    break;
                }

                if (leftBorderType == BorderType.WALL) {
                    System.out.println(String.format("Wall found to the left at %s", leftBorderPoint));
                    for (int x = leftBorderPoint.x + 1; x < rowSpreadPoint.x; x++) {
                        Point newWaterPoint = new Point(x, rowSpreadPoint.y);
                        putWaterAtPoint(newWaterPoint);
                    }
                }

                if (rightBorderType == BorderType.WALL) {
                    System.out.println(String.format("Wall found to the right at %s", rightBorderPoint));
                    for (int x = rowSpreadPoint.x + 1; x < rightBorderPoint.x; x++) {
                        Point newWaterPoint = new Point(x, rowSpreadPoint.y);
                        putWaterAtPoint(newWaterPoint);
                    }
                }

                if (leftBorderType == BorderType.CLIFF) {
                    System.out.println(String.format("Cliff found to the left at %s.", leftBorderPoint));
                    fillRowSectionLeftToRight(leftBorderPoint, rowSpreadPoint);
                    dropAndFill(leftBorderPoint);
                }

                if (rightBorderType == BorderType.CLIFF) {
                    System.out.println(String.format("Cliff found to the right at %s.", rightBorderPoint));
                    fillRowSectionLeftToRight(rowSpreadPoint, rightBorderPoint);
                    dropAndFill(rightBorderPoint);
                }

                System.out.print(String.format("Raising rowSpreadPoint from %s", rowSpreadPoint));
                rowSpreadPoint.y--;
                System.out.println(String.format(" to %s", rowSpreadPoint));
                if (rowSpreadPoint.y < startingPoint.y) {
                    System.out.println(String.format("We've filled higher than the starting point %s. Done filling.", startingPoint));
                    break;
                }
            }
        }
    }

    private static void fillRowSectionLeftToRight(Point startPoint, Point endPoint) {
        for (int x = startPoint.x + 1; x < endPoint.x; x++) {
            Point newWaterPoint = new Point(x, startPoint.y);
            putWaterAtPoint(newWaterPoint);
        }
    }

    private static Pair<Point, BorderType> getRowBorder(Point startingPoint, ScanDirection direction) {
        int xModifier = (direction == ScanDirection.LEFT) ? -1 : 1;
        Point currentPoint = startingPoint.copy();

        while (true){
            currentPoint.x += xModifier;
            Occupant currentPointOccupant = worldMap.get(currentPoint);
            if (currentPointOccupant == null) {
                Occupant occupantBelowCurrentPoint = worldMap.get(currentPoint.getBelowPoint());
                if (occupantBelowCurrentPoint == null) {
                    ScanDirection whereWeCameFrom = (direction == ScanDirection.LEFT) ? ScanDirection.RIGHT : ScanDirection.LEFT;
                    Occupant occupantBelowPreviousPoint =
                            worldMap.get(currentPoint.getBelowPointInDirection(whereWeCameFrom));
                    if (occupantBelowPreviousPoint instanceof Water) {
                        return new Pair<>(currentPoint, BorderType.WATERFALL);
                    } else if (occupantBelowPreviousPoint instanceof Clay) {
                        return new Pair<>(currentPoint, BorderType.CLIFF);
                    }
                }
            } else if (currentPointOccupant instanceof Clay){
                return new Pair<>(currentPoint, BorderType.WALL);
            }
        }
    }

    private static void putWaterAtPoint(Point currentPoint) {
        System.out.println("Putting Water at " + currentPoint);
        worldMap.put(currentPoint.copy(), new Water());
    }


    private static Point dropUntilImpact(Point startingPoint) {
        Point currentPoint = startingPoint.copy();
        while (true) {
            putWaterAtPoint(currentPoint);
            Point onePointBelow = new Point(currentPoint.x, currentPoint.y + 1);
            if (onePointBelow.y > highestClayY) {
                System.out.println("We've reached infinity at " + currentPoint);
                return null;
            }
            if (worldMap.get(onePointBelow) != null) {
                System.out.println("We've hit something! Stopping at " + currentPoint);
                return currentPoint;
            }
            currentPoint.y++;
        }
    }

    private static void initialize() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(new File("").getAbsolutePath() + "\\src\\input.txt"));
        Pattern xPattern = Pattern.compile("x=([0-9]*)..([0-9]*)");
        Pattern yPattern = Pattern.compile("y=([0-9]*)..([0-9]*)");

        for (String line : lines) {

            int xRangeMin = 0;
            int xRangeMax = 0;
            Matcher xMatcher = xPattern.matcher(line);
            if (xMatcher.find()) {
                xRangeMin = Integer.parseInt(xMatcher.group(1));
                xRangeMax = xRangeMin;
                if (!xMatcher.group(2).equals("")) {
                    xRangeMax = Integer.parseInt(xMatcher.group(2));
                }
            }

            int yRangeMin = 0;
            int yRangeMax = 0;
            Matcher yMatcher = yPattern.matcher(line);
            if (yMatcher.find()) {
                yRangeMin = Integer.parseInt(yMatcher.group(1));
                yRangeMax = yRangeMin;
                if (!yMatcher.group(2).equals("")) {
                    yRangeMax = Integer.parseInt(yMatcher.group(2));
                }
            }

            for (int x = xRangeMin; x <= xRangeMax; x++) {
                for (int y = yRangeMin; y <= yRangeMax; y++) {
                    worldMap.put(new Point(x, y), new Clay());
                }
            }
        }

        findHighestAndLowestClayPoints();
        worldMap.put(new Point(500, 0), new Spring());
    }

    private static void findHighestAndLowestClayPoints() {
        Map.Entry<Point, Occupant> entry = worldMap.entrySet().iterator().next();
        Point firstPoint = entry.getKey();
        int lowestY = firstPoint.y;
        int highestY = firstPoint.y;

        for (Point point : worldMap.keySet()) {
            if (point.y > highestY) {
                highestY = point.y;
            }

            if (point.y < lowestY) {
                lowestY = point.y;
            }
        }

        highestClayY = highestY;
        lowestClayY = lowestY;

    }
//
//    private static void findRenderCorners() {
//        Map.Entry<Point, Occupant> entry = worldMap.entrySet().iterator().next();
//        Point firstPoint = entry.getKey();
//        int lowestX = firstPoint.x;
//        int highestX = firstPoint.x;
//        int lowestY = firstPoint.y;
//        int highestY = firstPoint.y;
//
//        for (Point point : worldMap.keySet()) {
//            if (point.x > highestX) {
//                highestX = point.x;
//            }
//
//            if (point.x < lowestX) {
//                lowestX = point.x;
//            }
//
//            if (point.y > highestY) {
//                highestY = point.y;
//            }
//
//            if (point.y < lowestY) {
//                lowestY = point.y;
//            }
//        }
//
//        upperLeft = new Point(lowestX, lowestY);
//        lowerRight = new Point(highestX, highestY);
//
//    }
}
