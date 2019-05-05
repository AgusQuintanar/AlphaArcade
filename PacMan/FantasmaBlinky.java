import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Color;
public class FantasmaBlinky extends Fantasma {

    public FantasmaBlinky(int xF, int yF, double anchoPista, double altoPista, int[][] matrizPista, String direccionPacMan, double velocidad) {
        super(xF, yF, anchoPista, altoPista, matrizPista, new ImageIcon("Imagenes/fantasmaRojoImg.png").getImage(), direccionPacMan, velocidad);
    }

    public void pintaFantasma(Graphics g){
        g.setColor(Color.RED);
        super.pintaFantasma(g);
    }

    public void modoDispersion() {
        super.modoDispersion(51,0);
     }
}