// Agustin Quintanar y Julio Arath Rosales
// A01636142 y A01630738

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

public class Fantasma implements ImageObserver {

    protected int xF,
            yF,
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
            contador;
            
    protected double anchoPista,
                   altoPista,
                   coorXFTemp,
                   coorYFTemp,
                   velocidad;

    protected int[][] matrizPista;
    protected String direccionFantasma,
                   direccionPacMan;

    protected boolean peticionAbajo,
                    peticionArriba,
                    peticionDerecha,
                    peticionIzquierda,
                    paredArribaAbajo,
                    modoHuidaActivado;
    protected Image fantasmaImg,
                    fantasmaHuidaAzul,
                    fantasmaHuidaBlanco;
    protected long tiempoHuida;

    public Fantasma (int xF, int yF, double anchoPista, double altoPista, int[][] matrizPista, Image fantasmaImg, String direccionPacMan, double velocidad){
        ////System.out.println("xFROJO Y YFROJO: " + xF +", " +yF);
        this.xF = xF;
        this.yF = yF;
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
        this.direccionFantasma = "izq";
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
        this.contador = 0;
        this.fantasmaHuidaAzul = new ImageIcon("fantasmaHuida1Img.png").getImage();
        this.fantasmaHuidaBlanco = new ImageIcon("fantasmaHuida2Img.png").getImage();
    }

    public void setXF (int xF){
        this.xF = xF;
    }

