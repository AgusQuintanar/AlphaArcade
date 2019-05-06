import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Color;
public class FantasmaInky extends Fantasma {

    public FantasmaInky(int xF, int yF, double anchoPista, double altoPista, int[][] matrizPista, String direccionPacMan, double velocidad) {
        super(xF, yF, anchoPista, altoPista, matrizPista, new ImageIcon("Imagenes/fantasmaAzulImg.png").getImage(), direccionPacMan, velocidad);
    }

    public void pintaFantasma(Graphics g){
        g.setColor(Color.BLUE);
        super.pintaFantasma(g);
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

    public void modoPersecusion(int PacManXCoor, int PacManYCoor, String direccionPacMan, int coorXFBlinky, int coorYFBlinky) {
        generarRuta(PacManXCoor, PacManYCoor, direccionPacMan, coorXFBlinky, coorYFBlinky);
        super.modoPersecusion(PacManXCoor, PacManYCoor, direccionPacMan);
     }

     public void modoHuida(int PacManXCoor, int PacManYCoor, String direccionPacMan, int coorXFBlinky, int coorYFBlinky) {
        generarRuta(PacManXCoor, PacManYCoor, direccionPacMan, coorXFBlinky, coorYFBlinky);
        super.modoHuida(PacManXCoor, PacManYCoor, direccionPacMan);
     }

    public void modoDispersion() {
        super.modoDispersion(51,0);
     }
}