import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

public class Frutas implements ImageObserver {

    private int[] arrayNivelesFruta,
                  arrayNivelesPuntuacionFruta;

    private Image imagenFruta;

    private int x,
            y,
            contadorInicial;

    private double anchoPista,
            altoPista;

    private boolean mostrarFruta,
                    mostrarPuntaje;
    
    public Frutas(double anchoPista, double altoPista) {
        System.out.println(anchoPista);
        System.out.println(altoPista);
        this.anchoPista = anchoPista;
        this.altoPista = altoPista;
        this.imagenFruta = new ImageIcon("../Imagenes/Frutas/fruta1.png").getImage();
        this.arrayNivelesFruta = new int[]{1,2,3,3,4,4,5,5,6,6,7,7,8};
        this.arrayNivelesPuntuacionFruta = new int[]{100,300,500,500,700,700,1000,1000,2000,2000,3000,3000,5000};
        this.x = (int)(this.anchoPista / 2 - this.anchoPista / 104);
        this.y = (int) ((17) * .9928 * (this.altoPista / 31) - .3 * .985 * (this.altoPista / 31) + .985 * this.altoPista / 62);
        this.mostrarFruta = false;
        System.out.println(this.x);
    }

    public int pintaFruta(Graphics g, int puntosComidos, int contador, int xPacMan, int yPacMan, int nivel) {
        int nivelTemp = 0;
        if (nivel < 13) nivelTemp = nivel - 1;
        else nivelTemp = 12;
        this.imagenFruta = new ImageIcon("../Imagenes/Frutas/fruta"+this.arrayNivelesFruta[nivelTemp]+".png").getImage();

        generarFruta(puntosComidos, contador);
        int puntosAgregados = checarColisionConPacMan(xPacMan, yPacMan, contador, nivelTemp);
        if(this.mostrarFruta) g.drawImage(this.imagenFruta, x, y-(int)(this.altoPista/62/2), (int) (1.5*this.anchoPista/52), (int)(1.5*this.altoPista/31), this);
        else if (this.mostrarPuntaje)  g.drawString(Integer.toString(this.arrayNivelesPuntuacionFruta[nivelTemp]), this.x, this.y + (int)(this.anchoPista/52/3) + (int)(this.altoPista/62));

        return puntosAgregados;
    }

    public void generarFruta(int puntosComidos, int contador) {
        
        if ((puntosComidos == 70 || puntosComidos == 190) && !this.mostrarFruta){
            System.out.println("prueba 2");
            this.contadorInicial = contador;
            this.mostrarFruta = true;
            System.out.println("contador: " + contador);
            System.out.println("tiempo restante: " + (contador - contadorInicial));
        }

        if ((contador - this.contadorInicial == 750) && this.mostrarFruta) this.mostrarFruta = false;
    }

    public int checarColisionConPacMan(int xPacMan, int yPacMan, int contador, int nivel) {
        if (Math.abs(this.x-xPacMan) < 5 && Math.abs(this.y-yPacMan) < .3 && this.mostrarFruta){
            System.out.println("prueba 3");
            this.mostrarFruta = false;
            this.mostrarPuntaje = true;
            this.contadorInicial = contador;
        }

        if (contador - this.contadorInicial == 100){
            this.mostrarPuntaje = false;
            return this.arrayNivelesPuntuacionFruta[nivel];
        } 
        return 0;
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return false;
    }

}