import java.awt.Color;
import java.awt.Graphics;

public class FantasmaRojo {

    private int xFRojo,
            yFRojo,
            coorXFRojo,
            coorYFRojo;
            
    private double anchoPista,
                   altoPista,
                   coorXFRojoTemp,
                   coorYFRojoTemp;
    private int[][] matrizPista;

    public FantasmaRojo (int xFRojo, int yFRojo, double anchoPista, double altoPista, int[][] matrizPista){
        System.out.println("xFROJO Y YFROJO: " + xFRojo +", " +yFRojo);
        this.xFRojo = xFRojo;
        this.yFRojo = yFRojo;
        this.anchoPista = anchoPista;
        this.altoPista = altoPista;
        this.coorXFRojo = 25;
        this.coorYFRojo = 15;
        this.coorXFRojoTemp = 25;
        this.coorYFRojoTemp = 15;
        this.matrizPista = matrizPista;
    }

    public void setXFRojo (int xFRojo){
        this.xFRojo = xFRojo;
    }

    public void setYFRojo (int yFRojo){
        this.yFRojo = yFRojo;
    }

    public void pintaFantasmaRojo(Graphics g){
        g.setColor(Color.RED);
        g.drawRect(this.xFRojo, this.yFRojo,(int)(this.anchoPista/52), (int)(this.anchoPista/52));
    }

    public void modoPersecusion(int PacManXCoor, int PacManYCoor) {
        System.out.println("double: " + this.coorXFRojoTemp +", " +this.coorYFRojoTemp);
        System.out.println("int: " + this.coorXFRojo +", " +this.coorYFRojo);
        int velocidad = 2;
        this.coorXFRojoTemp = (this.xFRojo + .33*.9928*(this.anchoPista/52) -.9928*this.anchoPista/104) / (.9928*(this.anchoPista/52)) + .02;
        this.coorYFRojoTemp = (this.yFRojo + .39*.9928*(this.altoPista/31) -.985*this.altoPista/62) / (.985*(this.altoPista/31));

        this.coorXFRojo = (int) this.coorXFRojoTemp;
        this.coorYFRojo = (int) this.coorYFRojoTemp;

        if (PacManXCoor < this.coorXFRojo) this.coorXFRojo += 1;
        if (PacManYCoor < this.coorYFRojo) this.coorYFRojo += 1;
      
        // System.out.println("Coor X: "+ this.coorXFRojo + ", Y: " + this.coorYFRojo);
        // System.out.println("Int X: "+ this.xFRojo + ", Y: " + this.yFRojo);
        //Si PacMan esta arriba a la derecha
        
        //Arriba derehca
        //Arriba Izquierda
        //Arriba
        //Derecha
        //Izquierda
        //Abajo derecha
        // Abajo izquierda
        //Abajo

        if (!(this.coorXFRojo == PacManXCoor && this.coorYFRojo == PacManYCoor)){ //Mientras no sean iguales
           
            if(this.matrizPista[this.coorYFRojo][this.coorXFRojo+1] != 1 && PacManXCoor > this.coorXFRojo){
                this.xFRojo += velocidad;
            }
            else if(this.matrizPista[this.coorYFRojo-1][this.coorXFRojo] != 1 && PacManYCoor < this.coorYFRojo){
                this.yFRojo -= velocidad;
            }
            else if(this.matrizPista[this.coorYFRojo][this.coorXFRojo-1] != 1 && PacManXCoor < this.coorXFRojo){
                this.xFRojo -= velocidad;
            }
            else if(this.matrizPista[this.coorYFRojo+1][this.coorXFRojo] != 1 && PacManYCoor > this.coorYFRojo){
                this.yFRojo += velocidad;
            }
            
            }
        else{
            System.out.println("Game Over");
        }
     }
}