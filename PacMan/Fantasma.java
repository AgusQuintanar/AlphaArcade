
// Agustin Quintanar y Julio Arath Rosales
// A01636142 y A01660738

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

public class Fantasma implements ImageObserver {

    protected int   xF,
                    yF,
                    xFInicial,
                    yFInicial,
                    coorXF,
                    coorYF,
                    coorXPeticionPasada,
                    coorYPeticionPasada,
                    caminoX,
                    caminoY,
                    puntoXHuida,
                    puntoYHuida,
                    agregarACaminoX,
                    agregarACaminoY,
                    PacManXCoor,
                    PacManYCoor,
                    contador,
                    esquinaXDispersion,
                    esquinaYDispersion,
                    tiempoInicialSalidaCasa,
                    puntajePorFantasmaComido,
                    xFComido,
                    yFComido;
            
    protected double anchoPista,
                   altoPista,
                   coorXFTemp,
                   coorYFTemp,
                   velocidad;

    protected int[][] matrizPista;
    protected String direccionFantasma,
                   direccionPacMan,
                   fantasmaImg;

    protected boolean peticionAbajo,
                    peticionArriba,
                    peticionDerecha,
                    peticionIzquierda,
                    paredArribaAbajo,
                    modoHuidaActivado,
                    volverALaCasa,
                    salioFantasma,
                    fantasmaComido,
                    mostrarPuntaje;
 
    protected long tiempoFantasma,
                   tiempoInicialFantasma,
                   tiempoHuida,
                   tiempoInicialHuida,
                   tiempoComidoInicial;

    private Image ojo;
    private Font fuenteFantasma;

