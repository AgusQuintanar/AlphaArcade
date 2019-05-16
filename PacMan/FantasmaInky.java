// Agustin Quintanar y Julio Arath Rosales
// A01636142 y A01630738

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Color;
public class FantasmaInky extends Fantasma {
    private int coorXFBlinky,
                coorYFBlinky;

    public FantasmaInky(int xF, int yF, double anchoPista, double altoPista, int[][] matrizPista, String direccionPacMan, double velocidad) {
        super(xF, yF, anchoPista, altoPista, matrizPista, "fantasmaAzul", direccionPacMan, velocidad);
        this.direccionFantasma = "der";
        this.esquinaXDispersion = 51;
        this.esquinaYDispersion = 30;
        this.tiempoInicialSalidaCasa = 6;
        this.coorXFBlinky = 0;
        this.coorYFBlinky = 0;
    }

    public void generarRuta(int PacManXCoor, int PacManYCoor, String direccionPacMan, int coorXFBlinky, int coorYFBlinky) {
        if (this.direccionPacMan == "der"){
            this.agregarACaminoX = PacManXCoor + 4 - this.coorXFBlinky;
            this.agregarACaminoY = PacManYCoor - this.coorYFBlinky;
        }
        else if (this.direccionPacMan == "izq"){
            this.agregarACaminoX = PacManXCoor - 4 - this.coorXFBlinky;
            this.agregarACaminoY = PacManYCoor - this.coorYFBlinky;
        }  
        else if (this.direccionPacMan == "aba"){
            this.agregarACaminoX = PacManXCoor - this.coorXFBlinky;
            this.agregarACaminoY = PacManYCoor + 4 - this.coorYFBlinky;
        }
        else if (this.direccionPacMan == "arr"){
            this.agregarACaminoX = PacManXCoor - 4 - this.coorXFBlinky;
            this.agregarACaminoY = PacManYCoor - 4 - this.coorYFBlinky;
        }

        super.generarRuta(PacManXCoor, PacManYCoor, direccionPacMan);
       
    }

    public String comportamientoFantasma(long cronometro, int PacManXCoor, int PacManYCoor, String direccionPacMan, boolean pellet,double PacManXCoorTemp, double PacManYCoorTemp, double velocidadGlobal, int coorXFBlinky, int coorYFBlinky) {
        this.coorXFBlinky = coorXFBlinky;
        this.coorYFBlinky = coorYFBlinky;
        return super.comportamientoFantasma(cronometro, PacManXCoor, PacManYCoor, direccionPacMan, pellet, PacManXCoorTemp, PacManYCoorTemp, velocidadGlobal);
    }
}