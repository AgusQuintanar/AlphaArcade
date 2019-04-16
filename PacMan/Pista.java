import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class Pista extends JPanel implements Runnable, KeyListener {
	private PacMan pacman;
  private Thread hilo;
	private String direccionPacman,
									 direccionTmp = "arr";
	private boolean abiertoCerrado;

	public Pista() {
		super();
		int anchoPista = 680;
		this.setPreferredSize(new Dimension(anchoPista,anchoPista));
		this.setBackground(Color.BLACK);
		this.pacman = new PacMan(anchoPista/2-anchoPista/34, anchoPista/2-anchoPista/34);
		this.direccionPacman = "";
		this.abiertoCerrado = true;
		this.direccionTmp = "arr";
		this.setFocusable(true);
		this.addKeyListener(this);
		this.hilo = new Thread(this);
		this.hilo.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
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
				System.out.println("Cont 1: "+contMiliSeg);
				if(contMiliSeg % 147 == 0) this.abiertoCerrado = false;
				if(this.direccionPacman == "der") this.pacman.xPac += velocidad;
				else if(this.direccionPacman == "izq") this.pacman.xPac -= velocidad;
				else if(this.direccionPacman == "arr") this.pacman.yPac -= velocidad;
				else if(this.direccionPacman == "aba") this.pacman.yPac += velocidad;
				Thread.sleep(3);
				contMiliSeg += 3;
				System.out.println("Cont 2: "+contMiliSeg);
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