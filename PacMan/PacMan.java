import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PacMan extends JPanel implements ImageObserver, Runnable {
	protected int xPac,
				  yPac,
				  ancho;
	private Image pacManAbierto,
				  pacManCerrado;
	private Thread hilo;
	private boolean abiertoCerrado;
	
	public PacMan(int xPac, int yPac) {
		super();
		this.xPac = xPac;
		this.yPac = yPac;
		this.ancho = 40;
		this.setPreferredSize(new Dimension(this.ancho,this.ancho));
		this.setBackground(Color.BLACK);
		this.hilo = new Thread(this);
		this.hilo.start();
	}

	public void paintComponent(Graphics g, String direccionPacman) {
		super.paintComponent(g);
		this.pintaPacman(g,this.abiertoCerrado, direccionPacman);
    }

	public void pintaPacman(Graphics g, boolean abiertoCerrado, String direccionPacman) {
		this.pacManAbierto = new ImageIcon("Imagenes/pac-man-3d-"+direccionPacman+".png").getImage();
		this.pacManCerrado = new ImageIcon("Imagenes/PacManIconCerrado-3d-"+direccionPacman+".png").getImage();

		if(abiertoCerrado){
			g.drawImage(this.pacManAbierto, xPac, yPac, this.ancho, this.ancho, this);
		}
		else{
			g.drawImage(this.pacManCerrado, xPac, yPac, this.ancho, this.ancho, this);
		}
	}

	public void run(){
		while(true){
			this.repaint();
			this.abiertoCerrado = true;
			try {
				Thread.sleep(150);
				this.abiertoCerrado = false;
				this.repaint();
				Thread.sleep(150);
		}catch(InterruptedException ex) {
			System.out.println("No pude despertar!");
			}
		}
	}
	

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}

}
