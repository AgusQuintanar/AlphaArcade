// Agustin Quintanar y Julio Arath Rosales
// A01636142 y A01630738

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Color;
public class FantasmaPinky extends Fantasma {

    public FantasmaPinky(int xF, int yF, double anchoPista, double altoPista, int[][] matrizPista, String direccionPacMan, double velocidad) {
        super(xF, yF, anchoPista, altoPista, matrizPista, new ImageIcon("fantasmaRosaImg.png").getImage(), direccionPacMan, velocidad);
    }

    public void pintaFantasma(Graphics g){
        g.setColor(Color.PINK);
        super.pintaFantasma(g);
    }

    public void generarRuta(int PacManXCoor, int PacManYCoor, String direccionPacMan) {
        if (this.direccionPacMan == "der"){
            this.agregarACaminoX = 4;
            this.agregarACaminoY = 0;
        }
        else if (this.direccionPacMan == "izq"){
            this.agregarACaminoX = -4;
            this.agregarACaminoY = 0;
        }  
        else if (this.direccionPacMan == "aba"){
            this.agregarACaminoX = 0;
            this.agregarACaminoY = 4;
        }
        else if (this.direccionPacMan == "arr"){
            this.agregarACaminoX = -4;
            this.agregarACaminoY = -4;
        }
        super.generarRuta(PacManXCoor, PacManYCoor, direccionPacMan);  
    }

    public boolean modoDispersion() {
        return super.modoDispersion(1,1);
     }

     public void movimientoXY (){
        super.movimientoXY();
    }
}