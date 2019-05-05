import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

public class FantasmaRojo implements ImageObserver {

    private int xFRojo,
            yFRojo,
            coorXFRojo,
            coorYFRojo,
            coorXPeticionPasada,
            coorYPeticionPasada;
            
    private double anchoPista,
                   altoPista,
                   coorXFRojoTemp,
                   coorYFRojoTemp;
    private int[][] matrizPista;
    private String direccionFantasmaRojo,
                    direccionFantasmaRojoTemp;

    private boolean peticionAbajo,
                    peticionArriba,
                    peticionDerecha,
                    peticionIzquierda;
    private Image fantasmaRojoImg;

    public FantasmaRojo (int xFRojo, int yFRojo, double anchoPista, double altoPista, int[][] matrizPista){
        //System.out.println("xFROJO Y YFROJO: " + xFRojo +", " +yFRojo);
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
        this.direccionFantasmaRojoTemp = "izq";
        this.peticionDerecha = false;
        this.peticionIzquierda = false;
        this.peticionArriba = false;
        this.peticionAbajo = false;
        this.coorXPeticionPasada = 25;
        this.coorYPeticionPasada = 15;
        this.fantasmaRojoImg = new ImageIcon("Imagenes/fantasmaRojoImg.png").getImage();

    }

    public void setXFRojo (int xFRojo){
        this.xFRojo = xFRojo;
    }

    public void setYFRojo (int yFRojo){
        this.yFRojo = yFRojo;
    }

    public void pintaFantasmaRojo(Graphics g){
        this.fantasmaRojoImg = new ImageIcon("Imagenes/fantasmaRojoImg.png").getImage();
        g.setColor(Color.RED);
        g.drawImage(this.fantasmaRojoImg, this.xFRojo - (int)(.375*this.anchoPista/52), this.yFRojo - (int)(.375*this.anchoPista/52), (int)(1.75*this.anchoPista/52), (int)(1.75*this.anchoPista/52), this);
        g.drawRect(this.xFRojo, this.yFRojo,(int)(this.anchoPista/52), (int)(this.anchoPista/52));
    }

