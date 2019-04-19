import java.awt.BorderLayout;
import javax.swing.JFrame;

public class MyVentana extends JFrame{

    public MyVentana() {
        super("Space Invaders_0.2v");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        PanelDibujoSpaceInvaders pd= new PanelDibujoSpaceInvaders();
        this.add(pd);
        //this.add(new MyPanelControles(pd),BorderLayout.WEST);
        // this.setSize(500, 500);
        this.setVisible(true);
        this.pack();
    }

        public static void main(String[] args) {
            MyVentana Ventana =new MyVentana();


        }
    }
