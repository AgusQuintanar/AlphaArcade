import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Pista extends JPanel implements Runnable, KeyListener {
	private PacMan pacman;
  private Thread hilo;
	private String direccionPacman,
									 direccionTmp = "arr";
	private boolean abiertoCerrado;
	private Image pista;
	private double ancho,
					alto;

	public Pista(int ancho, int alto) {
		super();
		this.ancho = ancho;
		this.alto = alto*.95;
		this.setPreferredSize(new Dimension((int)this.ancho,(int)this.alto));
		this.setBackground(Color.BLUE);
		this.pacman = new PacMan((int)(this.ancho/2-this.ancho/34), (int)(this.ancho/2-this.ancho/34));
		this.direccionPacman = "";
		this.abiertoCerrado = true;
		this.direccionTmp = "arr";
		this.pista = new ImageIcon("Imagenes/PistaConPuntitos.png").getImage();
		this.setFocusable(true);
		this.addKeyListener(this);
		this.hilo = new Thread(this);
		this.hilo.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.pista, (int)(this.ancho*.025/2), 0, (int)(this.ancho*.975), (int)(this.alto*.975), this);
		this.pacman.pintaPacman(g,this.abiertoCerrado, this.direccionTmp);
    }
    
	public void run(){
		int velocidad = 4;
		int contMiliSeg = 0;
		while(true){
			if(direccionPacman != "") //Si esta parado se guarda su posicion y direccion
				direccionTmp = direccionPacman;
			this.repaint();
			try {
				Thread.sleep(3);
				contMiliSeg += 3;
				if(contMiliSeg % 147 == 0) this.abiertoCerrado = false;
				if(this.direccionPacman == "der") this.pacman.xPac += velocidad;
				else if(this.direccionPacman == "izq") this.pacman.xPac -= velocidad;
				else if(this.direccionPacman == "arr") this.pacman.yPac -= velocidad;
				else if(this.direccionPacman == "aba") this.pacman.yPac += velocidad;
				Thread.sleep(3);
				contMiliSeg += 3;
				if(contMiliSeg % 300 == 0){
					this.abiertoCerrado = true;
					contMiliSeg = 0; //Reinicia el contador
				}
				
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

}