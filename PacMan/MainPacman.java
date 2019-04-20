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
		this.setBounds(0, 0, (int)this.getToolkit().getScreenSize().getWidth(), (int)this.getToolkit().getScreenSize().getHeight());
		this.setResizable(false);
		this.setBackground(Color.BLACK);
		Pista pista;
		if (this.getToolkit().getScreenSize().getWidth() / this.getToolkit().getScreenSize().getHeight() == 16/10){ //Aspecto 16:10
			System.out.println("Aspecto 16:10");
			pista = new Pista(this.getToolkit().getScreenSize().getWidth());
		}
		else {
			System.out.println("Aspecto 16:9");
			JPanel lateral = new JPanel();
			lateral.setPreferredSize(new Dimension((int)(.05*this.getToolkit().getScreenSize().getWidth()),(int)(this.getToolkit().getScreenSize().getHeight())));
			lateral.setBackground(Color.BLACK);
			this.add(lateral, BorderLayout.WEST);
			pista = new Pista(.9*this.getToolkit().getScreenSize().getWidth()); //Aspecto 16:9
		}
		
		this.add(pista, BorderLayout.CENTER);
		this.setVisible(true);	
	}
	public static void main(String[] args) {
		
		MainPacMan game =new MainPacMan();
	}

}
