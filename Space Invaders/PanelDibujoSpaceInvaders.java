import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
/*
import java.awt.Toolkit;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.FontMetrics;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
*/

public class PanelDibujoSpaceInvaders  extends JPanel implements Runnable, MouseListener
{
    private boolean ingame;

    private int ancho,
            alto,
            velocidadBala,
            velocidadAlien,
            velocidadCañon,
            cantidadAliens,
            cantidadBalas,
            tamañoAlien,
            tamañoCañon,
            tamañoBala;

    private Thread hilo;

    Cañon p;
    Alienigena[] a;
    Bala[] b;

    public PanelDibujoSpaceInvaders() {
        ingame = true;
        alto=ancho=500;

        velocidadBala=1;
        velocidadAlien=1;
        velocidadCañon=5;
        cantidadAliens=28;
        cantidadBalas=20;
        tamañoAlien=25;
        tamañoCañon=20;
        tamañoBala=4;

        a= new Alienigena[cantidadAliens];
        b=new Bala[cantidadBalas];

        setPreferredSize(new Dimension(500, 500));
        addKeyListener(new TAdapter());
        addMouseListener(this);
        setFocusable(true);//enfocarme en eso


        p = new Cañon(ancho / 2, alto - 60, velocidadCañon,tamañoCañon);
        for(int i=0;i<b.length;i++) {
            b[i]=new Bala();
        }
        this.setBackground(Color.black);

        int posX = 10;
        int posY = 10;

        for (int i = 0; i < a.length; i++) {
            a[i] = new Alienigena(posX, posY, velocidadAlien,tamañoAlien);
            posX += 40;
            if ((i + 1) % 7 == 0 && i != 0) {
                posX = 10;
                posY += 40;
            }
        }

        //arrancar hilo

        if (hilo == null || !ingame) {
            hilo = new Thread(this);
            hilo.start();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //cañon
        this.pintaCañon(g);
        //aliens
        this.pintaAliens(g);
        this.pintaBala(g);
        moverAliens();
        moverBala();

    }

    public void pintaCañon(Graphics g){

        p.pinta(g);

        if(p.getMoverDerecha()==true){
            p.setPosX(p.getVelocidad());

        }else if(p.getMoverIzquierda()==true){
            p.setPosX(-p.getVelocidad());

        }
    }

    public void pintaBala(Graphics g){
        for(int i=0;i<b.length;i++) {
            b[i].pinta(g);
        }
    }

    public void pintaAliens(Graphics g){
        for(int i=0;i<a.length;i++){
            this.a[i].pinta(g);
        }
    }

    public void moverBala(){
        for(int i=0;i<b.length;i++) {
            if(b[i].getVisible()==true && b[i].posY>0){
                b[i].setPosY(-b[i].getVelocidad());
            }else{
                b[i]=new Bala();
            }
            for(int k=0;k<a.length;k++){
                if (a[k].getPosX()<b[i].getPosX() && b[i].getPosX()<(a[k].getPosX()+a[k].getTamaño()) && a[k].getPosY()<b[i].getPosY() && b[i].getPosY()<(a[k].getPosY()+a[k].getTamaño())){            {
                    a[k]=new Alienigena();
                    b[i]=new Bala();
                }
                }
            }
        }
    }

    public void moverAliens(){
        for(int i=0; i<a.length;i++){
           if(a[i].getMoverIzquierda()==true){
               a[i].setPosX(-a[i].getVelocidad());
           }else if(a[i].getMoverDerecha()==true){
               a[i].setPosX(a[i].getVelocidad());
           }

        }
        for(int i=0; i<a.length;i++) {
            if (a[i].getPosX() > (ancho-25)) {
                for (int j = 0; j < a.length; j++) {
                    if(a[j].getPosY()>alto-92){
                        ingame=false;
                        a[j].setMoverIzquierda(false);
                        a[j].setMoverDerecha(false);
                    }else{
                        p.setVisible(true);
                        a[j].setPosY(a[j].getVelocidad()*2);
                        a[j].setMoverDerecha(false);
                        a[j].setMoverIzquierda(true);
                    }
                }
            }
            if (a[i].getPosX() < 0) {
                for (int j = 0; j < a.length; j++) {
                    if(a[j].getPosY() >ancho-92 && a[j].getVisible()==true){
                        a[j].setMoverDerecha(false);
                        a[j].setMoverIzquierda(false);
                        //hacerlos inivisbles
                    }else{
                        a[j].setMoverDerecha(true);
                        a[j].setMoverIzquierda(false);
                        a[j].setPosY(a[j].getVelocidad()*2);
                    }

                }
            }
        }

    }

    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent evento) {
            p.moverIzquierda=false;
            p.moverDerecha=false;
        }

        public void keyPressed(KeyEvent evento) {
            //  System.out.println(evento.getKeyCode());
            if ((p.getPosX() >= 15) && (p.getPosX() <= (ancho - 50))) {
                if (evento.getKeyCode() == 39 || evento.getKeyCode() == 68) {
                    p.setMoverDerecha(true);
                } else if (evento.getKeyCode() == 37 || evento.getKeyCode() == 65) {
                    p.setMoverIzquierda(true);
                }
            } else if (15 > p.getPosX()) {
                if (evento.getKeyCode() == 39 || evento.getKeyCode() == 68) {
                    p.setMoverDerecha(true);
                }
            } else if ((ancho - 50) < p.getPosX()) {
                if (evento.getKeyCode() == 37 || evento.getKeyCode() == 65) {
                    p.setMoverIzquierda(true);
                }
            }
            if (evento.getKeyCode() == 38 || evento.getKeyCode() == 32 || evento.getKeyCode() == 87) {
                for(int i=0;i<b.length;i++) {
                    if (b[i].getVisible() == false) {
                        b[i] = new Bala(p.getPosX(), p.getPosY(), velocidadBala,tamañoBala);
                        b[i].setVisible(true);
                        break;
                    }
                }
            }
        }
    }




    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void run() {

        long beforeTime, timeDiff, sleep;

        //beforeTime = System.currentTimeMillis();
        int animationDelay = 7;
        long time = System.currentTimeMillis();
        while (true) {
            this.repaint();
            try {
                time += animationDelay;
                Thread.sleep(Math.max(0,time -
                        System.currentTimeMillis()));
            }catch (InterruptedException e) {
                System.out.println(e);
            }//end catch
        }//end while loop
    }

}

