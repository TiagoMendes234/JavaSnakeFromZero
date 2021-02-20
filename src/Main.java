import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private Snake snake = new Snake();


    public Main(){
        setTitle("mNdz Snake");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(snake);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }


    public static void main(String[] args){
        new Main();
    }
}
