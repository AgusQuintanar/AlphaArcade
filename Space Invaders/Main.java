import javax.swing.*;
import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Main extends JFrame{
    public Main() {
        super("Space Invaders_0.2v");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //MyPanelDibujo pd= new MyPanelDibujo();
        //this.add(pd);
        //this.add(new MyPanelControles(pd),BorderLayout.WEST);
        this.setSize(800, 600);
        this.setVisible(true);
        this.pack();
    }


        public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
