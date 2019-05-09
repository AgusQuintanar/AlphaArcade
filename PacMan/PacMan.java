// Agustin Quintanar y Julio Arath Rosales
// A01636142 y A01630738

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

public class PacMan implements ImageObserver {
	private int xPac,
				  yPac,
				  coorX,
				coorY;

	private Image pacManAbierto,
				  pacManCerrado,
				  pacManTemp;
	private String direccionPacman, 
				direccionTmp;
	private boolean abiertoCerrado,
			peticionSubirBajar,
			peticionIzqDer,
			peticionIzqDerDir,
			peticionSubirBajarDir;
	private double anchoPista, 
					altoPista,
					coorXTemp,
					coorYTemp,
					velocidad;
	private int[][] matrizPista;
				  
	public PacMan(int xPac, int yPac, double anchoPista, double altoPista, int[][] matrizPista) {
		this.xPac = xPac;
		this.yPac = yPac;
		this.anchoPista = anchoPista;
		this.altoPista = altoPista;
		this.matrizPista = matrizPista;
		this.pacManAbierto = new ImageIcon("pac-man-3d-arr.png").getImage();
		this.pacManCerrado = new ImageIcon("PacManIconCerrado-3d-arr.png").getImage();
		this.direccionPacman = "";
		this.abiertoCerrado = true;
		this.direccionTmp = "der";
		this.coorX = 1;
		this.peticionIzqDer = false;
		this.peticionIzqDerDir = false;
		this.peticionSubirBajar = false;
		this.peticionSubirBajarDir = false;
		this.coorXTemp = 1.0;
		this.coorYTemp = 1.0;
		this.coorY = 1;
		this.velocidad = 3;
		this.pacManTemp = this.pacManAbierto;
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

	public String getDireccionPacMan() {
		return this.direccionPacman;
	}

	public void pintaPacman(Graphics g, int contador) {
		this.pacManAbierto = new ImageIcon("pac-man-3d-"+direccionTmp+".png").getImage();
		this.pacManCerrado = new ImageIcon("PacManIconCerrado-3d-"+direccionTmp+".png").getImage();

		if(contador/60.0%.4 < .2) this.pacManTemp = this.pacManAbierto;
		else this.pacManTemp = this.pacManCerrado;
		g.drawImage(this.pacManTemp, xPac - (int)(.25*this.anchoPista/52), yPac - (int)(.3*this.anchoPista/52), (int)(1.5*this.anchoPista/52), (int)(1.5*this.anchoPista/52), this);
	
	}

	public void moverPacMan(String direccionPresionada) {
		this.direccionPacman = direccionPresionada;
		movimientoY(this.coorX, this.coorY);
		movimientoX(this.coorX, this.coorY);
		comerPuntitos();
		escucharTeclas();
		if ((direccionPacman == "arr" || direccionPacman == "aba") && !this.peticionSubirBajar) direccionTmp = direccionPacman;
		else if ((direccionPacman == "der" || direccionPacman == "izq") && !this.peticionIzqDer) direccionTmp = direccionPacman;
		this.direccionPacman = direccionTmp;
	}

	public String comerPuntitos(){
		if (this.coorX > 0 && this.coorX < 51 && this.coorY > 0 && this.coorY < 30 ){
			if (this.matrizPista[this.coorY][this.coorX] == 0){
				this.matrizPista[this.coorY][this.coorX] = 3;
			}
			else if (this.matrizPista[this.coorY][this.coorX] == 2){
				this.matrizPista[this.coorY][this.coorX] = 3;
				return "pellet"; //Si come un pellet
			}
		}
		return "punto";
	}

	public void movimientoX(int coorX, int coorY){
		this.coorXTemp = (this.xPac + .33*.9928*(this.anchoPista/52) -.9928*this.anchoPista/104) / (.9928*(this.anchoPista/52)) + .02;
		coorX = (int) coorXTemp;
		if(this.direccionTmp == "izq" && this.coorXTemp%1 > .15) coorX += 1;
		
		if ((this.direccionPacman == "arr" || this.direccionPacman == "aba") && (this.direccionTmp == "der" || this.direccionTmp == "izq")){
			this.peticionSubirBajar = true;
			if (this.direccionPacman == "arr") this.peticionSubirBajarDir = true;  //Arriba
			else this.peticionSubirBajarDir = false; //Abajo
			this.direccionPacman = this.direccionTmp;
		}
		
		if (this.peticionSubirBajar && coorX< 51 && coorX > 0 && coorY > 0){
			if (this.peticionSubirBajarDir && this.matrizPista[coorY - 1][coorX] != 1 && coorXTemp%1 < .2){
                if (Math.abs((int)coorXTemp - coorXTemp) < 0.15){
					this.setXPac((int)(((int)coorXTemp)*.9928*(this.anchoPista/52)-.3*.9928*(this.anchoPista/52)+.9928*this.anchoPista/104));
					this.direccionPacman = "arr";
					this.peticionSubirBajar = false;
                }
                else if (Math.abs((int)coorXTemp+1 - coorXTemp) < 0.15){
					this.setXPac((int)(((int)coorXTemp+1)*.9928*(this.anchoPista/52)-.3*.9928*(this.anchoPista/52)+.9928*this.anchoPista/104));
					this.direccionPacman = "arr";
					this.peticionSubirBajar = false;
                }
              
			 }
			 else if (!this.peticionSubirBajarDir && this.matrizPista[coorY + 1][coorX] != 1 && coorXTemp%1 < .2){

                if (Math.abs((int)coorXTemp - coorXTemp) < 0.15){
					this.setXPac((int)(((int)coorXTemp)*.9928*(this.anchoPista/52)-.3*.9928*(this.anchoPista/52)+.9928*this.anchoPista/104));
					this.direccionPacman = "aba";
					this.peticionSubirBajar = false;
                }
                else if (Math.abs((int)coorXTemp+1 - coorXTemp) < 0.15){
					this.setXPac((int)(((int)coorXTemp+1)*.9928*(this.anchoPista/52)-.3*.9928*(this.anchoPista/52)+.9928*this.anchoPista/104));
					this.direccionPacman = "aba";
					this.peticionSubirBajar = false;
                }
          
			 }	
		}

		this.coorX = coorX;
	}

	

	public void movimientoY (int coorX, int coorY){
		this.coorYTemp = (this.yPac + .39*.9928*(this.altoPista/31) -.985*this.altoPista/62) / (.985*(this.altoPista/31));
		coorY = (int) coorYTemp;
		if(this.direccionTmp == "arr"  && this.coorYTemp%1 > .15) coorY += 1;
		
		if ((this.direccionPacman == "izq" || this.direccionPacman == "der") && (this.direccionTmp == "arr" || this.direccionTmp == "aba")){
			this.peticionIzqDer = true; 
			if (this.direccionPacman == "der") this.peticionIzqDerDir = true;  //Derecha
			else this.peticionIzqDerDir = false; //Izquierda
			this.direccionPacman = this.direccionTmp;
		}

		if (this.peticionIzqDer && coorY > 0  && coorY < 30 && coorYTemp%1 < .2){
			if (this.peticionIzqDerDir && this.matrizPista[coorY][this.coorX +1] != 1 && coorX < 50){
				if (Math.abs((int)coorYTemp - coorYTemp) < .15){
					this.setYPac((int)(((int)coorYTemp)*.9928*(this.altoPista/31)-.3*.9928*(this.altoPista/31)+.9928*this.altoPista/62));
					this.direccionPacman = "der";
					this.peticionIzqDer = false;
				}
				else if (Math.abs((int)coorYTemp+1 - coorYTemp) < .15){
					this.setYPac((int)(((int)coorYTemp+1)*.9928*(this.altoPista/31)-.3*.9928*(this.altoPista/31)+.9928*this.altoPista/62));
					this.direccionPacman = "der";
					this.peticionIzqDer = false;
				}
			
			 }
			 else if (!this.peticionIzqDerDir && this.matrizPista[coorY][this.coorX-1] != 1 && coorX > 1 && coorYTemp%1 < .2){
				if (Math.abs((int)coorYTemp - coorYTemp) < .15){
					this.setYPac((int)(((int)coorYTemp)*.9928*(this.altoPista/31)-.3*.9928*(this.altoPista/31)+.9928*this.altoPista/62));
					this.direccionPacman = "izq";
					this.peticionIzqDer = false;
				}
				else if (Math.abs((int)coorYTemp+1 - coorYTemp) < .15){
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
			if (this.direccionPacman == "der"  && this.coorX < 52){
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
				this.xPac -= this.velocidad;
			}
			else if (this.direccionPacman == "arr" && this.matrizPista[this.coorY - 1][this.coorX] != 1 ){
				this.yPac -= this.velocidad;
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











