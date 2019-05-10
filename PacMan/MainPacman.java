// Agustin Quintanar y Julio Arath Rosales
// A01636142 y A01630738

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
		System.out.println(this.getToolkit().getScreenSize().getWidth() / this.getToolkit().getScreenSize().getHeight());
		System.out.println("Pantalla: "+this.getToolkit().getScreenSize().getWidth() + ", " + this.getToolkit().getScreenSize().getHeight());
		JuegoPacMan juegoPacMan;
		if (this.getToolkit().getScreenSize().getWidth() / this.getToolkit().getScreenSize().getHeight() == 1.6){ //Aspecto 16:10
			juegoPacMan = new JuegoPacMan(.95*this.getToolkit().getScreenSize().getWidth());
			System.out.println("Aspecto 16:10");
			JPanel lateral = new JPanel();
			lateral.setPreferredSize(new Dimension((int)(.025*this.getToolkit().getScreenSize().getWidth()),(int)(this.getToolkit().getScreenSize().getHeight())));
			lateral.setBackground(Color.BLACK);
			this.add(lateral, BorderLayout.WEST);
			
		}
		else {
			System.out.println("Aspecto 16:9");
			juegoPacMan = new JuegoPacMan(.85*this.getToolkit().getScreenSize().getWidth());
			JPanel lateral = new JPanel();

			lateral.setPreferredSize(new Dimension((int)(.075*this.getToolkit().getScreenSize().getWidth()),(int)(this.getToolkit().getScreenSize().getHeight())));
			lateral.setBackground(Color.BLACK);
			this.add(lateral, BorderLayout.WEST);

		}
		
		this.add(juegoPacMan, BorderLayout.CENTER);
		this.setVisible(true);	
	}
	
	public static void main(String[] args) {
		
		MainPacMan game =new MainPacMan();
	}

}
