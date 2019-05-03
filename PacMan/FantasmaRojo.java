import java.awt.Color;
import java.awt.Graphics;

public class FantasmaRojo {

    private int xFRojo,
            yFRojo,
            coorXFRojo,
            coorYFRojo;
            
    private double anchoPista,
                   altoPista,
                   coorXFRojoTemp,
                   coorYFRojoTemp;
    private int[][] matrizPista;
    private String direccionFantasmaRojo;

    private boolean peticionAbajo,
                    peticionArriba,
                    peticionDerecha,
                    peticionIzquierda;

    public FantasmaRojo (int xFRojo, int yFRojo, double anchoPista, double altoPista, int[][] matrizPista){
        System.out.println("xFROJO Y YFROJO: " + xFRojo +", " +yFRojo);
        this.xFRojo = xFRojo;
        this.yFRojo = yFRojo;
        this.anchoPista = anchoPista;
        this.altoPista = altoPista;
        this.coorXFRojo = 25;
        this.coorYFRojo = 15;
        this.coorXFRojoTemp = 25;
        this.coorYFRojoTemp = 15;
        this.matrizPista = matrizPista;
        this.direccionFantasmaRojo = "izq";
        this.peticionDerecha = false;
        this.peticionIzquierda = false;
        this.peticionArriba = false;
        this.peticionAbajo = false;
    }

    public void setXFRojo (int xFRojo){
        this.xFRojo = xFRojo;
    }

    public void setYFRojo (int yFRojo){
        this.yFRojo = yFRojo;
    }

    public void pintaFantasmaRojo(Graphics g){
        g.setColor(Color.RED);
        g.drawRect(this.xFRojo, this.yFRojo,(int)(this.anchoPista/52), (int)(this.anchoPista/52));
    }

