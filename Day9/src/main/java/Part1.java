import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Part1 {

    public static void main(String[] args) {

        int numberOfPlayers = 477;
//        int numberOfPlayers = 9;
//        int numberOfMarbles = 25;
        int numberOfMarbles = 70851;
        HashMap<Integer, Integer> playersAndPoints = new HashMap<>(numberOfPlayers);

        for (int i = 1; i <= numberOfPlayers; i++) {
            playersAndPoints.put(i, 0);
        }


        CircleDeque<Integer> marbleCircle = new CircleDeque<>();

        // The setup and first three turns are always the same, and coding for them sucks, so here they are.
        marbleCircle.add(0);
        marbleCircle.addLast(2);
        marbleCircle.addLast(1);
        marbleCircle.addLast(3);
        marbleCircle.rotate(1);

        int marbleNumberBeingPlaced = 4;
        int currentPlayer = 4;

        while (marbleNumberBeingPlaced <= numberOfMarbles) {

            if (marbleNumberBeingPlaced % 23 == 0 ) {
                marbleCircle.rotate(7);
                int valueOfTargetMarble = marbleCircle.peekFirst();
                int playerPoints = playersAndPoints.get(currentPlayer) + marbleNumberBeingPlaced + valueOfTargetMarble;
                playersAndPoints.put(currentPlayer, playerPoints);
                marbleCircle.pop();
            } else {
                marbleCircle.rotate(-3);
                marbleCircle.addFirst(marbleNumberBeingPlaced);
            }

            marbleNumberBeingPlaced++;
            currentPlayer++;
            while (currentPlayer > numberOfPlayers) {
                currentPlayer -= numberOfPlayers;
            }
        }

        int highScore = 0;
        for (int score : playersAndPoints.values()) {
            if (score > highScore) {
                highScore = score;
            }
        }

        System.out.println("High score: " + highScore);
        System.out.println("Done!");
    }
}