    public Fantasma (int xFInicial, int yFInicial, double anchoPista, double altoPista, int[][] matrizPista, String fantasmaImg, String direccionPacMan, double velocidad){
        ////System.out.println("xFROJO Y YFROJO: " + xF +", " +yF);
        this.xFInicial = xFInicial;
        this.yFInicial = yFInicial;
        this.xF = xFInicial;
        this.yF = yFInicial;
        this.anchoPista = anchoPista;
        this.altoPista = altoPista;
        this.coorXF = 1;
        this.coorYF = 1;
        this.coorXFTemp = 0;
        this.coorYFTemp = 0;
        this.caminoX = 5;
        this.caminoY = 4;
        this.PacManXCoor = 0;
        this.PacManYCoor = 0;
        this.matrizPista = matrizPista;
        this.direccionFantasma = "arr";
        this.peticionDerecha = false;
        this.peticionIzquierda = false;
        this.peticionArriba = false;
        this.peticionAbajo = false;
        this.coorXPeticionPasada = 0;
        this.coorYPeticionPasada = 0;
        this.fantasmaImg = fantasmaImg;
        this.direccionPacMan = direccionPacMan;
        this.velocidad = velocidad;
        this.puntoXHuida = 0;
        this.puntoYHuida = 0;
        this.modoHuidaActivado = false;
        this.agregarACaminoX = 0;
        this.agregarACaminoY = 0;
        this.paredArribaAbajo = true;
        this.tiempoHuida = 0;
        this.tiempoInicialHuida = 0;
        this.contador = 0;
        this.volverALaCasa = true;
        this.ojo = new ImageIcon("ojoFantasma.png").getImage();
        this.salioFantasma = false;
        this.esquinaXDispersion = 0;
        this.esquinaYDispersion = 0;
        this.tiempoInicialSalidaCasa = 6;
        this.fantasmaComido = false;
        this.mostrarPuntaje = false;
        this.puntajePorFantasmaComido = 0;
        this.tiempoComidoInicial = 0;
        this.xFComido = 0;
        this.yFComido = 0;
        try {
            this.fuenteFantasma = Font.createFont(Font.TRUETYPE_FONT, new File("LuckiestGuy-Regular.ttf")).deriveFont((float)(altoPista/40)*1f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(this.fuenteFantasma);
       } catch (IOException|FontFormatException e) {
            System.out.println("Fuente no encontrada");
       }
    }

    public void setXF(int xF) {
        this.xF = xF;
    }

    public void setYF(int yF) {
        this.yF = yF;
    }

    public int getXF() {
        return this.xF;
    }

    public int getYF() {
        return this.yF;
    }

    public void setXFComido(int xFComido) {
        this.xFComido = xFComido;
    }

    public void setYFComido(int yFComido) {
        this.yFComido = yFComido;
    }

    public void setPuntajePorFantasmaComido(int puntaje) {
        this.puntajePorFantasmaComido = puntaje;
    }

    public void setMostrarPuntaje(boolean mostrar) {
        this.mostrarPuntaje = mostrar;
    } 

    public int getCoorXF() {
        return this.coorXF;
    }

    public int getCoorYF() {
        return this.coorYF;
    }

    public void setVelocidad (double velocidad){
        this.velocidad = velocidad;
    }

    public void setModoHuidaActivado(boolean activadoDesactivado) {
        this.modoHuidaActivado = activadoDesactivado;
    }

    public boolean getModoHuidaActivado() {
        return this.modoHuidaActivado;
    }

    public void setTiempoComidoInicial(long cronometro) {
        this.tiempoComidoInicial = cronometro;
    }

    public void pintaFantasma(Graphics g, int contador){
        g.setFont(this.fuenteFantasma);
        //g.fillOval((int)(this.PacManXCoor*.9928*(this.anchoPista/52)-.3*.9928*(this.anchoPista/52)+.9928*this.anchoPista/104), (int)((this.PacManYCoor*.9928*(this.altoPista/31)-.3*.9928*(this.altoPista/31)+.9928*this.altoPista/62)), 25, 25);
        g.setColor(Color.WHITE);
        Image fantasmaImgTemp = new ImageIcon(this.fantasmaImg+"2.png").getImage();;

        if (this.mostrarPuntaje){
            g.drawString(Integer.toString(this.puntajePorFantasmaComido), this.xFComido, this.yFComido + (int)(this.anchoPista/52/3));
        }

        if (!this.volverALaCasa){
            fantasmaImgTemp = new ImageIcon("ojosFantasma.png").getImage();
        }

        else if (!this.modoHuidaActivado){
            if(contador/60.0%.3 < .15) fantasmaImgTemp = new ImageIcon(this.fantasmaImg+"1.png").getImage();
		    else fantasmaImgTemp = new ImageIcon(this.fantasmaImg+"2.png").getImage();
        } 
        else if (this.modoHuidaActivado){
            if (tiempoHuida < 10){
                if(contador/60.0%.3 < .15) fantasmaImgTemp = new ImageIcon("fantasmaAzulAsustado1.png").getImage();
		        else fantasmaImgTemp = new ImageIcon("fantasmaAzulAsustado2.png").getImage();
            }
            else {
                if(contador/60.0%.6 < .3) {
                    if(contador/60.0%.3 < .15) fantasmaImgTemp = new ImageIcon("fantasmaAzulAsustado1.png").getImage();
                    else fantasmaImgTemp = new ImageIcon("fantasmaAzulAsustado2.png").getImage(); 
                }
                else {
                    if(contador/60.0%.3 < .45) fantasmaImgTemp = new ImageIcon("fantasmaBlanco1.png").getImage();
                    else fantasmaImgTemp = new ImageIcon("fantasmaBlanco2.png").getImage();
                }
            }
        }

        double ajusteX = 0.0;
        if (this.coorXFTemp < 26) ajusteX = 3*(this.anchoPista/52)/(26-(this.coorXFTemp/3));
        
        g.drawImage(fantasmaImgTemp, this.xF - (int)(-ajusteX + .485*this.anchoPista/52), this.yF - (int)(.375*this.anchoPista/52), (int)(1.95*this.anchoPista/52), (int)(1.9*this.anchoPista/52), this);


        if (!modoHuidaActivado || !this.volverALaCasa) pintarOjos(g);
        //g.fillRect(this.xF, this.yF, (int)(this.anchoPista/52), (int)(this.anchoPista/52));

    }

    public void pintarOjos(Graphics g){

        int x1 = 0,
            y1 = 0,
            x2 = 0,
            y2 = 0;

        double ajusteX = 0.0;
        if (this.coorXFTemp < 26) ajusteX = 2.5*(this.anchoPista/52)/(26-(this.coorXFTemp/3));

        if (this.direccionFantasma == "izq"){
            x1 = this.xF + (int)(.9*ajusteX+.0655*1.15*this.anchoPista/52) - (int)(.195*1.15*this.anchoPista/52);
            x2 = x1 + (int)(this.anchoPista/63.25);
            y1 = y2 = this.yF + (int)(1.15*this.anchoPista/165);
        }
        else if (this.direccionFantasma == "der"){
            x1 = this.xF + (int)(ajusteX+.0655*1.15*this.anchoPista/52);
            x2 = x1 + (int)(this.anchoPista/63.25);
            y1 = y2 = this.yF + (int)(1.15*this.anchoPista/165);
        }
        else if (this.direccionFantasma == "arr"){
            x1 = this.xF + (int)(ajusteX+.05*this.anchoPista/52) - (int)(.085*1.15*this.anchoPista/52);
            x2 = x1 + (int)(1.15*this.anchoPista/72.175);
            y1 = y2 = this.yF + (int)(1.15*this.anchoPista/240);
    
        }
        else if (this.direccionFantasma == "aba"){
            x1 = this.xF + (int)(ajusteX+.05*this.anchoPista/52) - (int)(.085*1.15*this.anchoPista/52);
            x2 = x1 + (int)(1.15*this.anchoPista/72.175);
            y1 = y2 = this.yF + (int)(1.15*this.anchoPista/120);
        }


        g.drawImage(this.ojo, x1, y1, (int)(.275*1.25*this.anchoPista/52), (int)(.32*1.25*this.anchoPista/52), this);
        g.drawImage(this.ojo, x2, y2, (int)(.275*1.25*this.anchoPista/52), (int)(.32*1.25*this.anchoPista/52), this);

}

    public void generarRuta(int PacManXCoor, int PacManYCoor, String direccionPacMan) {

        this.PacManXCoor = PacManXCoor + this.agregarACaminoX;
        this.PacManYCoor = PacManYCoor + this.agregarACaminoY;
        this.direccionPacMan = direccionPacMan;

        ////System.out.println("Modo Persecucion Activado");
        this.coorXPeticionPasada = this.coorXF;
        this.coorYPeticionPasada = this.coorYF;

        this.coorXFTemp = (this.xF + .33*.9928*(this.anchoPista/52) -.9928*this.anchoPista/104) / (.9928*(this.anchoPista/52)) +  (.02*this.velocidad/3);
        this.coorYFTemp = (this.yF + .39*.9928*(this.altoPista/31) -.985*this.altoPista/62) / (.985*(this.altoPista/31)) +  (.02*this.velocidad/3);

        this.coorXF = (int) this.coorXFTemp;
        this.coorYF = (int) this.coorYFTemp;

        this.caminoX = Math.abs(this.coorXF - this.PacManXCoor);
        this.caminoY = Math.abs(this.coorYF - this.PacManYCoor);


        if (this.direccionFantasma == "izq" && this.coorXFTemp%1 > .15) this.coorXF += 1;
        if (this.direccionFantasma == "arr" && this.coorYFTemp%1 > .15) this.coorYF += 1;

        if (this.coorXF <= 50 && this.coorXF > 0 && this.coorYFTemp > 1 && this.coorYFTemp < 60){ //Mientras no sean iguales

            // if (this.matrizPista[this.coorYF][this.coorXF + 1] == 1) System.out.println("Pared derecha");
            // if (this.matrizPista[this.coorYF][this.coorXF - 1] == 1) System.out.println("Pared izquierda");
            // if (this.matrizPista[this.coorYF + 1][this.coorXF] == 1 || this.matrizPista[this.coorYF][this.coorXF + 1] == 5) System.out.println("Pared abajo");
            // if (this.matrizPista[this.coorYF - 1][this.coorXF] == 1 || this.matrizPista[this.coorYF][this.coorXF - 1] == 4) System.out.println("Pared arriba");
  
            if ((this.matrizPista[this.coorYF][this.coorXF - 1] != 1 || this.matrizPista[this.coorYF][this.coorXF + 1] != 1) && (this.peticionAbajo || this.peticionArriba) && (this.coorYF != this.coorYPeticionPasada)){
                this.peticionArriba = false;
                this.peticionAbajo = false;
            }
            else if ((this.matrizPista[this.coorYF + 1][this.coorXF] != 1 || this.matrizPista[this.coorYF - 1][this.coorXF] != 1) && (this.peticionIzquierda || this.peticionDerecha) && (this.coorXF != this.coorXPeticionPasada)){
                this.peticionIzquierda = false;
                this.peticionDerecha = false;
            }
            if (this.matrizPista[this.coorYF][this.coorXF + 1] == 1) this.peticionDerecha = false;
            if (this.matrizPista[this.coorYF][this.coorXF - 1] == 1) this.peticionIzquierda = false;
            if (this.matrizPista[this.coorYF + 1][this.coorXF] == 1 || this.matrizPista[this.coorYF][this.coorXF + 1] == 5) this.peticionAbajo = false;
            if (this.matrizPista[this.coorYF - 1][this.coorXF] == 1 || this.matrizPista[this.coorYF][this.coorXF - 1] == 4) this.peticionArriba = false;
         
            if (this.coorXF > 45 && this.coorYF == 14) this.peticionDerecha = true;
            else if (this.coorXF < 5 && this.coorYF == 14) this.peticionIzquierda = true;
          

            if (this.coorXF <= 50 && this.coorXF > 0 && this.coorYFTemp >= 1 && this.coorYFTemp < 60 && (!this.peticionAbajo && !this.peticionArriba && !this.peticionDerecha && !this.peticionIzquierda)) {
                

                if (caminoX == caminoY){
                    //System.out.println("Camino igual");
                    if (this.matrizPista[this.coorYF-1][this.coorXF] != 1 && this.direccionFantasma != "aba" && this.matrizPista[this.coorYF-1][this.coorXF] != 4)  this.peticionArriba = true;
                    else if (this.matrizPista[this.coorYF][this.coorXF-1] != 1 && this.direccionFantasma != "der")  this.peticionIzquierda = true;
                    else if (this.matrizPista[this.coorYF+1][this.coorXF] != 1 && this.direccionFantasma != "arr"  && this.matrizPista[this.coorYF+1][this.coorXF] != 5)  this.peticionAbajo = true;
                    else if (this.matrizPista[this.coorYF][this.coorXF+1] != 1 && this.direccionFantasma != "izq")  this.peticionDerecha = true;
                }
                else {  
                    
                    if (PacManYCoor < this.coorYF && PacManXCoor > this.coorXF){ //pACmAN ARRIBA A LA DERECHA
                        //System.out.println("Pacman arriba a la derecha");
                        if (this.matrizPista[this.coorYF][this.coorXF+1] != 1 && this.direccionFantasma != "izq" && caminoX > caminoY)  this.peticionDerecha = true;
                        else if (this.matrizPista[this.coorYF-1][this.coorXF] != 1 && this.direccionFantasma != "aba" && caminoX > caminoY && this.matrizPista[this.coorYF-1][this.coorXF] != 4)  this.peticionArriba = true;
                        else if (this.matrizPista[this.coorYF-1][this.coorXF] != 1 && this.direccionFantasma != "aba" && caminoX < caminoY && this.matrizPista[this.coorYF-1][this.coorXF] != 4)  this.peticionArriba = true;
                        else if (this.matrizPista[this.coorYF][this.coorXF+1] != 1 && this.direccionFantasma != "izq" && caminoX < caminoY)  this.peticionDerecha = true;
                        else if (this.direccionFantasma == "der" || this.direccionFantasma == "izq" && this.matrizPista[this.coorYF+1][this.coorXF] != 5) this.peticionAbajo = true;
                        else if (this.direccionFantasma == "aba" || this.direccionFantasma == "arr")  this.peticionIzquierda = true; 
                    }

                    else if (PacManYCoor < this.coorYF && PacManXCoor <= this.coorXF){ //pACmAN ARRIBA A LA Izquierda
                        //System.out.println("Pacman Arriba a la izquierda");
                        if (this.matrizPista[this.coorYF][this.coorXF-1] != 1 && this.direccionFantasma != "der" && caminoX > caminoY)  this.peticionIzquierda = true;
                        else if (this.matrizPista[this.coorYF-1][this.coorXF] != 1 && this.direccionFantasma != "aba" && caminoX > caminoY && this.matrizPista[this.coorYF-1][this.coorXF] != 4)  this.peticionArriba = true;
                        else if (this.matrizPista[this.coorYF-1][this.coorXF] != 1 && this.direccionFantasma != "aba" && caminoX < caminoY && this.matrizPista[this.coorYF-1][this.coorXF] != 4)  this.peticionArriba = true;
                        else if (this.matrizPista[this.coorYF][this.coorXF-1] != 1 && this.direccionFantasma != "der" && caminoX < caminoY)  this.peticionIzquierda = true;
                        else if (this.direccionFantasma == "der" || this.direccionFantasma == "izq" && this.matrizPista[this.coorYF+1][this.coorXF] != 5) this.peticionAbajo = true;
                        else if (this.direccionFantasma == "aba" || this.direccionFantasma == "arr")this.peticionDerecha = true;
                    }

                    else if (PacManYCoor >= this.coorYF && PacManXCoor > this.coorXF){ //pACmAN Abajo A LA DERECHA
                        //System.out.println("Pacman abajo a la derecha");
                        if (this.matrizPista[this.coorYF][this.coorXF+1] != 1 && this.direccionFantasma != "izq" && caminoX > caminoY)  this.peticionDerecha = true;
                        else if (this.matrizPista[this.coorYF+1][this.coorXF] != 1 && this.direccionFantasma != "arr" && caminoX > caminoY && this.matrizPista[this.coorYF+1][this.coorXF] != 5) this.peticionAbajo = true;
                        else if (this.matrizPista[this.coorYF+1][this.coorXF] != 1 && this.direccionFantasma != "arr" && caminoX < caminoY && this.matrizPista[this.coorYF+1][this.coorXF] != 5) this.peticionAbajo = true;
                        else if (this.matrizPista[this.coorYF][this.coorXF+1] != 1 && this.direccionFantasma != "izq" && caminoX < caminoY) this.peticionDerecha = true;
                        else if (this.direccionFantasma == "der" || this.direccionFantasma == "izq" && this.matrizPista[this.coorYF-1][this.coorXF] != 4)  this.peticionArriba = true;
                        else if (this.direccionFantasma == "aba" || this.direccionFantasma == "arr")  this.peticionIzquierda = true;
                    }

                    else if (PacManYCoor >= this.coorYF && PacManXCoor <= this.coorXF){ //pACmAN abajo a la izquierda
                        //System.out.println("Pacman abajo a la izquierda");
                        if (this.matrizPista[this.coorYF][this.coorXF-1] != 1 && this.direccionFantasma != "der" && caminoX > caminoY)  this.peticionIzquierda = true;
                        else if (this.matrizPista[this.coorYF+1][this.coorXF] != 1 && this.direccionFantasma != "arr" && caminoX > caminoY && this.matrizPista[this.coorYF+1][this.coorXF] != 5) this.peticionAbajo = true;
                        else if (this.matrizPista[this.coorYF+1][this.coorXF] != 1 && this.direccionFantasma != "arr" && caminoX < caminoY && this.matrizPista[this.coorYF+1][this.coorXF] != 5) this.peticionAbajo = true;
                        else if (this.matrizPista[this.coorYF][this.coorXF-1] != 1 && this.direccionFantasma != "der" && caminoX < caminoY)  this.peticionIzquierda = true;
                        else if (this.direccionFantasma == "der" || this.direccionFantasma == "izq" && this.matrizPista[this.coorYF-1][this.coorXF] != 4)  this.peticionArriba = true;
                        else if (this.direccionFantasma == "aba" || this.direccionFantasma == "arr")  this.peticionDerecha = true;
                    }
                }
            }    
        }
        else{
          ////System.out.println("Game Over");
        }
        if (peticionAbajo) this.direccionFantasma = "aba";
        else if (peticionArriba) this.direccionFantasma = "arr";
        else if (peticionDerecha) this.direccionFantasma = "der";
        else if (peticionIzquierda) this.direccionFantasma = "izq";

     }
     public void modoPersecusion(int PacManXCoor, int PacManYCoor, String direccionPacMan) {
        this.modoHuidaActivado = false;
        generarRuta(PacManXCoor, PacManYCoor, direccionPacMan);
        movimientoXY();
        
     }
     
     public void modoHuida(int PacManXCoor, int PacManYCoor, String direccionPacMan, int contador) {
        this.modoHuidaActivado = true;
        this.contador = contador; 
        if (this.coorXF <= PacManXCoor) this.puntoXHuida = this.coorXF - this.caminoX;
        else if (this.coorXF > PacManXCoor) this.puntoXHuida = this.coorXF + this.caminoX;
        if (this.coorYF < PacManYCoor) this.puntoYHuida = this.coorYF - this.caminoY;
        else if (this.coorYF >= PacManYCoor) this.puntoYHuida = this.coorYF + this.caminoY;
        generarRuta(this.puntoXHuida, this.puntoYHuida, direccionPacMan);
        movimientoXY();
     }

     public void modoDispersion() {
        this.modoHuidaActivado = false;
        generarRuta(this.esquinaXDispersion, this.esquinaYDispersion, this.direccionPacMan);
        movimientoXY();
    }

    public boolean salirDeLaCasa(boolean salir){
        this.coorXFTemp = (this.xF + .33*.9928*(this.anchoPista/52) -.9928*this.anchoPista/104) / (.9928*(this.anchoPista/52)) +  (.02*this.velocidad/3);
        this.coorYFTemp = (this.yF + .39*.9928*(this.altoPista/31) -.985*this.altoPista/62) / (.985*(this.altoPista/31)) +  (.02*this.velocidad/3);
        this.coorXF = (int) this.coorXFTemp;
        this.coorYF = (int) this.coorYFTemp;
        if (this.direccionFantasma == "izq" && this.coorXFTemp%1 > .15) this.coorXF += 1;
        if (this.direccionFantasma == "arr" && this.coorYFTemp%1 > .15) this.coorYF += 1;

        if (!salir){
            if (this.matrizPista[this.coorYF - 1][this.coorXF] == 1) this.paredArribaAbajo = true;
            else if (this.matrizPista[this.coorYF+2][this.coorXF] == 1 && this.coorYFTemp%1  > .75) this.paredArribaAbajo = false;

            if (this.paredArribaAbajo) this.direccionFantasma = "aba";
            else this.direccionFantasma = "arr";
          
        }
        else {
            if (this.coorYF == 11) return true;  // Sale el fantasma
            if (this.coorXFTemp > 25.3 && this.coorXFTemp < 25.75){
                if (this.coorYF != 11) this.direccionFantasma = "arr";
                else  {
                    this.setYF((int)((11)*.9928*(this.altoPista/31)-.3*.985*(this.altoPista/31)+.985*this.altoPista/62));
                }
            }
            else if (this.coorXFTemp <= 25.3 && this.matrizPista[this.coorYF ][this.coorXF] == 6) this.direccionFantasma = "der";
            else if (this.coorXFTemp >= 25.75 && this.matrizPista[this.coorYF ][this.coorXF] == 6)this.direccionFantasma = "izq";
        }

        if (this.direccionFantasma == "der") this.xF += this.velocidad;
		else if ( this.direccionFantasma == "izq") this.xF -= this.velocidad;
        else if (this.direccionFantasma == "arr") this.yF -= this.velocidad;
        else if (this.direccionFantasma == "aba") this.yF += this.velocidad;
        
        return false;
    }

    public boolean volverALaCasa() {
        this.modoHuidaActivado = false;
        this.volverALaCasa = false;

        if (this.coorXF == 25 && this.coorYF == 11){
            this.volverALaCasa = true;
            return true;
        }
        else {
            this.agregarACaminoX = 0;
            this.agregarACaminoY = 0;
            generarRuta(25, 13, "arr");
            movimientoXY();
            generarRuta(25, 13, "arr");
            movimientoXY();
        }
        return false;
    }

     public void movimientoXY(){
		if (this.coorXF > 45 && this.coorYF == 14){
			if (this.direccionFantasma == "der"  && this.coorXF < 52){
				xF += this.velocidad;
			}
			else if(this.direccionFantasma == "der"){
				this.setXF(0);
			}
			else if (this.direccionFantasma == "izq"){
				xF -= this.velocidad;
			}	
		}
		else if (this.coorXF < 5 && this.coorYF == 14) {
			if (this.direccionFantasma == "izq" && this.coorXF >= -1){
				xF -= this.velocidad;
			}
			else if(this.direccionFantasma == "izq"){
				this.setXF((int)(this.anchoPista-this.anchoPista/52));
			}
			else if (this.direccionFantasma == "der"){
				xF += this.velocidad;
			}
		}			
		else {
			if (this.direccionFantasma == "der" && this.matrizPista[this.coorYF][this.coorXF + 1] != 1) this.xF += this.velocidad;

			else if ( this.direccionFantasma == "izq" && this.matrizPista[this.coorYF][this.coorXF - 1] != 1) this.xF -= this.velocidad*.9;
               
			else if (this.direccionFantasma == "arr" && this.matrizPista[this.coorYF - 1][this.coorXF] != 1 ) this.yF -= this.velocidad*.9;
                
			else if (this.direccionFantasma == "aba" && this.matrizPista[this.coorYF + 1][this.coorXF] != 1 ) this.yF += this.velocidad;
		}
			
    }

    public boolean getVolverALaCasa (){
        return this.volverALaCasa;
    }


    public void manejoVelocidades(double velocidadGlobal) {
        if (this.modoHuidaActivado){
            this.velocidad = velocidadGlobal * .5;
        }
        else {
            this.velocidad = velocidadGlobal * .75;
        }
    }


    public String comportamientoFantasma(long cronometro, int PacManXCoor, int PacManYCoor, String direccionPacMan, boolean pellet, double PacManXCoorTemp, double PacManYCoorTemp, double velocidadGlobal) {
        this.PacManXCoor = PacManXCoor;
        this.PacManYCoor = PacManYCoor;
        this.direccionPacMan = direccionPacMan;
        this.tiempoFantasma = cronometro - this.tiempoInicialFantasma;

        manejoVelocidades(velocidadGlobal);

        if (cronometro - this.tiempoComidoInicial == 2){
            this.mostrarPuntaje = false;
        }

        if (pellet){
            this.tiempoInicialHuida = cronometro;	
            this.modoHuidaActivado = true;
          
        } 

        if (cronometro < 13){
			if(this.tiempoFantasma < this.tiempoInicialSalidaCasa) this.salioFantasma = this.salirDeLaCasa(false);
			if(!this.salioFantasma && this.tiempoFantasma >= this.tiempoInicialSalidaCasa) salioFantasma = this.salirDeLaCasa(true);
		}
		else {
			if(tiempoFantasma < 2 && this.volverALaCasa) this.salioFantasma = this.salirDeLaCasa(false);
            if(!this.salioFantasma && tiempoFantasma >= 2) this.salioFantasma = this.salirDeLaCasa(true);
		}

		boolean fantasma = false;
		if (!this.modoHuidaActivado && this.volverALaCasa){
			if (cronometro < 7){
				if (this.salioFantasma && this.volverALaCasa) this.modoDispersion();
			}
			else if (cronometro < 27){
				if (this.salioFantasma && this.volverALaCasa) this.modoPersecusion(this.PacManXCoor, this.PacManYCoor, this.direccionPacMan);
			}
			else if (cronometro < 34){
				if (this.salioFantasma && this.volverALaCasa) this.modoDispersion();
			}
			else if (cronometro < 54){
				if (this.salioFantasma && this.volverALaCasa) this.modoPersecusion(this.PacManXCoor, this.PacManYCoor, this.direccionPacMan);
			}
			else if (cronometro < 61){
				if (this.salioFantasma && this.volverALaCasa) this.modoDispersion();
			}
			else {
				if (this.salioFantasma && this.volverALaCasa) this.modoPersecusion(this.PacManXCoor, this.PacManYCoor, this.direccionPacMan);
            }
            if (Math.abs(this.coorXFTemp - PacManXCoorTemp) < 1 && Math.abs(this.coorYFTemp - PacManYCoorTemp) < 1 ) fantasma = true;
            if(fantasma) return "tocado";

		} else {
            
            if (this.modoHuidaActivado) this.tiempoHuida = cronometro - this.tiempoInicialHuida;

			if (this.salioFantasma && this.volverALaCasa) this.modoHuida(this.PacManXCoor, this.PacManXCoor, this.direccionPacMan, this.contador);

            if (!this.fantasmaComido && Math.abs(this.coorXFTemp - PacManXCoorTemp) < 1 && Math.abs(this.coorYFTemp - PacManYCoorTemp) < 1 ){
                fantasma = true;
                this.fantasmaComido = true;
            } 

			if (fantasma || !this.volverALaCasa) {
                boolean volvioACasa = this.volverALaCasa();
               
				if (volvioACasa){
                    this.xF = xFInicial;
                    this.yF = yFInicial;
					this.tiempoInicialFantasma = cronometro;
					this.tiempoFantasma = 0;
                    this.salioFantasma = false;
                    this.tiempoHuida = 0;
                    this.fantasmaComido = false;
				}	
            }

            if (fantasma) return "comido";

            if (this.tiempoHuida == 15 && this.volverALaCasa){
                this.modoHuidaActivado = false;
            }
        }
        return "";
    }



    @Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}

}