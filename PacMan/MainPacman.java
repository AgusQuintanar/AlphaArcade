import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

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
		JPanel puntuacion = new JPanel();
		puntuacion.setPreferredSize(new Dimension((int)this.getToolkit().getScreenSize().width,(int)(this.getToolkit().getScreenSize().height*.05)));
		puntuacion.setBackground(Color.BLACK);
		this.add(puntuacion, BorderLayout.SOUTH);
		this.add(new Pista(this.getToolkit().getScreenSize().width,this.getToolkit().getScreenSize().height));
		this.setVisible(true);	
	}
	public static void main(String[] args) {
		
		MainPacMan game =new MainPacMan();
	}

}
