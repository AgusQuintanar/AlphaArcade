import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Color;
public class FantasmaClyde extends Fantasma {

    public FantasmaClyde(int xF, int yF, double anchoPista, double altoPista, int[][] matrizPista, String direccionPacMan, double velocidad) {
        super(xF, yF, anchoPista, altoPista, matrizPista, new ImageIcon("Imagenes/fantasmaNaranjaImg.png").getImage(), direccionPacMan, velocidad);
    }

    public void pintaFantasma(Graphics g){
        g.setColor(Color.ORANGE);
        super.pintaFantasma(g);
    }

    public void modoPersecusion(int PacManXCoor, int PacManYCoor, String direccionPacMan) {
        if (Math.sqrt(Math.pow(this.coorXF - PacManXCoor,2) + Math.pow(this.coorYF - PacManYCoor,2)) > 8){ //Si esta fuera de un radio de 8 casillas
            generarRuta(PacManXCoor, PacManYCoor, direccionPacMan);
            movimientoXY();
        }
        else {
            modoHuida(PacManXCoor, PacManYCoor, direccionPacMan);
        }
     
     }

    public void modoDispersion() {
        super.modoDispersion(0,30);
    }
}