    public void modoPersecusion(int PacManXCoor, int PacManYCoor) {
        System.out.println("double: " + this.coorXFRojoTemp +", " +this.coorYFRojoTemp);
        System.out.println("int: " + this.coorXFRojo +", " +this.coorYFRojo);
        double velocidad = 2;
        
        this.coorXPeticionPasada = this.coorXFRojo;
        this.coorYPeticionPasada = this.coorYFRojo;

        this.coorXFRojoTemp = (this.xFRojo + .33*.9928*(this.anchoPista/52) -.9928*this.anchoPista/104) / (.9928*(this.anchoPista/52)) + .02;
        this.coorYFRojoTemp = (this.yFRojo + .39*.9928*(this.altoPista/31) -.985*this.altoPista/62) / (.985*(this.altoPista/31)) + .02;

        this.coorXFRojo = (int) this.coorXFRojoTemp;
        this.coorYFRojo = (int) this.coorYFRojoTemp;

        if (this.direccionFantasmaRojo == "izq" && this.coorXFRojoTemp%1 > .15) this.coorXFRojo += 1;
        if (this.direccionFantasmaRojo == "arr" && this.coorYFRojoTemp%1 > .15) this.coorYFRojo += 1;
      
      
        //System.out.println("valor izquierda: " + this.matrizPista[this.coorYFRojo][this.coorXFRojo - 1]);
        //System.out.println("valor der: " + this.matrizPista[this.coorYFRojo][this.coorXFRojo + 1]);
        //System.out.println("valor arr: " + this.matrizPista[this.coorYFRojo - 1][this.coorXFRojo]);
        //System.out.println("valor aba: " + this.matrizPista[this.coorYFRojo + 1][this.coorXFRojo]);

        //System.out.println("Direccion: " + this.direccionFantasmaRojo);

        if (!(this.coorXFRojo == PacManXCoor && this.coorYFRojo == PacManYCoor)){ //Mientras no sean iguales
           
            if ((this.matrizPista[this.coorYFRojo][this.coorXFRojo - 1] != 1 || this.matrizPista[this.coorYFRojo][this.coorXFRojo + 1] != 1) && (this.peticionAbajo || this.peticionArriba) && (this.coorYFRojo != this.coorYPeticionPasada)){
              //System.out.println("Peticion arriba abajo desactivada");
                this.peticionArriba = false;
                this.peticionAbajo = false;
            }
            else if ((this.matrizPista[this.coorYFRojo + 1][this.coorXFRojo] != 1 || this.matrizPista[this.coorYFRojo - 1][this.coorXFRojo] != 1 ) && (this.peticionIzquierda || this.peticionDerecha) && (this.coorXFRojo != this.coorXPeticionPasada)){
              //System.out.println("Peticion derecha izquierda desactivada");
                this.peticionIzquierda = false;
                this.peticionDerecha = false;
            }

            // if (this.peticionAbajo)    //System.out.println("Peticion abajo");
            // if (this.peticionArriba)    //System.out.println("Peticion arriba");
            // if (this.peticionDerecha)    //System.out.println("Peticion derecha");
            // if (this.peticionIzquierda)    //System.out.println("Peticion izquierda");

         
            if (this.coorXFRojo > 45 && this.coorYFRojo == 14) this.peticionDerecha = true;
            else if (this.coorXFRojo < 5 && this.coorYFRojo == 14) this.peticionIzquierda = true;
          

            if (this.coorXFRojo <= 50 && this.coorXFRojo > 0 && this.coorYFRojoTemp > 1 && this.coorYFRojoTemp < 30 && (!this.peticionAbajo && !this.peticionArriba && !this.peticionDerecha && !this.peticionIzquierda)) {
                //System.out.println("o vaya: " + Math.abs(this.coorYFRojo - PacManYCoor));
                //System.out.println("o vaya x: " + Math.abs(this.coorXFRojo - PacManXCoor));


                int caminoX = Math.abs(this.coorXFRojo - PacManXCoor),
                    caminoY = Math.abs(this.coorYFRojo - PacManYCoor);


                if (caminoX == caminoY){
                    
                    if (this.matrizPista[this.coorYFRojo-1][this.coorXFRojo] != 1 && this.direccionFantasmaRojo != "aba" ){
                        this.peticionArriba = true;
                           //System.out.println("caso 1");
                    }
                    else if (this.matrizPista[this.coorYFRojo][this.coorXFRojo-1] != 1 && this.direccionFantasmaRojo != "der"){
                        this.peticionIzquierda = true;
                           //System.out.println("caso 2");
                    }
                    else if (this.matrizPista[this.coorYFRojo+1][this.coorXFRojo] != 1 && this.direccionFantasmaRojo != "arr"){
                        this.peticionAbajo = true;
                           //System.out.println("caso 3");
                    }
                    else if (this.matrizPista[this.coorYFRojo][this.coorXFRojo+1] != 1 && this.direccionFantasmaRojo != "izq"){
                        this.peticionDerecha = true;
                           //System.out.println("caso 4");
                    }

                }
                else {
                   
                    if (PacManYCoor < this.coorYFRojo && PacManXCoor >= this.coorXFRojo){ //pACmAN ARRIBA A LA DERECHA
                      //System.out.println("Arriba a la derecha");
                      
                        if (caminoX > caminoY){
                            if (this.matrizPista[this.coorYFRojo][this.coorXFRojo+1] != 1 && this.direccionFantasmaRojo != "izq"){
                                this.peticionDerecha = true;
                                   //System.out.println("caso 5");
                            }
                            else if (this.matrizPista[this.coorYFRojo-1][this.coorXFRojo] != 1 && this.direccionFantasmaRojo != "aba"){
                                this.peticionArriba = true;
                                   //System.out.println("caso 6");
                            }
                            else if (this.direccionFantasmaRojo == "der" || this.direccionFantasmaRojo == "izq"){
                                this.peticionAbajo = true;
                                   //System.out.println("caso 7");
                            }
                            else if (this.direccionFantasmaRojo == "aba" || this.direccionFantasmaRojo == "arr"){
                                this.peticionIzquierda = true;
                                   //System.out.println("caso 8");
                            }
                        }
                        else { // Camiyo Y > Camino X
                            if (this.matrizPista[this.coorYFRojo-1][this.coorXFRojo] != 1 && this.direccionFantasmaRojo != "aba"){
                                this.peticionArriba = true;
                                   //System.out.println("caso 9");
                            }
                            else if (this.matrizPista[this.coorYFRojo][this.coorXFRojo+1] != 1 && this.direccionFantasmaRojo != "izq"){
                                this.peticionDerecha = true;
                                   //System.out.println("caso 10");
                            }
                            else if (this.direccionFantasmaRojo == "der" || this.direccionFantasmaRojo == "izq"){
                                this.peticionAbajo = true;
                                   //System.out.println("caso 11");
                            }
                            else if (this.direccionFantasmaRojo == "aba" || this.direccionFantasmaRojo == "arr"){
                                this.peticionIzquierda = true;
                                   //System.out.println("caso 12");
                            }
                        }
                    }

                    else if (PacManYCoor <= this.coorYFRojo && PacManXCoor <= this.coorXFRojo){ //pACmAN ARRIBA A LA Izquierda
                      //System.out.println("Arriba a la izquierda");
                
                        if (caminoX > caminoY){
                            if (this.matrizPista[this.coorYFRojo][this.coorXFRojo-1] != 1 && this.direccionFantasmaRojo != "der"){
                                this.peticionIzquierda = true;
                                   //System.out.println("caso 13");
                            }
                            else if (this.matrizPista[this.coorYFRojo-1][this.coorXFRojo] != 1 && this.direccionFantasmaRojo != "aba"){
                                this.peticionArriba = true;
                                   //System.out.println("caso 14");
                            }
                            else if (this.direccionFantasmaRojo == "der" || this.direccionFantasmaRojo == "izq"){
                                this.peticionAbajo = true;
                                   //System.out.println("caso 15");
                            }
                            else if (this.direccionFantasmaRojo == "aba" || this.direccionFantasmaRojo == "arr"){
                                this.peticionDerecha = true;
                                   //System.out.println("caso 16");
                            }
                        }
                        else { // Camiyo Y > Camino X
                            if (this.matrizPista[this.coorYFRojo-1][this.coorXFRojo] != 1 && this.direccionFantasmaRojo != "aba"){
                                this.peticionArriba = true;
                                   //System.out.println("caso 17");
                            }
                            else if (this.matrizPista[this.coorYFRojo][this.coorXFRojo-1] != 1 && this.direccionFantasmaRojo != "der"){
                                this.peticionIzquierda = true;
                                   //System.out.println("caso 18");
                            }
                            else if (this.direccionFantasmaRojo == "der" || this.direccionFantasmaRojo == "izq"){
                                this.peticionAbajo = true;
                                   //System.out.println("caso 19");
                            }
                            else if (this.direccionFantasmaRojo == "aba" || this.direccionFantasmaRojo == "arr"){
                                this.peticionDerecha = true;
                                   //System.out.println("caso 20");
                            }
                        }
                
                    }

                    else if (PacManYCoor >= this.coorYFRojo && PacManXCoor >= this.coorXFRojo){ //pACmAN Abajo A LA DERECHA
                      //System.out.println("Abajo a la derecha");
            
                        if (caminoX > caminoY){
                            if (this.matrizPista[this.coorYFRojo][this.coorXFRojo+1] != 1 && this.direccionFantasmaRojo != "izq"){
                                this.peticionDerecha = true;
                                   //System.out.println("caso 21");
                            }
                            else if (this.matrizPista[this.coorYFRojo+1][this.coorXFRojo] != 1 && this.direccionFantasmaRojo != "arr"){
                                this.peticionAbajo = true;
                                   //System.out.println("caso 22");
                            }
                            else if (this.direccionFantasmaRojo == "der" || this.direccionFantasmaRojo == "izq"){
                                this.peticionArriba = true;
                                   //System.out.println("caso 23");
                            }
                            else if (this.direccionFantasmaRojo == "aba" || this.direccionFantasmaRojo == "arr"){
                                this.peticionIzquierda = true;
                                   //System.out.println("caso 24");
                            }
                        }
                        else { // Camiyo Y > Camino X
                            if (this.matrizPista[this.coorYFRojo+1][this.coorXFRojo] != 1 && this.direccionFantasmaRojo != "arr"){
                                this.peticionAbajo = true;
                                   //System.out.println("caso 25");
                            }
                            else if (this.matrizPista[this.coorYFRojo][this.coorXFRojo+1] != 1 && this.direccionFantasmaRojo != "izq"){
                                this.peticionDerecha = true;
                                   //System.out.println("caso 26");
                            }
                            else if (this.direccionFantasmaRojo == "der" || this.direccionFantasmaRojo == "izq"){
                                this.peticionArriba = true;
                                   //System.out.println("caso 27");
                            }
                            else if (this.direccionFantasmaRojo == "aba" || this.direccionFantasmaRojo == "arr"){
                                this.peticionIzquierda = true;
                                   //System.out.println("caso 28");
                            }
                        }    
                    }

                    else if (PacManYCoor >= this.coorYFRojo && PacManXCoor <= this.coorXFRojo){ //pACmAN abajo a la izquierda
                      //System.out.println("Abajo a la izquierda");
                        
                        if (caminoX > caminoY){
                            if (this.matrizPista[this.coorYFRojo][this.coorXFRojo-1] != 1 && this.direccionFantasmaRojo != "der"){
                                this.peticionIzquierda = true;
                                   //System.out.println("caso 29");
                            }
                            else if (this.matrizPista[this.coorYFRojo+1][this.coorXFRojo] != 1 && this.direccionFantasmaRojo != "arr"){
                                this.peticionAbajo = true;
                                   //System.out.println("caso 30");
                            }
                            else if (this.direccionFantasmaRojo == "der" || this.direccionFantasmaRojo == "izq"){
                                this.peticionArriba = true;
                                   //System.out.println("caso 31");
                            }
                            else if (this.direccionFantasmaRojo == "aba" || this.direccionFantasmaRojo == "arr"){
                                this.peticionDerecha = true;
                                   //System.out.println("caso 32");
                            }
                        }
                        else { // Camiyo Y > Camino X
                            if (this.matrizPista[this.coorYFRojo][this.coorXFRojo-1] != 1 && this.direccionFantasmaRojo != "der"){
                                this.peticionIzquierda = true;
                                   //System.out.println("caso 33");
                            }
                            else if (this.matrizPista[this.coorYFRojo+1][this.coorXFRojo] != 1 && this.direccionFantasmaRojo != "arr"){
                                this.peticionAbajo = true;
                                   //System.out.println("caso 34");
                            }
                            else if (this.direccionFantasmaRojo == "der" || this.direccionFantasmaRojo == "izq"){
                                this.peticionArriba = true;
                                   //System.out.println("caso 35");
                            }
                            else if (this.direccionFantasmaRojo == "aba" || this.direccionFantasmaRojo == "arr"){
                                this.peticionDerecha = true;
                                   //System.out.println("caso 36");
                            }
                        }
                
                    }

                }
         
              
            }
               
            
          
        }
        else{
          //System.out.println("Game Over");
        }
        if (peticionAbajo) this.direccionFantasmaRojo = "aba";
        else if (peticionArriba) this.direccionFantasmaRojo = "arr";
        else if (peticionDerecha) this.direccionFantasmaRojo = "der";
        else if (peticionIzquierda) this.direccionFantasmaRojo = "izq";








        
        escucharTeclas(velocidad);
     }


