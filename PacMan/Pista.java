import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Pista extends JPanel implements Runnable, KeyListener, MouseListener {
	private PacMan pacman;
    private Thread hilo;
		private boolean moverPacMan;
		private String direccionPacman;

	public Pista() {
		super();
		int anchoPista = 680;
		this.setPreferredSize(new Dimension(anchoPista,anchoPista));
		this.setBackground(Color.BLACK);
		this.pacman = new PacMan(anchoPista/2-anchoPista/34, anchoPista/2-anchoPista/34);
		this.moverPacMan = false;
		this.direccionPacman = "";
		this.setFocusable(true);
		this.addMouseListener(this);
		this.addKeyListener(this);
		this.hilo = new Thread(this);
		this.hilo.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.pacman.pintaPacman(g);
    }
    
	public void run(){
		int velocidad = 3;
	while(true){
		try {
			Thread.sleep(5);
			if(this.direccionPacman == "der") this.pacman.xPac += velocidad;
			else if(this.direccionPacman == "izq") this.pacman.xPac -= velocidad;
			else if(this.direccionPacman == "arr") this.pacman.yPac -= velocidad;
			else if(this.direccionPacman == "aba") this.pacman.yPac += velocidad;
			this.repaint();
			
		}catch(InterruptedException ex) {
			System.out.println("No pude despertar!");
		}
	}
}



	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) this.direccionPacman = "der";
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) this.direccionPacman = "izq";
		else if(e.getKeyCode() == KeyEvent.VK_UP) this.direccionPacman = "arr";
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) this.direccionPacman = "aba";
		System.out.println(direccionPacman);
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) this.direccionPacman = "";
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) this.direccionPacman = "";
		else if(e.getKeyCode() == KeyEvent.VK_UP) this.direccionPacman = "";
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) this.direccionPacman = "";
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.moverPacMan = true;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

}