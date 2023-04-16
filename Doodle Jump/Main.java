import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main{
    public static void main(String[] args){
        JFrame window = new JFrame();
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Java Game");

        GamePanel gamePanel = new GamePanel();
        Color backgroundColor = Color.white;
        gamePanel.setBackground(backgroundColor);

        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        JLabel label = new JLabel("Sample text");
        label.setLocation(200, 500);

        gamePanel.startGameThread();
    }
}