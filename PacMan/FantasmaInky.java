// Agustin Quintanar y Julio Arath Rosales
// A01636142 y A01630738

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Color;
public class FantasmaInky extends Fantasma {
    

    public FantasmaInky(int xF, int yF, double anchoPista, double altoPista, int[][] matrizPista, String direccionPacMan, double velocidad) {
        super(xF, yF, anchoPista, altoPista, matrizPista, "fantasmaAzul", direccionPacMan, velocidad);
        this.direccionFantasma = "der";
        this.esquinaXDispersion = 51;
        this.esquinaYDispersion = 30;
    }

    public void generarRuta(int PacManXCoor, int PacManYCoor, String direccionPacMan, int coorXFBlinky, int coorYFBlinky) {
        if (this.direccionPacMan == "der"){
            this.agregarACaminoX = PacManXCoor + 4 - coorXFBlinky;
            this.agregarACaminoY = PacManYCoor - coorYFBlinky;
        }
        else if (this.direccionPacMan == "izq"){
            this.agregarACaminoX = PacManXCoor - 4 - coorXFBlinky;
            this.agregarACaminoY = PacManYCoor - coorYFBlinky;
        }  
        else if (this.direccionPacMan == "aba"){
            this.agregarACaminoX = PacManXCoor - coorXFBlinky;
            this.agregarACaminoY = PacManYCoor + 4 - coorYFBlinky;
        }
        else if (this.direccionPacMan == "arr"){
            this.agregarACaminoX = PacManXCoor - 4 - coorXFBlinky;
            this.agregarACaminoY = PacManYCoor - 4 - coorYFBlinky;
        }

        super.generarRuta(PacManXCoor, PacManYCoor, direccionPacMan);
       
    }

    public boolean modoPersecusion(int PacManXCoor, int PacManYCoor, String direccionPacMan, int coorXFBlinky, int coorYFBlinky) {
        generarRuta(PacManXCoor, PacManYCoor, direccionPacMan, coorXFBlinky, coorYFBlinky);
        return super.modoPersecusion(PacManXCoor, PacManYCoor, direccionPacMan);
     }

     public boolean modoHuida(int PacManXCoor, int PacManYCoor, String direccionPacMan, int contador) {
        super.generarRuta(PacManXCoor, PacManYCoor, direccionPacMan);
        return super.modoHuida(PacManXCoor, PacManYCoor, direccionPacMan, contador);
     }

    public void movimientoXY (){
        super.movimientoXY();
    }
}