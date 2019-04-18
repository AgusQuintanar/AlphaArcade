import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainPacMan extends JFrame{
	public MainPacMan() {
		super("PacMan v1.0 by Agus Quintanar");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setBackground(Color.BLACK);
		Pista pista = new Pista(this.getToolkit().getScreenSize().getWidth());
		this.add(pista);
		this.setVisible(true);	
	}
	public static void main(String[] args) {
		
		MainPacMan game =new MainPacMan();
	}

}
