
// Agustin Quintanar y Julio Arath Rosales
// A01636142 y A01630738

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

public class PacMan implements ImageObserver {
	private int xPac,
				  yPac,
				  coorX,
				coorY,
				contadorMuertePacManInicial;

	private Image pacManAbierto1,
				  pacManAbierto2,
				  pacManCerrado,
				  pacManTemp;
	private String direccionPacman, 
				direccionTmp;
	private boolean peticionSubirBajar,
			peticionIzqDer,
			peticionIzqDerDir,
			peticionSubirBajarDir,
			muerto;
	private double anchoPista, 
					altoPista,
					coorXTemp,
					coorYTemp,
					velocidad;
	private int[][] matrizPista;
	private int[] arrayAnimacionMuerte;
				  
	public PacMan(int xPac, int yPac, double anchoPista, double altoPista, int[][] matrizPista) {
		this.xPac = xPac;
		this.yPac = yPac;
		this.anchoPista = anchoPista;
		this.altoPista = altoPista;
		this.matrizPista = matrizPista;
		this.pacManAbierto1 = new ImageIcon("PacMan2-izq.png").getImage();
		this.pacManAbierto2 = new ImageIcon("PacMan3-izq.png").getImage();
		this.pacManCerrado = new ImageIcon("PacMan1.png").getImage();
		this.direccionPacman = "izq";
		this.direccionTmp = "izq";
		this.coorX = 26;
		this.peticionIzqDer = false;
		this.peticionIzqDerDir = false;
		this.peticionSubirBajar = false;
		this.peticionSubirBajarDir = false;
		this.coorXTemp = 26;
		this.coorYTemp = 23;
		this.coorY = 23;
		this.velocidad = 3;
		this.pacManTemp = this.pacManAbierto1;
		this.muerto = false;
		this.contadorMuertePacManInicial = 0;
		this.arrayAnimacionMuerte = new int[] {0,1,2,3,4,5,6,-1,-1,7,8,9,10,11,12,8,9,10,11,13,-1};
	}

	public void setXPac(int xPac){
		this.xPac = xPac;
	}

	public void setYPac(int yPac){
		this.yPac = yPac;
	}

	public int getCoorX() {
		return this.coorX;
	}

	public int getCoorY() {
		return this.coorY;
	}

	public int getXPac() {
		return this.xPac;
	}

	public int getYPac() {
		return this.yPac;
	}

	public double getCoorXTemp() {
		return this.coorXTemp;
	}

	public double getCoorYTemp() {
		return this.coorYTemp;
	}