     public void escucharTeclas(double velocidad){

		if (this.coorXFRojo > 45 && this.coorYFRojo == 14){
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
		else if (this.coorXFRojo < 5 && this.coorYFRojo == 14) {
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

			if (this.direccionFantasmaRojo == "der" && this.matrizPista[this.coorYFRojo][this.coorXFRojo + 1] != 1  && this.coorXFRojoTemp < 49.98){
                xFRojo += velocidad;
                
                //this.setYFRojo((int)(this.coorYFRojo*.9928*(this.altoPista/31)-.3*.9928*(this.altoPista/31)+.9928*this.altoPista/62));
			}	
			else if ( this.direccionFantasmaRojo == "izq" && this.matrizPista[this.coorYFRojo][this.coorXFRojo - 1] != 1 && this.coorXFRojoTemp > 1){
                xFRojo -= velocidad;
               
                //this.setYFRojo((int)(this.coorYFRojo*.9928*(this.altoPista/31)-.3*.9928*(this.altoPista/31)+.9928*this.altoPista/62));
			}
			else if (this.direccionFantasmaRojo == "arr" && this.matrizPista[this.coorYFRojo - 1][this.coorXFRojo] != 1 ){
                yFRojo -= velocidad;
                
                //this.setXFRojo((int)(this.coorXFRojo*.9928*(this.anchoPista/52)-.3*.9928*(this.anchoPista/52)+.9928*this.anchoPista/104));
			}
				
			else if (this.direccionFantasmaRojo == "aba" && this.matrizPista[this.coorYFRojo + 1][this.coorXFRojo] != 1 ){
                yFRojo += velocidad;
               
                //this.setXFRojo((int)(this.coorXFRojo*.9928*(this.anchoPista/52)-.3*.9928*(this.anchoPista/52)+.9928*this.anchoPista/104));
			}
		}
			
    }
    @Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}

}