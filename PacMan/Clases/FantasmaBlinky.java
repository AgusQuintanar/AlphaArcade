// Agustin Quintanar y Julio Arath Rosales
// A01636142 y A01630738

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Color;
public class FantasmaBlinky extends Fantasma {

    public FantasmaBlinky(int xF, int yF, double anchoPista, double altoPista, int[][] matrizPista, String direccionPacMan, double velocidad) {
        super(xF, yF, anchoPista, altoPista, matrizPista, "fantasmaRojo", direccionPacMan, velocidad);
        this.direccionFantasma = "der";
        this.esquinaXDispersion = 51;
        this.esquinaYDispersion = 0;
        this.salioFantasma = true;
        this.tiempoInicialSalidaCasa = 0;
    }
}