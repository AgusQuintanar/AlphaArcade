import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

public class PacMan implements ImageObserver {
	protected int xPac,
				  yPac,
				  ancho;
	private Image pacManAbierto,
				  pacManCerrado;
				  
	public PacMan(int xPac, int yPac, int ancho) {
		this.xPac = xPac;
		this.yPac = yPac;
		this.ancho = ancho;
		this.pacManAbierto = new ImageIcon("Imagenes/pac-man-3d-arr.png").getImage();
		this.pacManCerrado = new ImageIcon("Imagenes/PacManIconCerrado-3d-arr.png").getImage();
	}

	public void setXPac(int xPac){
		this.xPac = xPac;
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

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}

}











