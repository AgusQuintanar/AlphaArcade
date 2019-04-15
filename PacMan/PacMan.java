import java.awt.Color;
import java.awt.Graphics;

public class PacMan {
	protected int xPac,
				  yPac,
				  ancho;
	protected Color colorPacMan;
	
	public PacMan(int xPac, int yPac) {
		this.xPac = xPac;
		this.yPac = yPac;
		this.ancho = 40;
	}

	public void setPosicionInicial(int xPac, int yPac) { //Cuando se pierde una vida
		this.xPac = xPac;
		this.yPac = yPac;
	}
	public void pintaPacman(Graphics g) {
        g.setColor(Color.YELLOW);
		g.fillOval(xPac, yPac, this.ancho, this.ancho);
	}

}
