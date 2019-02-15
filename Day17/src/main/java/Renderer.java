import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class Renderer {


    private static Renderer single_instance = null;
    private JTextPane textPane = new JTextPane();
    private JFrame frame = new JFrame("ScrollPane to TextArea");
    private JScrollPane scroll;


    static Renderer getInstance() {
        if (single_instance == null) {
            single_instance = new Renderer();
        }

        return single_instance;
    }

    private Renderer() {
        textPane.setEditable(false);
        textPane.setOpaque(true);
        textPane.setBackground(Color.BLACK);
        textPane.setForeground(Color.LIGHT_GRAY);
        textPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JPanel panel = new JPanel();
        panel.add(textPane);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);

    }

    void drawMap(HashMap<Point, Occupant> worldMap, Point upperLeft, Point lowerRight){

        textPane.setEditable(false);
        textPane.setOpaque(true);
        textPane.setBackground(Color.BLACK);
        textPane.setForeground(Color.LIGHT_GRAY);
        textPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        scroll = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        frame.add(scroll);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ArrayList<Point> points = new ArrayList<>(worldMap.keySet());
        Collections.sort(points);


        StringBuilder sb = new StringBuilder();
        for (int y = 0; y <= lowerRight.y; y++) {
            for (int x = upperLeft.x; x <= lowerRight.x; x++) {
                Point point = new Point(x, y);
                Occupant occupant = worldMap.get(point);
                char character;
                if (occupant == null) {
                    character = ' ';
                } else if (occupant instanceof Spring) {
                    character = '+';
                } else if (occupant instanceof Clay) {
                    character = '#';
                } else if (occupant instanceof UnsettledWater) {
                    character = '|';
                } else {
                    character = '~';
                }
                sb.append(character);
            }
            sb.append("\r\n");
        }
        textPane.setText(sb.toString());
        frame.pack();
    }
}