	public String getDireccionPacMan() {
		return this.direccionPacman;
	}

	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}

	public void setMuerto(boolean muerto) {
		this.muerto = muerto;
	}

	public boolean getMuerte() {
		return this.muerto;
	}

	public void setContadorMuertePacManInicial(int contador) {
		this.contadorMuertePacManInicial = contador;
	}

	public void pintaPacman(Graphics g, int contador) {
		this.pacManAbierto1 = new ImageIcon("PacMan2-"+direccionTmp+".png").getImage(); //Se pueden cambiar por variables temporales
		this.pacManAbierto2 = new ImageIcon("PacMan3-"+direccionTmp+".png").getImage();
		this.pacManCerrado = new ImageIcon("PacMan1.png").getImage();
		//System.out.println("contador: " + contador);

		//System.out.println("CoorTemp: x " + this.coorXTemp + ", " + this.coorYTemp);
		if (this.muerto) this.muerto = animarMuerte(g, contador);
		else if (this.coorX > 0 && this.coorX < 51){
			if (this.matrizPista[this.coorY][this.coorX+1] == 1 && this.direccionPacman == "der") this.pacManTemp = this.pacManAbierto1;
			else if (this.matrizPista[this.coorY][this.coorX-1] == 1 && this.direccionPacman == "izq") this.pacManTemp = this.pacManAbierto1;
			else if (this.matrizPista[this.coorY-1][this.coorX] == 1 && this.direccionPacman == "arr") this.pacManTemp = this.pacManAbierto1;
			else if (this.matrizPista[this.coorY+1][this.coorX] == 1 && this.direccionPacman == "aba") this.pacManTemp = this.pacManAbierto1;
			else if(contador%24 < 6) this.pacManTemp = this.pacManAbierto1; // Se puede simplificar este bloque
			else if(contador%24 < 12) this.pacManTemp = this.pacManAbierto2;
			else if(contador%24 < 18) this.pacManTemp = this.pacManAbierto1;
			else this.pacManTemp = this.pacManCerrado;
		}
		else if(contador%24 < 6) this.pacManTemp = this.pacManAbierto1;
		else if(contador%24 < 12) this.pacManTemp = this.pacManAbierto2;
		else if(contador%24 < 18) this.pacManTemp = this.pacManAbierto1;
		else this.pacManTemp = this.pacManCerrado;
		//else this.pacManTemp = this.pacManAbierto2;
		double ajusteY = 0.0;
		if (this.coorYTemp < 14) ajusteY = 3*(this.anchoPista/52)/(20-(this.coorYTemp/3));
		double ajusteX = 1.0;
		if (this.coorXTemp > 26) ajusteX = 1.5;
		if (!this.muerto) g.drawImage(this.pacManTemp, xPac - (int)(.2*ajusteX*this.anchoPista/52), yPac - (int)(-ajusteY + .6*this.anchoPista/52), (int)(1.725*this.anchoPista/52), (int)(1.95*this.anchoPista/52), this);
		else g.drawImage(this.pacManTemp, xPac - (int)(1.4*.2*ajusteX*this.anchoPista/52), yPac - (int)(.85*(ajusteY + .6*this.anchoPista/52)), (int)(1.1*1.725*this.anchoPista/52), (int)(1.125*1.95*this.anchoPista/52), this);

		//g.setColor(Color.RED);
		//g.fillRect(xPac, yPac,(int)(this.anchoPista/52) , (int)(this.anchoPista/52));
	}

	public boolean animarMuerte(Graphics g, int contador) {
		System.out.println(contador - this.contadorMuertePacManInicial);
		if (contador - this.contadorMuertePacManInicial < 105){
			this.pacManTemp = new ImageIcon("PacMan-muerto"+this.arrayAnimacionMuerte[(contador - this.contadorMuertePacManInicial)/5]+".png").getImage();
			return true;
		}
		return false; //Indica que pacman ya revivio
	}
	

	public void manejoVelocidades(double velocidadGlobal, boolean modoHuidaFantasmas) {
		if (coorX > 0 && coorY > 0 && coorX < 51 && coorYTemp < 30){
			if (this.matrizPista[this.coorY][this.coorX] == 0 && modoHuidaFantasmas) {
				this.velocidad = velocidadGlobal*.79;
			}
			else if (this.matrizPista[this.coorY][this.coorX] != 0 && modoHuidaFantasmas) {
				this.velocidad = velocidadGlobal*.9;
			}
			else if (this.matrizPista[this.coorY][this.coorX] == 0) {
				this.velocidad = velocidadGlobal*.71;
			}
			else{
				this.velocidad = velocidadGlobal*.8;
			}
		}
	}

	public void moverPacMan(String direccionPresionada, double velocidadGlobal, boolean modoHuidaFantasmas) {
		manejoVelocidades(velocidadGlobal, modoHuidaFantasmas);
		this.direccionPacman = direccionPresionada;
		movimientoY(this.coorX, this.coorY);
		movimientoX(this.coorX, this.coorY);
		escucharTeclas();
		if ((direccionPacman == "arr" || direccionPacman == "aba") && !this.peticionSubirBajar) direccionTmp = direccionPacman;
		else if ((direccionPacman == "der" || direccionPacman == "izq") && !this.peticionIzqDer) direccionTmp = direccionPacman;
		this.direccionPacman = direccionTmp;
	}

	public String comerPuntitos(int[][] matrizPista){
		if (this.coorX > 0 && this.coorX < 51 && this.coorY > 0 && this.coorY < 30 ){
			if (matrizPista[this.coorY][this.coorX] == 0){
				matrizPista[this.coorY][this.coorX] = 3;
				return "punto";
			}
			else if (matrizPista[this.coorY][this.coorX] == 2){
				matrizPista[this.coorY][this.coorX] = 3;
				return "pellet"; //Si come un pellet
			}
		}
		return "";
	}

	public void movimientoX(int coorX, int coorY){
		this.coorXTemp = (this.xPac + .33*.99*(this.anchoPista/52) -.9928*this.anchoPista/104) / (.9928*(this.anchoPista/52)) + (.02*this.velocidad/3);
		coorX = (int) coorXTemp;
		if(this.direccionTmp == "izq" && this.coorXTemp%1 > .25) coorX += 1;
		
		if ((this.direccionPacman == "arr" || this.direccionPacman == "aba") && (this.direccionTmp == "der" || this.direccionTmp == "izq")){
			this.peticionSubirBajar = true;
			if (this.direccionPacman == "arr") this.peticionSubirBajarDir = true;  //Arriba
			else this.peticionSubirBajarDir = false; //Abajo
			this.direccionPacman = this.direccionTmp;
		}
		
		if (this.peticionSubirBajar && coorX< 51 && coorX > 0 && coorY > 0){
			if (this.peticionSubirBajarDir && this.matrizPista[coorY - 1][coorX] != 1 && coorXTemp%1 < 0.25){
                if (Math.abs((int)coorXTemp - coorXTemp) < 0.25){
					this.setXPac((int)(((int)coorXTemp)*.9928*(this.anchoPista/52)-.3*.9928*(this.anchoPista/52)+.9928*this.anchoPista/104));
					this.direccionPacman = "arr";
					this.peticionSubirBajar = false;
                }
                else if (Math.abs((int)coorXTemp+1 - coorXTemp) < 0.25){
					this.setXPac((int)(((int)coorXTemp+1)*.9928*(this.anchoPista/52)-.3*.9928*(this.anchoPista/52)+.9928*this.anchoPista/104));
					this.direccionPacman = "arr";
					this.peticionSubirBajar = false;
                }
              
			 }
			 else if (!this.peticionSubirBajarDir && this.matrizPista[coorY + 1][coorX] != 1 && coorXTemp%1 < 0.25){

                if (Math.abs((int)coorXTemp - coorXTemp) < 0.25){
					this.setXPac((int)(((int)coorXTemp)*.9928*(this.anchoPista/52)-.3*.9928*(this.anchoPista/52)+.9928*this.anchoPista/104));
					this.direccionPacman = "aba";
					this.peticionSubirBajar = false;
                }
                else if (Math.abs((int)coorXTemp+1 - coorXTemp) < 0.25){
					this.setXPac((int)(((int)coorXTemp+1)*.9928*(this.anchoPista/52)-.3*.9928*(this.anchoPista/52)+.9928*this.anchoPista/104));
					this.direccionPacman = "aba";
					this.peticionSubirBajar = false;
                }
          
			 }	
		}

		this.coorX = coorX;
	}

	

	public void movimientoY (int coorX, int coorY){
		this.coorYTemp = (this.yPac + .39*.9928*(this.altoPista/31) -.99*this.altoPista/62) / (.99*(this.altoPista/31)) + (.02*this.velocidad/3);
		coorY = (int) coorYTemp;
		if(this.direccionTmp == "arr"  && this.coorYTemp%1 > .25) coorY += 1;
		
		if ((this.direccionPacman == "izq" || this.direccionPacman == "der") && (this.direccionTmp == "arr" || this.direccionTmp == "aba")){
			this.peticionIzqDer = true; 
			if (this.direccionPacman == "der") this.peticionIzqDerDir = true;  //Derecha
			else this.peticionIzqDerDir = false; //Izquierda
			this.direccionPacman = this.direccionTmp;
		}

		if (this.peticionIzqDer && coorY > 0  && coorY < 30 && coorYTemp%1 < 0.25){
			if (this.peticionIzqDerDir && this.matrizPista[coorY][this.coorX +1] != 1 && coorX < 50){
				if (Math.abs((int)coorYTemp - coorYTemp) < .25){
					this.setYPac((int)(((int)coorYTemp)*.9928*(this.altoPista/31)-.3*.9928*(this.altoPista/31)+.9928*this.altoPista/62));
					this.direccionPacman = "der";
					this.peticionIzqDer = false;
				}
				else if (Math.abs((int)coorYTemp+1 - coorYTemp) < .25){
					this.setYPac((int)(((int)coorYTemp+1)*.9928*(this.altoPista/31)-.3*.9928*(this.altoPista/31)+.9928*this.altoPista/62));
					this.direccionPacman = "der";
					this.peticionIzqDer = false;
				}
			
			 }
			 else if (!this.peticionIzqDerDir && this.matrizPista[coorY][this.coorX-1] != 1 && coorX > 1 && coorYTemp%1 < 0.25){
				if (Math.abs((int)coorYTemp - coorYTemp) < .25){
					this.setYPac((int)(((int)coorYTemp)*.9928*(this.altoPista/31)-.3*.9928*(this.altoPista/31)+.9928*this.altoPista/62));
					this.direccionPacman = "izq";
					this.peticionIzqDer = false;
				}
				else if (Math.abs((int)coorYTemp+1 - coorYTemp) < .25){
					this.setYPac((int)(((int)coorYTemp+1)*.9928*(this.altoPista/31)-.3*.9928*(this.altoPista/31)+.9928*this.altoPista/62));
					this.direccionPacman = "izq";
					this.peticionIzqDer = false;
				}
			 }
		}
		this.coorY = coorY;	
	}

	public void escucharTeclas(){

		if (this.coorX > 50){
			if (this.direccionPacman == "der"  && this.coorX < 51){
				this.xPac += this.velocidad;
			}
			else if(this.direccionPacman == "der"){
				this.setXPac(0);
			}
			else if (this.direccionPacman == "izq"){
				this.xPac -= this.velocidad;
			}	
		}
		else if (this.coorX < 1) {
			if (this.direccionPacman == "izq" && this.coorX >= -1){
				this.xPac -= this.velocidad;
			}
			else if(this.direccionPacman == "izq"){
				this.setXPac((int)(this.anchoPista-this.anchoPista/52));
			}
			else if (this.direccionPacman == "der"){
				this.xPac += this.velocidad;
			}
		}			
		else {
		

			if (this.direccionPacman == "der" && this.matrizPista[this.coorY][this.coorX + 1] != 1  && this.coorX < 51){
				this.xPac += this.velocidad;
			}	
			else if (this.direccionPacman == "izq" && this.matrizPista[this.coorY][this.coorX - 1] != 1 ){
				this.xPac -= this.velocidad*.9;
			}
			else if (this.direccionPacman == "arr" && this.matrizPista[this.coorY - 1][this.coorX] != 1 ){
				this.yPac -= this.velocidad*.9;
			}
				
			else if (this.direccionPacman == "aba" && this.matrizPista[this.coorY + 1][this.coorX] != 1 ){
				this.yPac += this.velocidad;
			}
		}
			
		}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}

}











