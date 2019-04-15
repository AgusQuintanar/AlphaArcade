import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Pista extends JPanel {
	private PacMan pacman;

	public Pista() {
        super();
        int anchoPista = 680;
		this.setPreferredSize(new Dimension(anchoPista,anchoPista));
        this.setBackground(Color.BLACK);
        this.pacman = new PacMan(anchoPista/2-anchoPista/34, anchoPista/2-anchoPista/34);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.pacman.pintaPacman(g);
	}
}
