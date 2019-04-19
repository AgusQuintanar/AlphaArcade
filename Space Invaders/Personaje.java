import java.awt.*;

public class Personaje {
    protected int posX,
        posY,
        velocidad,
        tamaño;
    protected boolean moverDerecha,
            moverIzquierda,
            visible;

    public  Personaje(){
        this(0,0,0,0);
        this.visible=false;
    }

    public Personaje(int posX, int posY, int velocidad, int tamaño){
        this.posX=posX;
        this.posY=posY;
        this.velocidad=velocidad;
        this.tamaño=tamaño;
        moverDerecha=false;
        moverIzquierda=false;
        visible=true;
    }


    public void pinta(Graphics g) {
        g.setColor(Color.red);
    }

    //set

    public void setPosX(int posX){
        this.posX+=posX;
    }

    public void setVelocidad(int velocidad){
        this.velocidad=velocidad;
    }

    public void setPosY(int posY){
        this.posY+=posY;
    }

    public void setTamaño(int tamaño){
        this.tamaño=tamaño;
    }

    public void setMoverDerecha(boolean moverDerecha){
        this.moverDerecha=moverDerecha;
    }

    public void setMoverIzquierda(boolean moverIzquierda){
        this.moverIzquierda=moverIzquierda;
    }

    public void setVisible(boolean visible){
        this.visible=visible;
    }



    //get

    public int getPosX(){
        return this.posX;
    }

    public int getVelocidad(){
        return this.velocidad;
    }

    public int getPosY(){
        return this.posY;
    }

    public int getTamaño(){
        return this.tamaño;
    }

    public boolean getMoverDerecha(){
        return  this.moverDerecha;
    }

    public boolean getMoverIzquierda(){
        return  this.moverIzquierda;
    }

    public boolean getVisible(){
        return  this.visible;
    }

}

