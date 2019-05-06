// Agustin Quintanar y Julio Arath Rosales
// A01636142 y A01630738

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Color;
public class FantasmaBlinky extends Fantasma {

    public FantasmaBlinky(int xF, int yF, double anchoPista, double altoPista, int[][] matrizPista, String direccionPacMan, double velocidad) {
        super(xF, yF, anchoPista, altoPista, matrizPista, new ImageIcon("fantasmaRojoImg.png").getImage(), direccionPacMan, velocidad);
        this.direccionFantasma = "der";
    }

    public void pintaFantasma(Graphics g){
        g.setColor(Color.RED);
        super.pintaFantasma(g);
    }

    public boolean modoDispersion() {
        return super.modoDispersion(51,0);
    }

    public void movimientoXY (){
        super.movimientoXY();
    }
}