    public void modoPersecusion(int PacManXCoor, int PacManYCoor) {
        // System.out.println("double: " + this.coorXFRojoTemp +", " +this.coorYFRojoTemp);
        // System.out.println("int: " + this.coorXFRojo +", " +this.coorYFRojo);
        int velocidad = 3;
        int distanciaCasillas = 0;
        this.coorXFRojoTemp = (this.xFRojo + .33*.9928*(this.anchoPista/52) -.9928*this.anchoPista/104) / (.9928*(this.anchoPista/52)) + .02;
        this.coorYFRojoTemp = (this.yFRojo + .39*.9928*(this.altoPista/31) -.985*this.altoPista/62) / (.985*(this.altoPista/31));

        this.coorXFRojo = (int) this.coorXFRojoTemp;
        this.coorYFRojo = (int) this.coorYFRojoTemp;

        if (this.direccionFantasmaRojo == "izq" && this.coorXFRojoTemp%1 > .15) this.coorXFRojo += 1;
        if (this.direccionFantasmaRojo == "arr" && this.coorYFRojoTemp%1 > .15) this.coorYFRojo += 1;
      
        System.out.println("Coor X: "+ this.coorXFRojoTemp + ", Y: " + this.coorYFRojoTemp);
        System.out.println("Int X: "+ this.coorXFRojo + ", Y: " + this.coorYFRojo);


        if (!(this.coorXFRojo == PacManXCoor && this.coorYFRojo == PacManYCoor)){ //Mientras no sean iguales
           
            if (this.coorXFRojo < 50 && this.coorXFRojo > 0 && this.coorYFRojoTemp > 1 && this.coorYFRojoTemp < 30) {
                System.out.println("o vaya: " + Math.abs(this.coorYFRojo - PacManYCoor));
                System.out.println("o vaya x: " + Math.abs(this.coorXFRojo - PacManXCoor));

                int caminoX = Math.abs(this.coorXFRojo - PacManXCoor),
                    caminoY = Math.abs(this.coorYFRojo - PacManYCoor);


                if (PacManYCoor <= this.coorYFRojo && PacManXCoor >= this.coorXFRojo){ //pACmAN ARRIBA A LA DERECHA
                    System.out.println("Arriba a la derecha");
                    if (caminoX >= caminoY){
                        if (this.matrizPista[this.coorYFRojo][this.coorXFRojo+1] != 1 && !this.peticionArriba){
                            this.direccionFantasmaRojo = "der";
                            this.peticionDerecha = false;
                        }
                        else if (this.matrizPista[this.coorYFRojo-1][this.coorXFRojo] != 1 && !this.peticionDerecha){
                            this.direccionFantasmaRojo = "arr";
                            this.peticionArriba = false;
                        }
                        else if (this.direccionFantasmaRojo == "der"){
                            this.direccionFantasmaRojo = "aba";
                            this.peticionDerecha = true;
                        }
                        else if (this.direccionFantasmaRojo == "arr"){
                            this.direccionFantasmaRojo = "izq";
                            this.peticionArriba = true;
                        }
                    }
                    else { // Camiyo Y > Camino X
                        if (this.matrizPista[this.coorYFRojo-1][this.coorXFRojo] != 1 && !this.peticionDerecha){
                            this.direccionFantasmaRojo = "arr";
                            this.peticionArriba = false;
                        }
                        else if (this.matrizPista[this.coorYFRojo][this.coorXFRojo+1] != 1 && !this.peticionArriba){
                            this.direccionFantasmaRojo = "der";
                            this.peticionDerecha = false;
                        }
                   
                        else if (this.direccionFantasmaRojo == "der"){
                            this.direccionFantasmaRojo = "aba";
                            this.peticionDerecha = true;
                        }
                        else if (this.direccionFantasmaRojo == "arr"){
                            this.direccionFantasmaRojo = "izq";
                            this.peticionArriba = true;
                        }
                    }
                }

                else if (PacManYCoor <= this.coorYFRojo && PacManXCoor < this.coorXFRojo){ //pACmAN ARRIBA A LA Izquierda
                    System.out.println("Arriba a la izquierda");
                    if (caminoX >= caminoY){
                        if (this.matrizPista[this.coorYFRojo][this.coorXFRojo-1] != 1 && !this.peticionArriba){
                            this.direccionFantasmaRojo = "izq";
                            this.peticionIzquierda = false;
                        }
                        else if (this.matrizPista[this.coorYFRojo-1][this.coorXFRojo] != 1 && !this.peticionIzquierda){
                            this.direccionFantasmaRojo = "arr";
                            this.peticionArriba = false;
                        }
                        else if (this.direccionFantasmaRojo == "izq"){
                            this.direccionFantasmaRojo = "aba";
                            this.peticionIzquierda = true;
                        }
                        else if (this.direccionFantasmaRojo == "arr"){
                            this.direccionFantasmaRojo = "der";
                            this.peticionArriba = true;
                        }
                    }
                    else { // Camiyo Y > Camino X
                        if (this.matrizPista[this.coorYFRojo-1][this.coorXFRojo] != 1 && !this.peticionIzquierda){
                            this.direccionFantasmaRojo = "arr";
                            this.peticionArriba = false;
                        }
                        else if (this.matrizPista[this.coorYFRojo][this.coorXFRojo-1] != 1 && !this.peticionArriba){
                            this.direccionFantasmaRojo = "izq";
                            this.peticionIzquierda = false;
                        }
                   
                        else if (this.direccionFantasmaRojo == "izq"){
                            this.direccionFantasmaRojo = "aba";
                            this.peticionIzquierda = true;
                        }
                        else if (this.direccionFantasmaRojo == "arr"){
                            this.direccionFantasmaRojo = "der";
                            this.peticionArriba = true;
                        }
                    }
                }

                else if (PacManYCoor > this.coorYFRojo && PacManXCoor >= this.coorXFRojo){ //pACmAN Abajo A LA DERECHA
                    System.out.println("Abajo a la derecha");
                    if (caminoX >= caminoY){
                        if (this.matrizPista[this.coorYFRojo][this.coorXFRojo+1] != 1 && !this.peticionAbajo){
                            this.direccionFantasmaRojo = "der";
                            this.peticionDerecha = false;
                        }
                        else if (this.matrizPista[this.coorYFRojo+1][this.coorXFRojo] != 1 && !this.peticionDerecha){
                            this.direccionFantasmaRojo = "aba";
                            this.peticionAbajo = false;
                        }
                        else if (this.direccionFantasmaRojo == "der"){
                            this.direccionFantasmaRojo = "arr";
                            this.peticionDerecha = true;
                        }
                        else if (this.direccionFantasmaRojo == "aba"){
                            this.direccionFantasmaRojo = "izq";
                            this.peticionAbajo = true;
                        }
                    }
                    else { // Camiyo Y > Camino X
                        if (this.matrizPista[this.coorYFRojo+1][this.coorXFRojo] != 1 && !this.peticionDerecha){
                            this.direccionFantasmaRojo = "aba";
                            this.peticionAbajo = false;
                        }
                        else if (this.matrizPista[this.coorYFRojo][this.coorXFRojo+1] != 1 && !this.peticionAbajo){
                            this.direccionFantasmaRojo = "der";
                            this.peticionDerecha = false;
                        }
                   
                        else if (this.direccionFantasmaRojo == "der"){
                            this.direccionFantasmaRojo = "aba";
                            this.peticionDerecha = true;
                        }
                        else if (this.direccionFantasmaRojo == "aba"){
                            this.direccionFantasmaRojo = "izq";
                            this.peticionAbajo = true;
                        }
                    }
                }

                else if (PacManYCoor > this.coorYFRojo && PacManXCoor < this.coorXFRojo){ //pACmAN abajo a la izquierda
                    System.out.println("Abajo a la izquierda");
                    if (caminoX >= caminoY){
                        if (this.matrizPista[this.coorYFRojo][this.coorXFRojo-1] != 1 && !this.peticionAbajo){
                            this.direccionFantasmaRojo = "izq";
                            this.peticionIzquierda = false;
                        }
                        else if (this.matrizPista[this.coorYFRojo+1][this.coorXFRojo] != 1 && !this.peticionIzquierda){
                            this.direccionFantasmaRojo = "aba";
                            this.peticionAbajo = false;
                        }
                        else if (this.direccionFantasmaRojo == "izq"){
                            this.direccionFantasmaRojo = "arr";
                            this.peticionIzquierda = true;
                        }
                        else if (this.direccionFantasmaRojo == "aba"){
                            this.direccionFantasmaRojo = "der";
                            this.peticionAbajo = true;
                        }
                    }
                    else { // Camiyo Y > Camino X
                        if (this.matrizPista[this.coorYFRojo+1][this.coorXFRojo] != 1 && !this.peticionIzquierda){
                            this.direccionFantasmaRojo = "aba";
                            this.peticionAbajo = false;
                        }
                        else if (this.matrizPista[this.coorYFRojo][this.coorXFRojo-1] != 1 && !this.peticionAbajo){
                            this.direccionFantasmaRojo = "izq";
                            this.peticionIzquierda = false;
                        }
                   
                        else if (this.direccionFantasmaRojo == "izq"){
                            this.direccionFantasmaRojo = "arr";
                            this.peticionIzquierda = true;
                        }
                        else if (this.direccionFantasmaRojo == "aba"){
                            this.direccionFantasmaRojo = "der";
                            this.peticionAbajo = true;
                        }
                    }
                }


         
              
            }
               
            
          
        }
        else{
            System.out.println("Game Over");
        }
        escucharTeclas(velocidad);
     }


