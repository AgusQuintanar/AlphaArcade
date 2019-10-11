import java.awt.*;

public class Cañon extends Personaje {

    public Cañon (){
        super(0,0,0,0);

    }
    public Cañon(int posX,int posY, int velocidad, int tamaño){
        super(posX,posY, velocidad, tamaño);
    }

    public void pinta(Graphics g) {
        if(this.visible==true){
            g.setColor(Color.white);
            g.fillRect(this.posX,this.posY,this.getTamaño(),this.getTamaño());
        }

    }
}
