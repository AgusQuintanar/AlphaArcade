import java.awt.Image;

import javax.swing.ImageIcon;

public class Frutas {

    private int[] arrayNivelesFruta,
                  arrayNivelesPuntuacionFruta;

    private Image imagenFruta;

    private int x,
            y,
            anchoPista,
            altoPista;
    
    public Frutas() {
        this.imagenFruta = new ImageIcon("fruta1.png").getImage();
        this.arrayNivelesFruta = new int[]{1,2,3,3,4,4,5,5,6,6,7,7,8};
        this.arrayNivelesPuntuacionFruta = new int[]{100,300,500,500,700,700,1000,1000,2000,2000,3000,3000,5000};
        this.x = (this.anchoPista / 2 - this.anchoPista / 104);
    }


}