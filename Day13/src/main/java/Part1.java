import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part1 {

    public static void main(String[] args) throws IOException{
        List<String> lines = Files.readAllLines(Paths.get(new File("").getAbsolutePath() + "\\src\\input.txt"));
        HashMap<String, String> grid = new HashMap<>();

        int y = 0;
        for (String line :lines) {
            for (int x = 0; x < line.length(); x++) {
                char character = line.charAt(x);
                grid.put(x + "," + y, String.valueOf(character));
            }
            y++;
        }

        ArrayList<Cart> carts = createCarts(grid);
        while (true) {
            if (carts.size() == 1) {
                System.out.println("One cart remaining at " + carts.get(0).getLocation());
                return;
            }

            Collections.sort(carts);

            for (int i = 0; i < carts.size(); i++) {
                Cart cart = carts.get(i);
                String nextTrackLocation = determineNextTrackLocation(cart);
                String nextTrack = grid.get(nextTrackLocation);
                cart.act(nextTrack);
                int crashIndex = checkForCrashes(cart, carts);
                if (crashIndex != -1) {
                    System.out.println("Crash at " + cart.getLocation() + "!");
                    return;
                }
            }
        }
    }

    private static int checkForCrashes(Cart cartToCheck, ArrayList<Cart> carts) {
        for (Cart cart : carts) {
            if (cart != cartToCheck && cart.getLocation().equals(cartToCheck.getLocation())) {
                return carts.indexOf(cart);
            }
        }
        return -1;
    }

    private static String determineNextTrackLocation(Cart cart) {
        int x = cart.x;
        int y = cart.y;

        switch (cart.facing) {
            case NORTH:
                y -= 1;
                break;
            case SOUTH:
                y += 1;
                break;
            case EAST:
                x += 1;
                break;
            case WEST:
                x -= 1;
                break;
        }

        return x + "," + y;
    }

    private static ArrayList<Cart> createCarts(HashMap<String, String> grid) {
        ArrayList<Cart> carts = new ArrayList<>();

        for  (Map.Entry<String, String> entry : grid.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if ("^v<>".contains(value)) {
                Cart newCart = new Cart();
                newCart.x = Integer.parseInt(key.split(",")[0]);
                newCart.y = Integer.parseInt(key.split(",")[1]);
                String replacementTrack = "|";
                switch (value) {
                    case "^" :
                        newCart.facing = Facing.NORTH;
                        break;
                    case "v" :
                        newCart.facing = Facing.SOUTH;
                        break;
                    case ">" :
                        replacementTrack = "-";
                        newCart.facing = Facing.EAST;
                        break;
                    case "<" :
                        replacementTrack = "-";
                        newCart.facing = Facing.WEST;
                        break;
                }
                carts.add(newCart);
                grid.put(newCart.x + "," + newCart.y, replacementTrack);
            }
        }
        return carts;
    }

}
