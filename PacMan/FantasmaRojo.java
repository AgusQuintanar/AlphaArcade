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

    public void modoPersecusion(double PacManXCoor, double PacManYCoor) {
        int velocidad = 3;
        this.coorXFRojo = (int) ((this.xFRojo + .3*.9928*(this.anchoPista/52) -.9928*this.anchoPista/104) / (.9928*(this.anchoPista/52)));
        this.coorYFRojo = (int) ((this.yFrojo + .3*.9928*(this.altoPista/31) -.9928*this.altoPista/62) / (.9928*(this.altoPista/31)));
        if (this.coorXFRojo != PacManXCoor && this.coorYFRojo != PacManYCoor){
            if (PacManXCoor > this.coorXFRojo && PacManYCoor > this.coorYFRojo) { //Si PacMan esta arriba a la derecha
                if(this.matrizPista[this.yFrojo-1][this.xFRojo] != 1){
                    this.yFrojo -= velocidad;
                }
                if(this.matrizPista[this.yFrojo][this.xFRojo-1] != 1){
                    this.xFRojo -= velocidad;
                }
            }
        }
        else{
            System.out.println("Game Over");
        }
    }
}