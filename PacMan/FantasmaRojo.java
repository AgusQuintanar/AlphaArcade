import java.awt.Color;
import java.awt.Graphics;

public class FantasmaRojo {

    private int xFRojo,
            yFrojo,
            coorXFRojo,
            coorYFRojo;
    private double anchoPista,
                   altoPista;
    private int[][] matrizPista;

    public FantasmaRojo (int xFRojo, int yFRojo, double anchoPista, double altoPista, int[][] matrizPista){
        this.xFRojo = xFRojo;
        this.yFrojo = yFRojo;
        this.anchoPista = anchoPista;
        this.altoPista = altoPista;
        this.coorXFRojo = 0;
        this.coorYFRojo = 0;
        this.matrizPista = matrizPista;
    }

    public void setXFRojo (int xFRojo){
        this.xFRojo = xFRojo;
    }

    public void setYFRojo (int yFRojo){
        this.yFrojo = yFRojo;
    }

    public void pintaFantasmaRojo(Graphics g){
        g.setColor(Color.RED);
        g.drawRect(this.xFRojo, this.yFrojo,(int)(this.anchoPista/52), (int)(this.anchoPista/52));
    }

    public void modoPersecusion(int PacManXCoor, int PacManYCoor) {
        this.coorXFRojo = (int) ((this.xFRojo + .3*.9928*(this.ancho/52) -.9928*this.ancho/104) / (.9928*(this.ancho/52)));
        this.coorXFRojo = (int) ((this.yFrojo + .3*.9928*(this.alto/31) -.9928*this.alto/62) / (.9928*(this.alto/31)));
        if (this.coorXFRojo != PacManXCoor && this.coorYFRojo != PacManYCoor){
            if (PacManXCoor > this.coorXFRojo) {

            }
        }
        else{
            System.out.println("Game Over");
        }
    }
}