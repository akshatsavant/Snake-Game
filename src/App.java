import javax.swing.*;


public class App {
    public static void main(String[] args) throws Exception {
        int width = 650;
        int height = width;

        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame sg = new SnakeGame(width, height);
        frame.add(sg);
        frame.pack();
        sg.requestFocus();
    }
}
