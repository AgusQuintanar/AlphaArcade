// Agustin Quintanar y Julio Arath Rosales
// A01636142 y A01630738

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Color;
public class FantasmaClyde extends Fantasma {

    public FantasmaClyde(int xF, int yF, double anchoPista, double altoPista, int[][] matrizPista, String direccionPacMan, double velocidad) {
        super(xF, yF, anchoPista, altoPista, matrizPista, "fantasmaNaranja", direccionPacMan, velocidad);
        this.direccionFantasma = "aba";
    }

    public boolean modoPersecusion(int PacManXCoor, int PacManYCoor, String direccionPacMan, long tiempoHuida, int contador) {
        if (Math.sqrt(Math.pow(this.coorXF - PacManXCoor,2) + Math.pow(this.coorYF - PacManYCoor,2)) > 8){ //Si esta fuera de un radio de 8 casillas
            generarRuta(PacManXCoor, PacManYCoor, direccionPacMan);
            movimientoXY();
        }
        else {
            modoHuida(PacManXCoor, PacManYCoor, direccionPacMan, tiempoHuida, contador);
        }
        if (this.coorXF == PacManXCoor && this.coorYF == PacManYCoor) return true;
        else return false;
     }

    public boolean modoDispersion() {
        return super.modoDispersion(0,30);
    }

    public void movimientoXY (){
        super.movimientoXY();
    }
}