    public void setYF (int yF){
        this.yF = yF;
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

    public void pintaFantasma(Graphics g){
        g.fillOval((int)(this.PacManXCoor*.9928*(this.anchoPista/52)-.3*.9928*(this.anchoPista/52)+.9928*this.anchoPista/104), (int)((this.PacManYCoor*.9928*(this.altoPista/31)-.3*.9928*(this.altoPista/31)+.9928*this.altoPista/62)), 25, 25);
        g.setColor(Color.BLUE);
        if (!this.modoHuidaActivado) g.drawImage(this.fantasmaImg, this.xF - (int)(.375*this.anchoPista/52), this.yF - (int)(.375*this.anchoPista/52), (int)(1.75*this.anchoPista/52), (int)(1.75*this.anchoPista/52), this);
        else if (this.modoHuidaActivado) g.drawImage(this.fantasmaHuidaAzul, this.xF - (int)(.375*this.anchoPista/52), this.yF - (int)(.375*this.anchoPista/52), (int)(1.75*this.anchoPista/52), (int)(1.75*this.anchoPista/52), this);
        // else {
        //     if(this.contador % 12 == 0) g.drawImage(this.fantasmaHuidaBlanco, this.xF - (int)(.375*this.anchoPista/52), this.yF - (int)(.375*this.anchoPista/52), (int)(1.75*this.anchoPista/52), (int)(1.75*this.anchoPista/52), this);
        //     else if (this.contador % 25 == 0) g.drawImage(this.fantasmaHuidaAzul, this.xF - (int)(.375*this.anchoPista/52), this.yF - (int)(.375*this.anchoPista/52), (int)(1.75*this.anchoPista/52), (int)(1.75*this.anchoPista/52), this);
        // }
        //g.drawRect(this.xF, this.yF,(int)(this.anchoPista/52), (int)(this.anchoPista/52));
        pintarOjos(g);

    }

    public void pintarOjos(Graphics g){

        if (this.direccionFantasma == "izq"){
            g.fillOval(this.xF + (int)(.5*this.anchoPista/156), this.yF + (int)(.7575*this.anchoPista/155.75),(int)(this.anchoPista/200), (int)(this.anchoPista/200));
            g.fillOval(this.xF + (int)(.5*this.anchoPista/156) + (int)(this.anchoPista/84.5), this.yF + (int)(.7575*this.anchoPista/155.75),(int)(this.anchoPista/200), (int)(this.anchoPista/200));
        }
        else if (this.direccionFantasma == "der"){
            g.fillOval(this.xF + (int)(.9*this.anchoPista/156), this.yF + (int)(.7575*this.anchoPista/155.75),(int)(this.anchoPista/200), (int)(this.anchoPista/200));
            g.fillOval(this.xF + (int)(.9*this.anchoPista/156) + (int)(this.anchoPista/84.5), this.yF + (int)(.7575*this.anchoPista/155.75),(int)(this.anchoPista/200), (int)(this.anchoPista/200));
        }
        else if (this.direccionFantasma == "arr"){
            g.fillOval(this.xF + (int)(.7*this.anchoPista/156), this.yF + (int)(.55*this.anchoPista/155.75),(int)(this.anchoPista/200), (int)(this.anchoPista/200));
            g.fillOval(this.xF + (int)(.68*this.anchoPista/156) + (int)(this.anchoPista/84.56), this.yF + (int)(.55*this.anchoPista/155.75),(int)(this.anchoPista/200), (int)(this.anchoPista/200));
    
        }
        else if (this.direccionFantasma == "aba"){
            g.fillOval(this.xF + (int)(.7*this.anchoPista/156), this.yF + (int)(.95*this.anchoPista/155.75),(int)(this.anchoPista/200), (int)(this.anchoPista/200));
            g.fillOval(this.xF + (int)(.68*this.anchoPista/156) + (int)(this.anchoPista/84.56), this.yF + (int)(.95*this.anchoPista/155.75),(int)(this.anchoPista/200), (int)(this.anchoPista/200));
        }

}

    public void generarRuta(int PacManXCoor, int PacManYCoor, String direccionPacMan) {

        this.PacManXCoor = PacManXCoor + this.agregarACaminoX;
        this.PacManYCoor = PacManYCoor + this.agregarACaminoY;
        this.direccionPacMan = direccionPacMan;

        ////System.out.println("Modo Persecucion Activado");
        this.coorXPeticionPasada = this.coorXF;
        this.coorYPeticionPasada = this.coorYF;

        this.coorXFTemp = (this.xF + .33*.9928*(this.anchoPista/52) -.9928*this.anchoPista/104) / (.9928*(this.anchoPista/52)) + .02;
        this.coorYFTemp = (this.yF + .39*.9928*(this.altoPista/31) -.985*this.altoPista/62) / (.985*(this.altoPista/31)) + .02;

        this.coorXF = (int) this.coorXFTemp;
        this.coorYF = (int) this.coorYFTemp;

        this.caminoX = Math.abs(this.coorXF - this.PacManXCoor);
        this.caminoY = Math.abs(this.coorYF - this.PacManYCoor);

        System.out.println("Camino X: " + this.caminoX);
        System.out.println("Camino Y: " + this.caminoY);

        
        if (this.direccionFantasma == "izq" && this.coorXFTemp%1 > .15) this.coorXF += 1;
        if (this.direccionFantasma == "arr" && this.coorYFTemp%1 > .15) this.coorYF += 1;

        if (this.matrizPista[this.coorYF][this.coorXF - 1] != 1) System.out.println("Pared izquierda FALSE");
        else System.out.println("Pantalla izquierda TRUE");

        if (this.matrizPista[this.coorYF][this.coorXF + 1] != 1) System.out.println("Pared derecha FALSE");
        else System.out.println("Pantalla derecha TRUE");

        if (this.matrizPista[this.coorYF - 1][this.coorXF] != 1) System.out.println("Pared arriba FALSE");
        else System.out.println("Pantalla arriba TRUE");

        if (this.matrizPista[this.coorYF + 1][this.coorXF] != 1) System.out.println("Pared abajoFALSE");
        else System.out.println("Pantalla abajo TRUE");



        if (!(this.coorXF == PacManXCoor && this.coorYF == PacManYCoor) && (this.coorXF <= 50 && this.coorXF > 0 && this.coorYFTemp > 1 && this.coorYFTemp < 30)){ //Mientras no sean iguales
           
            if ((this.matrizPista[this.coorYF][this.coorXF - 1] != 1 || this.matrizPista[this.coorYF][this.coorXF + 1] != 1) && (this.peticionAbajo || this.peticionArriba) && (this.coorYF != this.coorYPeticionPasada)){
                this.peticionArriba = false;
                this.peticionAbajo = false;
                System.out.println("Peticion arriba o abajo desactivada");
            }
            else if ((this.matrizPista[this.coorYF + 1][this.coorXF] != 1 || this.matrizPista[this.coorYF - 1][this.coorXF] != 1 ) && (this.peticionIzquierda || this.peticionDerecha) && (this.coorXF != this.coorXPeticionPasada)){
                this.peticionIzquierda = false;
                this.peticionDerecha = false;
                System.out.println("Peticion en izquierda o derecha desactivada");
            }
         
            if (this.coorXF > 45 && this.coorYF == 14) this.peticionDerecha = true;
            else if (this.coorXF < 5 && this.coorYF == 14) this.peticionIzquierda = true;
          

            if (this.coorXF <= 50 && this.coorXF > 0 && this.coorYFTemp >= 1 && this.coorYFTemp < 30 && (!this.peticionAbajo && !this.peticionArriba && !this.peticionDerecha && !this.peticionIzquierda)) {
                

                if (caminoX == caminoY){
                    System.out.println("Camino igual");
                    if (this.matrizPista[this.coorYF-1][this.coorXF] != 1 && this.direccionFantasma != "aba" )  this.peticionArriba = true;
                    else if (this.matrizPista[this.coorYF][this.coorXF-1] != 1 && this.direccionFantasma != "der")  this.peticionIzquierda = true;
                    else if (this.matrizPista[this.coorYF+1][this.coorXF] != 1 && this.direccionFantasma != "arr"  )  this.peticionAbajo = true;
                    else if (this.matrizPista[this.coorYF][this.coorXF+1] != 1 && this.direccionFantasma != "izq")  this.peticionDerecha = true;
                }
                else {  
                    
                    if (PacManYCoor <= this.coorYF && PacManXCoor >= this.coorXF){ //pACmAN ARRIBA A LA DERECHA
                        System.out.println("Pacman arriba a la derecha");
                        if (this.matrizPista[this.coorYF][this.coorXF+1] != 1 && this.direccionFantasma != "izq" && caminoX > caminoY)  this.peticionDerecha = true;
                        else if (this.matrizPista[this.coorYF-1][this.coorXF] != 1 && this.direccionFantasma != "aba" && caminoX > caminoY )  this.peticionArriba = true;
                        else if (this.matrizPista[this.coorYF-1][this.coorXF] != 1 && this.direccionFantasma != "aba" && caminoX < caminoY )  this.peticionArriba = true;
                        else if (this.matrizPista[this.coorYF][this.coorXF+1] != 1 && this.direccionFantasma != "izq" && caminoX < caminoY)  this.peticionDerecha = true;
                        else if (this.direccionFantasma == "der" || this.direccionFantasma == "izq" ) this.peticionAbajo = true;
                        else if (this.direccionFantasma == "aba" || this.direccionFantasma == "arr")  this.peticionIzquierda = true; 
                    }

                    else if (PacManYCoor <= this.coorYF && PacManXCoor <= this.coorXF){ //pACmAN ARRIBA A LA Izquierda
                        System.out.println("Pacman Arriba a la izquierda");
                        if (this.matrizPista[this.coorYF][this.coorXF-1] != 1 && this.direccionFantasma != "der" && caminoX > caminoY)  this.peticionIzquierda = true;
                        else if (this.matrizPista[this.coorYF-1][this.coorXF] != 1 && this.direccionFantasma != "aba" && caminoX > caminoY )  this.peticionArriba = true;
                        else if (this.matrizPista[this.coorYF-1][this.coorXF] != 1 && this.direccionFantasma != "aba" && caminoX < caminoY )  this.peticionArriba = true;
                        else if (this.matrizPista[this.coorYF][this.coorXF-1] != 1 && this.direccionFantasma != "der" && caminoX < caminoY)  this.peticionIzquierda = true;
                        else if (this.direccionFantasma == "der" || this.direccionFantasma == "izq" ) this.peticionAbajo = true;
                        else if (this.direccionFantasma == "aba" || this.direccionFantasma == "arr")this.peticionDerecha = true;
                    }

                    else if (PacManYCoor >= this.coorYF && PacManXCoor >= this.coorXF){ //pACmAN Abajo A LA DERECHA
                        System.out.println("Pacman abajo a la derecha");
                        if (this.matrizPista[this.coorYF][this.coorXF+1] != 1 && this.direccionFantasma != "izq" && caminoX > caminoY)  this.peticionDerecha = true;
                        else if (this.matrizPista[this.coorYF+1][this.coorXF] != 1 && this.direccionFantasma != "arr" && caminoX > caminoY ) this.peticionAbajo = true;
                        else if (this.matrizPista[this.coorYF+1][this.coorXF] != 1 && this.direccionFantasma != "arr" && caminoX < caminoY ) this.peticionAbajo = true;
                        else if (this.matrizPista[this.coorYF][this.coorXF+1] != 1 && this.direccionFantasma != "izq" && caminoX < caminoY) this.peticionDerecha = true;
                        else if (this.direccionFantasma == "der" || this.direccionFantasma == "izq" )  this.peticionArriba = true;
                        else if (this.direccionFantasma == "aba" || this.direccionFantasma == "arr")  this.peticionIzquierda = true;
                    }

                    else if (PacManYCoor >= this.coorYF && PacManXCoor <= this.coorXF){ //pACmAN abajo a la izquierda
                        System.out.println("Pacman abajo a la izquierda");
                        if (this.matrizPista[this.coorYF][this.coorXF-1] != 1 && this.direccionFantasma != "der" && caminoX > caminoY)  this.peticionIzquierda = true;
                        else if (this.matrizPista[this.coorYF+1][this.coorXF] != 1 && this.direccionFantasma != "arr" && caminoX > caminoY ) this.peticionAbajo = true;
                        else if (this.matrizPista[this.coorYF+1][this.coorXF] != 1 && this.direccionFantasma != "arr" && caminoX < caminoY ) this.peticionAbajo = true;
                        else if (this.matrizPista[this.coorYF][this.coorXF-1] != 1 && this.direccionFantasma != "der" && caminoX < caminoY)  this.peticionIzquierda = true;
                        else if (this.direccionFantasma == "der" || this.direccionFantasma == "izq" )  this.peticionArriba = true;
                        else if (this.direccionFantasma == "aba" || this.direccionFantasma == "arr")  this.peticionDerecha = true;
                    }
                }
            }    
        }
        else{
          ////System.out.println("Game Over");
        }

        if (peticionAbajo) System.out.println("Peticion abajo activada");
        else if (peticionArriba) System.out.println("Peticion arriba activada");
        else if (peticionDerecha) System.out.println("Peticion derecha activada");
        else if (peticionIzquierda) System.out.println("Peticion izquierda activada");

       
     }
     public boolean modoPersecusion(int PacManXCoor, int PacManYCoor, String direccionPacMan) {
        this.modoHuidaActivado = false;
        generarRuta(PacManXCoor, PacManYCoor, direccionPacMan);
        movimientoXY();
        if (this.coorXF == PacManXCoor && this.coorYF == PacManYCoor) return true;
        else return false;
     }
     
     public boolean modoHuida(int PacManXCoor, int PacManYCoor, String direccionPacMan, long tiempoHuida, int contador) {
        this.modoHuidaActivado = true;
        this.tiempoHuida = tiempoHuida;
        this.contador = contador; 
        if (this.coorXF <= PacManXCoor) this.puntoXHuida = this.coorXF - this.caminoX;
        else if (this.coorXF > PacManXCoor) this.puntoXHuida = this.coorXF + this.caminoX;
        if (this.coorYF < PacManYCoor) this.puntoYHuida = this.coorYF - this.caminoY;
        else if (this.coorYF >= PacManYCoor) this.puntoYHuida = this.coorYF + this.caminoY;
        generarRuta(this.puntoXHuida, this.puntoYHuida, direccionPacMan);
        movimientoXY();
        if (this.coorXF == PacManXCoor && this.coorYF == PacManYCoor) return true;
        else return false;
     }

     public boolean modoDispersion(int esquinaX, int esquinaY) {
        this.modoHuidaActivado = false;
        generarRuta(esquinaX, esquinaY, this.direccionPacMan);
        movimientoXY();
        if (this.coorXF == PacManXCoor && this.coorYF == PacManYCoor) return true;
        else return false;
    }

    public boolean salirDeLaCasa(boolean salir){
        System.out.println("salir de la cada");
        this.coorXFTemp = (this.xF + .33*.9928*(this.anchoPista/52) -.9928*this.anchoPista/104) / (.9928*(this.anchoPista/52)) + .02;
        this.coorYFTemp = (this.yF + .39*.9928*(this.altoPista/31) -.985*this.altoPista/62) / (.985*(this.altoPista/31)) + .02;
        this.coorXF = (int) this.coorXFTemp;
        this.coorYF = (int) this.coorYFTemp;
        if (this.direccionFantasma == "izq" && this.coorXFTemp%1 > .15) this.coorXF += 1;
        if (this.direccionFantasma == "arr" && this.coorYFTemp%1 > .15) this.coorYF += 1;

        if (!salir){
            if (this.matrizPista[this.coorYF - 1][this.coorXF] == 1) this.paredArribaAbajo = true;
            else if (this.matrizPista[this.coorYF + 1][this.coorXF] == 1) this.paredArribaAbajo = false;

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

     public void movimientoXY(){



        if (peticionAbajo) this.direccionFantasma = "aba";
        else if (peticionArriba) this.direccionFantasma = "arr";
        else if (peticionDerecha) this.direccionFantasma = "der";
        else if (peticionIzquierda) this.direccionFantasma = "izq";

        
        
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

			else if ( this.direccionFantasma == "izq" && this.matrizPista[this.coorYF][this.coorXF - 1] != 1) this.xF -= this.velocidad;
               
			else if (this.direccionFantasma == "arr" && this.matrizPista[this.coorYF - 1][this.coorXF] != 1 ) this.yF -= this.velocidad;
                
			else if (this.direccionFantasma == "aba" && this.matrizPista[this.coorYF + 1][this.coorXF] != 1 ) this.yF += this.velocidad;
		}
			
    }
    @Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}

}