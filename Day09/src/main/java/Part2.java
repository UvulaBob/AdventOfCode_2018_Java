import java.util.HashMap;

public class Part2 {

    public static void main(String[] args) {

        int numberOfPlayers = 477;
        int numberOfMarbles = 7085100;
        HashMap<Integer, Long> playersAndPoints = new HashMap<>(numberOfPlayers);

        for (int i = 1; i <= numberOfPlayers; i++) {
            playersAndPoints.put(i, 0L);
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
                long valueOfTargetMarble = marbleCircle.peekFirst();
                long playerPoints = playersAndPoints.get(currentPlayer) + marbleNumberBeingPlaced + valueOfTargetMarble;
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

        long highScore = 0;
        for (long score : playersAndPoints.values()) {
            if (score > highScore) {
                highScore = score;
            }
        }

        System.out.println("High score: " + highScore);
        System.out.println("Done!");
    }
}