     public void escucharTeclas(int velocidad){

		if (this.coorXFRojo >= 50){
			if (this.direccionFantasmaRojo == "der"  && this.coorXFRojo < 52){
				xFRojo += velocidad;
			}
			else if(this.direccionFantasmaRojo == "der"){
				this.setXFRojo(0);
			}
			else if (this.direccionFantasmaRojo == "izq"){
				xFRojo -= velocidad;
			}	
		}
		else if (this.coorXFRojo < 1) {
			if (this.direccionFantasmaRojo == "izq" && this.coorXFRojo >= -1){
				xFRojo -= velocidad;
			}
			else if(this.direccionFantasmaRojo == "izq"){
				this.setXFRojo((int)(this.anchoPista-this.anchoPista/52));
			}
			else if (this.direccionFantasmaRojo == "der"){
				xFRojo += velocidad;
			}
		}			
		else {
			// if (this.matrizPista[this.coorYFRojo][this.coorXFRojo+1] == 1 && this.direccionTmp == "der") this.pared = true;
			// else if (this.matrizPista[this.coorYFRojo][this.coorXFRojo-1] == 1 && this.direccionTmp == "izq") this.pared = true;
			// else if (this.matrizPista[this.coorYFRojo+1][this.coorXFRojo] == 1 && this.direccionTmp == "aba") this.pared = true;
			// else if (this.matrizPista[this.coorYFRojo-1][this.coorXFRojo] == 1 && this.direccionTmp == "arr") this.pared = true;
			// else this.pared = false;

			if (this.direccionFantasmaRojo == "der" && this.matrizPista[this.coorYFRojo][this.coorXFRojo + 1] != 1  && this.coorXFRojo < 51){
				xFRojo += velocidad;
			}	
			else if (this.direccionFantasmaRojo == "izq" && this.matrizPista[this.coorYFRojo][this.coorXFRojo - 1] != 1 ){
				xFRojo -= velocidad;
			}
			else if (this.direccionFantasmaRojo == "arr" && this.matrizPista[this.coorYFRojo - 1][this.coorXFRojo] != 1 ){
				yFRojo -= velocidad;
			}
				
			else if (this.direccionFantasmaRojo == "aba" && this.matrizPista[this.coorYFRojo + 1][this.coorXFRojo] != 1 ){
				yFRojo += velocidad;
			}
		}
			
		}
}