import java.awt.*;

public class Alienigena extends Personaje{

    public Alienigena (){
        this(0,0,0,0);
        this.moverDerecha=true;

    }
    public Alienigena(int posX,int posY, int velocidad,int tamaño){
        super(posX,posY, velocidad,tamaño);
        moverDerecha=true;


    }

    public void pinta(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(this.getPosX(),this.getPosY(),this.getTamaño(),this.getTamaño());
    }



}
