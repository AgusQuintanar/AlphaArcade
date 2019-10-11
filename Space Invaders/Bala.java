import java.awt.*;

public class Bala extends Personaje {
    public  Bala(){
        super();
    }
    public Bala(int posX, int posY, int velocidad, int tamaño){
        this.posX=posX+8;
        this.posY=posY-10;
        this.velocidad=velocidad;
        this.tamaño=tamaño;
        moverDerecha=false;
        moverIzquierda=false;
        visible=true;
    }
    public void pinta(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(this.posX,this.posY,this.getTamaño()/2,this.getTamaño()*2);

    }
}
