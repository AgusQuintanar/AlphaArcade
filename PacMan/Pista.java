import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Pista extends JPanel implements Runnable, KeyListener {
	private PacMan pacman;
	private FantasmaRojo fantasmaRojo;
	private Thread hilo;
	private String direccionPacman, 
				   direccionTmp;
	private boolean abiertoCerrado,
					subirBajar,
					avanzarIzquierdaDerecha,
					peticionSubirBajar,
					peticionIzqDer,
					peticionIzqDerDir,
					peticionSubirBajarDir;
	private Image pista;
	private double ancho, alto,
					coorXTemp,
					coorYTemp;
	private int[][] matrizPista;
	private int contador,
				coorX,
				coorY;


	public Pista(double ancho) {
		super();
		int anchoOriginal = 1890, altoOriginal = 1131;
		double proporcionAncho = ancho / anchoOriginal; // Considerando un ancho del 100%
		System.out.println(ancho + " " + alto);
		this.ancho = (int) (anchoOriginal * proporcionAncho);
		this.alto = (int) (altoOriginal * proporcionAncho);
		System.out.println(proporcionAncho);
		System.out.println(("ancho: " + this.ancho) + " alto: " + this.alto);
		this.setPreferredSize(new Dimension((int) this.ancho, (int) this.alto));
		this.setBackground(Color.BLACK);
		this.pacman = new PacMan((int) (this.ancho / 2 - this.ancho / 104),
		(int)((17)*.985*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), (int) (.985*(this.ancho / 52)));
		this.direccionPacman = "";
		this.fantasmaRojo = new FantasmaRojo((int) (this.ancho / 2 - this.ancho / 104),
		(int) (.95 * this.alto / 2 - .95 * this.alto / 62), this.ancho, this.alto, this.matrizPista);
		this.abiertoCerrado = true;
		this.direccionTmp = "der";
		this.subirBajar = true;
		this.avanzarIzquierdaDerecha = true;
		this.coorX = 0;
		this.peticionIzqDer = false;
		this.peticionIzqDerDir = false;
		this.peticionSubirBajar = false;
		this.peticionSubirBajarDir = false;
		this.coorXTemp = 0.0;
		this.coorYTemp = 0.0;
		this.coorY = 0;
		System.out.println(this.ancho + ", " + this.alto);
		this.pista = new ImageIcon("Imagenes/PistaPacMan.png").getImage();
		this.setFocusable(true);
		this.addKeyListener(this);
		this.contador = 0;
		this.hilo = new Thread(this);
		this.hilo.start();
		this.matrizPista = new int[][] {
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1 },
				{ 1, 2, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 2, 1 },
				{ 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1 },
				{ 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 3, 1, 3, 3, 3, 3, 3, 3, 1, 3, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{ 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3 },
				{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 3, 1, 3, 3, 3, 3, 3, 3, 1, 3, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1 },
				{ 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1 },
				{ 1, 2, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 2, 1 },
				{ 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1 },
				{ 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
				{ 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } 
			};

	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.pista, 0, 0, (int) this.ancho, (int) this.alto, this);
		g.setColor(Color.WHITE);
		//g.fillRect(this.pacman.xPac, this.pacman.yPac, (int)(this.ancho/52), (int)(this.ancho/52));
		for (int y = 0; y < this.matrizPista.length-1; y++){
			for (int x = 0; x < this.matrizPista[y].length-1; x++){
				if (this.matrizPista[y][x] == 0){
					g.fillOval((int)((x+1)*.99*(this.ancho/52)-.33*.99*(this.ancho/52)), (int)((y+1)*.985*(this.alto/31)-.39*.985*(this.alto/31)), (int)(this.ancho/52/3.5),(int)(this.alto/31/3.5));
				}
				else if (this.matrizPista[y][x] == 2 && this.contador < 7) {
					g.fillOval((int)((x+1)*.99*(this.ancho/52)-.27*.99*(this.ancho/52)-this.ancho/104), (int)((y+1)*.99*(this.alto/31)-.39*.99*(this.alto/31)-this.alto/62), (int)(this.ancho/52*1.1), (int)(this.alto/31*1.1));
				}
			}
		}
		this.fantasmaRojo.pintaFantasmaRojo(g);
		this.pacman.pintaPacman(g, this.abiertoCerrado, this.direccionTmp);
	}

	public void run() {
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks, delta = 0.0;
		int fps = 0;

		long timer = System.currentTimeMillis();

		while (true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				render();
				this.contador++;
				fps++;
				delta--;

			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println("Fps " + fps);
				fps = 0;
			}

		}
	}

	private void tick() {
		int velocidad = 3;
		//.out.println(coorX + ", " + coorY);

		movimientoY(this.coorX, this.coorY);
		movimientoX(this.coorX, this.coorY);

		
		comerPuntitos(this.coorX, this.coorY);
		escucharTeclas(velocidad);

		comportamientoPacman();
		//System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
	}

	private void render() {
		this.repaint();
	}

	public void comerPuntitos(int x, int y){
		if (x > 0 && x < 51 && y > 0 && y < 30 ){
			if (this.matrizPista[y][x] == 0){
				this.matrizPista[y][x] = 3;
			}
			if (this.matrizPista[y][x] == 2){
				this.matrizPista[y][x] = 3;
			}
		}
	}

	public void movimientoX(int coorX, int coorY){
		this.coorXTemp = (this.pacman.xPac + .33*.9928*(this.ancho/52) -.9928*this.ancho/104) / (.9928*(this.ancho/52)) + .02;
		
		boolean direccionX = true; //derecha
		coorX = (int) coorXTemp;
		if(this.direccionTmp == "izq" && this.coorXTemp%1 > .15){
			coorX += 1;
			direccionX = false;
		}
		
		System.out.println("CoooXTemp: " + coorXTemp);
		System.out.println("CoorXINT: " + coorX);
		
		if ((this.direccionPacman == "arr" || this.direccionPacman == "aba") && (this.direccionTmp == "der" || this.direccionTmp == "izq")){
            System.out.println("Peticion activada");
			this.peticionSubirBajar = true;
			if (this.direccionPacman == "arr") this.peticionSubirBajarDir = true;  //Arriba
			else this.peticionSubirBajarDir = false; //Abajo
			
			this.direccionPacman = this.direccionTmp;
		}
		

		if (this.peticionSubirBajar && coorX< 51 && coorX > 0 && coorY > 0){
			
			if (this.peticionSubirBajarDir && this.matrizPista[coorY - 1][coorX] != 1 ){
                if (Math.abs((int)coorXTemp - coorXTemp) < 0.5){
                    this.pacman.setXPac((int)(((int)coorXTemp)*.9928*(this.ancho/52)-.3*.9928*(this.ancho/52)+.9928*this.ancho/104));
                }
                else if (Math.abs((int)coorXTemp+1 - coorXTemp) < 0.5){
                    this.pacman.setXPac((int)(((int)coorXTemp+1)*.9928*(this.ancho/52)-.3*.9928*(this.ancho/52)+.9928*this.ancho/104));
                }
                this.direccionPacman = "arr";
                this.peticionSubirBajar = false;
			 }
			 else if (!this.peticionSubirBajarDir && this.matrizPista[coorY + 1][coorX] != 1){

                if (Math.abs((int)coorXTemp - coorXTemp) < 0.5){
                    this.pacman.setXPac((int)(((int)coorXTemp)*.9928*(this.ancho/52)-.3*.9928*(this.ancho/52)+.9928*this.ancho/104));
                }
                else if (Math.abs((int)coorXTemp+1 - coorXTemp) < 0.5){
                    this.pacman.setXPac((int)(((int)coorXTemp+1)*.9928*(this.ancho/52)-.3*.9928*(this.ancho/52)+.9928*this.ancho/104));
                }
                this.direccionPacman = "aba";
                this.peticionSubirBajar = false;
			 }	
		}

		this.coorX = coorX;
	}
	

	public void movimientoY (int coorX, int coorY){
		this.coorYTemp = (this.pacman.yPac + .39*.9928*(this.alto/31) -.985*this.alto/62) / (.985*(this.alto/31));
		coorY = (int) coorYTemp;


		if(this.direccionTmp == "arr"  && this.coorYTemp%1 > .15){
			coorY += 1;
		}
		
		System.out.println("CoooYTemp: " + coorYTemp);
		System.out.println("CoorYINT: " + coorY);

		
		if ((this.direccionPacman == "izq" || this.direccionPacman == "der") && (this.direccionTmp == "arr" || this.direccionTmp == "aba")){
			this.peticionIzqDer = true; 
			if (this.direccionPacman == "der") this.peticionIzqDerDir = true;  //Derecha
			else this.peticionIzqDerDir = false; //Izquierda
			this.direccionPacman = this.direccionTmp;
		}



		if (this.peticionIzqDer && coorY > 0  && coorY < 30){
			if (this.peticionIzqDerDir && this.matrizPista[coorY][this.coorX +1] != 1 && coorX < 50){
				if (Math.abs((int)coorYTemp - coorYTemp) < .5){
					this.pacman.setYPac((int)(((int)coorYTemp)*.9928*(this.alto/31)-.3*.9928*(this.alto/31)+.9928*this.alto/62));
				}
				else if (Math.abs((int)coorYTemp+1 - coorYTemp) < .5){
					this.pacman.setYPac((int)(((int)coorYTemp+1)*.9928*(this.alto/31)-.3*.9928*(this.alto/31)+.9928*this.alto/62));
				}
				this.direccionPacman = "der";
				this.peticionIzqDer = false;
			 }
			 else if (!this.peticionIzqDerDir && this.matrizPista[coorY][this.coorX-1] != 1 && coorX > 1){
				if (Math.abs((int)coorYTemp - coorYTemp) < .5){
					this.pacman.setYPac((int)(((int)coorYTemp)*.9928*(this.alto/31)-.3*.9928*(this.alto/31)+.9928*this.alto/62));
				}
				else if (Math.abs((int)coorYTemp+1 - coorYTemp) < .5){
					this.pacman.setYPac((int)(((int)coorYTemp+1)*.9928*(this.alto/31)-.3*.9928*(this.alto/31)+.9928*this.alto/62));
				}
				this.direccionPacman = "izq";
				this.peticionIzqDer = false;
			 }
		}
		//System.out.println("Paso segundo if de Y");
		this.coorY = coorY;	
	}

	
    public void escucharTeclas(int velocidad){
		if (this.coorX > 50){
			
			if (this.direccionPacman == "der"  && this.coorX < 52){
				this.pacman.xPac += velocidad;
			}
			else if(this.direccionPacman == "der"){
				this.pacman.setXPac(0);
			}
			else if (this.direccionPacman == "izq"){
				this.pacman.xPac -= velocidad;
			}
			
	
		}
		else if (this.coorX < 1) {
			if (this.direccionPacman == "izq" && this.coorX >= -1){
				this.pacman.xPac -= velocidad;
			}
			else if(this.direccionPacman == "izq"){
				this.pacman.setXPac((int)(this.ancho-this.ancho/52));
			}
			else if (this.direccionPacman == "der"){
				this.pacman.xPac += velocidad;
			}

		}
					
		
		else {
            //System.out.println("afneovernvoeewr: " +this.coorXTemp);
            //System.out.println("babababa: " + (velocidad/this.ancho)*52);
			if (this.direccionPacman == "der" && this.matrizPista[this.coorY][this.coorX + 1] != 1  && this.coorX < 51){
                
				this.pacman.xPac += velocidad;
			}	
			else if (this.direccionPacman == "izq" && this.matrizPista[this.coorY][(int)(this.coorXTemp - (velocidad/this.ancho)*52) + 1- 1] != 1 ){
				this.pacman.xPac -= velocidad;
			}
			else if (this.direccionPacman == "arr" && this.matrizPista[(int)(this.coorYTemp - (velocidad/this.alto)*31) + 1 - 1][this.coorX] != 1){
				this.pacman.yPac -= velocidad;
			}
				
			else if (this.direccionPacman == "aba" && this.matrizPista[this.coorY + 1][this.coorX] != 1 ){
				this.pacman.yPac += velocidad;
			}
		}
		
			
	}	
		
		
			
	

		
		
			
	

	public void comportamientoPacman(){
		if ((direccionPacman == "arr" || direccionPacman == "aba") && !this.peticionSubirBajar) direccionTmp = direccionPacman;
		else if ((direccionPacman == "der" || direccionPacman == "izq") && !this.peticionIzqDer) direccionTmp = direccionPacman;
		this.direccionPacman = direccionTmp;

		if (this.contador % 7 == 0) this.abiertoCerrado = false;

		else if (this.contador % 15 == 0) {
			this.abiertoCerrado = true;
			this.contador = 0; 
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			this.direccionPacman = "der";
		else if (e.getKeyCode() == KeyEvent.VK_LEFT)
			this.direccionPacman = "izq";
		else if (e.getKeyCode() == KeyEvent.VK_UP)
			this.direccionPacman = "arr";
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
			this.direccionPacman = "aba";
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			this.direccionPacman = "";
		else if (e.getKeyCode() == KeyEvent.VK_LEFT)
			this.direccionPacman = "";
		else if (e.getKeyCode() == KeyEvent.VK_UP)
			this.direccionPacman = "";
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
			this.direccionPacman = "";
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}


}