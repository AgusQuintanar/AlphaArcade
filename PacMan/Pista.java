// Agustin Quintanar y Julio Arath Rosales
// A01636142 y A01630738

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
import java.util.Timer;

public class Pista extends JPanel implements Runnable, KeyListener {
	private PacMan pacman;
	private FantasmaBlinky fantasmaBlinky;
	private FantasmaPinky fantasmaPinky;
	private FantasmaClyde fantasmaClyde;
	private FantasmaInky fantasmaInky;
	private Thread hilo;
	private String direccionPacman, 
				   direccionTmp;
	private boolean abiertoCerrado,
					peticionSubirBajar,
					peticionIzqDer,
					peticionIzqDerDir,
					peticionSubirBajarDir,
					pared,
					salioPinky,
					salioBlinky,
					salioClyde,
					salioInky,
					modoHuidaActivado,
					jugar;
	private Image pista;
	private double ancho, 
					alto,
					coorXTemp,
					coorYTemp;
	private int[][] matrizPista;
	private int contador,
				coorX,
				coorY,
				vidas;
	private long tiempoDeInicio,
				 cronometro, 
				 tiempoBlinky,
				 tiempoPinky,
				 tiempoInky,
				 tiempoClyde,
				 tiempoInicioBlinky,
				 tiempoInicioPinky,
				 tiempoInicioInky,
				 tiempoInicioClyde,
				 tiempoModoHuida;


	public Pista(double ancho) {
		super();
		int anchoOriginal = 1890, altoOriginal = 1131;
		double proporcionAncho = ancho / anchoOriginal; // Considerando un ancho del 100%
		//System.out.println(ancho + " " + alto);
		this.ancho = (int) (anchoOriginal * proporcionAncho);
		this.alto = (int) (altoOriginal * proporcionAncho);
		//System.out.println(proporcionAncho);
		//System.out.println(("ancho: " + this.ancho) + " alto: " + this.alto);
		this.setPreferredSize(new Dimension((int) this.ancho, (int) this.alto));
		this.setBackground(Color.BLACK);
		this.pacman = new PacMan((int) (this.ancho / 2 - this.ancho / 104),
		(int)((17)*.985*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), (int) (1.5*(this.ancho / 52)));
		this.direccionPacman = "";
		this.abiertoCerrado = true;
		this.direccionTmp = "der";
		this.coorX = 1;
		this.peticionIzqDer = false;
		this.peticionIzqDerDir = false;
		this.peticionSubirBajar = false;
		this.peticionSubirBajarDir = false;
		this.pared = false;
		this.coorXTemp = 1.0;
		this.coorYTemp = 1.0;
		this.coorY = 1;
		//System.out.println(this.ancho + ", " + this.alto);
		this.pista = new ImageIcon("PistaPacMan.png").getImage();
		this.setFocusable(true);
		this.addKeyListener(this);
		this.contador = 0;
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
				{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 3, 1, 6, 6, 6, 6, 6, 6, 1, 3, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{ 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 3, 3, 3, 1, 6, 6, 6, 6, 6, 6, 1, 3, 3, 3, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3 },
				{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 3, 1, 6, 6, 6, 6, 6, 6, 1, 3, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 5, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 5, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
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

			//Simbologia:
			//
			// 0 - Espacio hueco con puntito
			// 1 - Espacio con pared
			// 2 - Espacio con pellet
			// 3 - Espacio hueco sin puntito
			// 4 - Espacio hueco donde los fantasmas pueden bajar pero no subir
			// 5 - Espacio hueco donde los fantasmas pueden subir pero no bajar
			// 6- Adentro de la casa de los fantasmas

		this.fantasmaBlinky = new FantasmaBlinky((int)(this.ancho/2-this.ancho/104), (int)((11)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionTmp, 3);
		this.fantasmaPinky = new FantasmaPinky((int)(this.ancho/2-this.ancho/104), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionTmp, 2.5);
		this.fantasmaClyde = new FantasmaClyde((int)(this.ancho/2-this.ancho/104+this.ancho/26), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionTmp, 2.5);
		this.fantasmaInky = new FantasmaInky((int)(this.ancho/2-this.ancho/104-this.ancho/26), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionTmp, 2.5);

		this.tiempoDeInicio = System.currentTimeMillis();

		this.tiempoBlinky = 0;
		this.tiempoClyde = 0;
		this.tiempoInky = 0;
		this.tiempoPinky = 0;
		this.tiempoInicioBlinky = 0;
		this.tiempoInicioClyde = 0;
		this.tiempoInicioPinky = 0;
		this.tiempoInicioInky = 0;

		this.salioPinky = false;
		this.salioInky = false;
		this.salioBlinky = true;
		this.salioClyde = false;

		this.cronometro = 0;

		this.modoHuidaActivado = false;

		this.vidas = 3;
		this.jugar = false;

		this.hilo = new Thread(this);
		this.hilo.start();
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.pista, 0, 0, (int) this.ancho, (int) this.alto, this);
		g.setColor(Color.WHITE);
		pintarPuntitos(g);
		this.pacman.pintaPacman(g, this.abiertoCerrado, this.direccionTmp);
		//g.fillRect(this.pacman.xPac, this.pacman.yPac, (int)(this.ancho/52), (int)(this.ancho/52));
		this.fantasmaBlinky.pintaFantasma(g);
		this.fantasmaPinky.pintaFantasma(g);
		this.fantasmaClyde.pintaFantasma(g);
		this.fantasmaInky.pintaFantasma(g);		
	}

	public void pintarPuntitos(Graphics g){
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
	}

	public void run() {
		
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks, delta = 0.0;
		int fps = 0;

		long timer = System.currentTimeMillis();

		while (vidas >= 0) {
			this.cronometro = (timer - this.tiempoDeInicio)/1000;
			////System.out.println("crono: " + cronometro);
			////System.out.println(this.cronometro);
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
			
			////System.out.println("fps: " + fps);
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				fps = 0;
			}
		}
		//System.out.println("Game Over");
	}

	private void tick() {
		double velocidad = 3;
		movimientoY(this.coorX, this.coorY);
		movimientoX(this.coorX, this.coorY);
		comerPuntitos(this.coorX, this.coorY);
		escucharTeclas(velocidad);
		comportamientoPacman();
		
		////System.out.println("Tiempo blinky: " + this.tiempoBlinky);

		this.tiempoBlinky = this.cronometro - this.tiempoInicioBlinky;
		this.tiempoPinky = this.cronometro - this.tiempoInicioPinky;
		this.tiempoInky = this.cronometro - this.tiempoInicioInky;
		this.tiempoClyde = this.cronometro - this.tiempoInicioClyde;

		if (this.cronometro < 13){
			if(this.tiempoPinky < 3) this.salioPinky = this.fantasmaPinky.salirDeLaCasa(false);
			if(!this.salioPinky && this.tiempoPinky >= 3) salioPinky = this.fantasmaPinky.salirDeLaCasa(true);
	
			if(this.tiempoInky < 7) this.salioInky = this.fantasmaInky.salirDeLaCasa(false);
			if(!this.salioInky && this.tiempoInky >= 7) salioInky = this.fantasmaInky.salirDeLaCasa(true);
	
			if(this.tiempoClyde < 11) this.salioClyde = this.fantasmaClyde.salirDeLaCasa(false);
			if(!this.salioClyde && this.tiempoClyde >= 11) salioClyde = this.fantasmaClyde.salirDeLaCasa(true);
		}
		else {
			if(this.tiempoBlinky < 2) this.salioBlinky = this.fantasmaBlinky.salirDeLaCasa(false);
			if(!this.salioBlinky && this.tiempoBlinky >= 2) salioBlinky = this.fantasmaBlinky.salirDeLaCasa(true);
	
			if(this.tiempoPinky < 2) this.salioPinky = this.fantasmaPinky.salirDeLaCasa(false);
			if(!this.salioPinky && this.tiempoPinky >= 2) salioPinky = this.fantasmaPinky.salirDeLaCasa(true);
	
			if(this.tiempoInky < 2) this.salioInky = this.fantasmaInky.salirDeLaCasa(false);
			if(!this.salioInky && this.tiempoInky >= 2) salioInky = this.fantasmaInky.salirDeLaCasa(true);
	
			if(this.tiempoClyde < 2) this.salioClyde = this.fantasmaClyde.salirDeLaCasa(false);
			if(!this.salioClyde && this.tiempoClyde >= 2) salioClyde = this.fantasmaClyde.salirDeLaCasa(true);

		}

		if (!this.modoHuidaActivado){
			boolean blinky = false,
			        pinky = false,
			        inky = false,
			        clyde = false;
			if (cronometro < 7){
				if (salioBlinky) blinky = this.fantasmaBlinky.modoDispersion();
				if (salioPinky) pinky = this.fantasmaPinky.modoDispersion();
				if (salioInky) inky = this.fantasmaInky.modoDispersion();
				if (salioClyde) clyde = this.fantasmaClyde.modoDispersion();
			}
			else if (cronometro < 27){
				if (salioPinky) pinky = this.fantasmaPinky.modoPersecusion(this.coorX, this.coorY, this.direccionTmp);
				if (salioInky) inky = this.fantasmaInky.modoPersecusion(this.coorX, this.coorY, this.direccionTmp, this.fantasmaBlinky.getCoorXF(), this.fantasmaBlinky.getCoorYF());
				if (salioBlinky) blinky = this.fantasmaBlinky.modoPersecusion(this.coorX, this.coorY, this.direccionTmp);
				if (salioClyde) clyde = this.fantasmaClyde.modoPersecusion(this.coorX, this.coorY, this.direccionTmp);
			}
			else if (cronometro < 34){
				if (salioBlinky) blinky = this.fantasmaBlinky.modoDispersion();
				if (salioPinky) pinky = this.fantasmaPinky.modoDispersion();
				if (salioInky) inky = this.fantasmaInky.modoDispersion();
				if (salioClyde) clyde = this.fantasmaClyde.modoDispersion();
			}
			else if (cronometro < 54){
				if (salioPinky) pinky = this.fantasmaPinky.modoPersecusion(this.coorX, this.coorY, this.direccionTmp);
				if (salioInky) inky = this.fantasmaInky.modoPersecusion(this.coorX, this.coorY, this.direccionTmp, this.fantasmaBlinky.getCoorXF(), this.fantasmaBlinky.getCoorYF());
				if (salioBlinky) blinky = this.fantasmaBlinky.modoPersecusion(this.coorX, this.coorY, this.direccionTmp);
				if (salioClyde) clyde = this.fantasmaClyde.modoPersecusion(this.coorX, this.coorY, this.direccionTmp);
			}
			else if (cronometro < 61){
				if (salioBlinky) blinky = this.fantasmaBlinky.modoDispersion();
				if (salioPinky) pinky = this.fantasmaPinky.modoDispersion();
				if (salioInky) inky = this.fantasmaInky.modoDispersion();
				if (salioClyde) clyde = this.fantasmaClyde.modoDispersion();
			}
			else {
				if (salioPinky) pinky = this.fantasmaPinky.modoPersecusion(this.coorX, this.coorY, this.direccionTmp);
				if (salioInky) inky = this.fantasmaInky.modoPersecusion(this.coorX, this.coorY, this.direccionTmp, this.fantasmaBlinky.getCoorXF(), this.fantasmaBlinky.getCoorYF());
				if (salioBlinky) blinky = this.fantasmaBlinky.modoPersecusion(this.coorX, this.coorY, this.direccionTmp);
				if (salioClyde) clyde = this.fantasmaClyde.modoPersecusion(this.coorX, this.coorY, this.direccionTmp);
			}

			if (blinky || pinky || inky || clyde) {
				this.vidas -= 1;
				this.fantasmaBlinky = new FantasmaBlinky((int)(this.ancho/2-this.ancho/104), (int)((11)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionTmp, 3);
				this.fantasmaPinky = new FantasmaPinky((int)(this.ancho/2-this.ancho/104), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionTmp, 2.5);
				this.fantasmaClyde = new FantasmaClyde((int)(this.ancho/2-this.ancho/104+this.ancho/26), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionTmp, 2.5);
				this.fantasmaInky = new FantasmaInky((int)(this.ancho/2-this.ancho/104-this.ancho/26), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionTmp, 2.5);
				this.tiempoDeInicio = System.currentTimeMillis();

				this.tiempoBlinky = 0;
				this.tiempoClyde = 0;
				this.tiempoInky = 0;
				this.tiempoPinky = 0;
				this.tiempoInicioBlinky = 0;
				this.tiempoInicioClyde = 0;
				this.tiempoInicioPinky = 0;
				this.tiempoInicioInky = 0;

				this.cronometro = 0;

				this.salioPinky = false;
				this.salioInky = false;
				this.salioBlinky = true;
				this.salioClyde = false;

				this.jugar = false;

				this.pacman = new PacMan((int) (this.ancho / 2 - this.ancho / 104), (int)((17)*.985*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), (int) (1.5*(this.ancho / 52)));

				//System.out.println("Vidas restantes: " + this.vidas);

				try {
					Thread.sleep(2000);
				}
				catch (InterruptedException e){

				}
				

			}

		}
		else {
			boolean blinky = false,
			        pinky = false,
			        inky = false,
			        clyde = false;
			       
			if (salioBlinky) blinky = this.fantasmaBlinky.modoHuida(this.coorX, this.coorY, this.direccionTmp, this.tiempoModoHuida, this.contador);
			if (salioPinky) pinky = this.fantasmaPinky.modoHuida(this.coorX, this.coorY, this.direccionTmp, this.tiempoModoHuida, this.contador);
			if (salioInky) inky = this.fantasmaInky.modoHuida(this.coorX, this.coorY, this.direccionTmp, this.tiempoModoHuida, this.contador, this.fantasmaBlinky.getCoorXF(), this.fantasmaBlinky.getCoorYF());
			if (salioClyde) clyde = this.fantasmaClyde.modoHuida(this.coorX, this.coorY, this.direccionTmp, this.tiempoModoHuida, this.contador);

			if (blinky) {
				//anmacion va aqui
				this.fantasmaBlinky = new FantasmaBlinky((int)(this.ancho/2-this.ancho/104), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionTmp, 3);
				this.tiempoInicioBlinky = this.cronometro;
				this.tiempoBlinky = 0;
				this.salioBlinky = false;
			}
			
			if (pinky) {
				//anmacion va aqui
				this.fantasmaPinky = new FantasmaPinky((int)(this.ancho/2-this.ancho/104), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionTmp, 2.5);
				this.tiempoInicioPinky = this.cronometro;
				this.tiempoPinky = 0;
				this.salioPinky = false;
			} 
			
			if (clyde) {
				//anmacion va aqui
				this.fantasmaClyde = new FantasmaClyde((int)(this.ancho/2-this.ancho/104+this.ancho/26), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionTmp, 2.5);
				this.tiempoInicioClyde = this.cronometro;
				this.tiempoClyde = 0;
				this.salioClyde = false;
			} 
			
			if (inky) {
				//anmacion va aqui
				this.fantasmaInky = new FantasmaInky((int)(this.ancho/2-this.ancho/104-this.ancho/26), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionTmp, 2.5);
				this.tiempoInicioInky = this.cronometro;
				this.tiempoInky = 0;
				this.salioInky = false;
			}  
			if (this.cronometro - this.tiempoModoHuida == 15) this.modoHuidaActivado = false; //temporizador de 15 segundos
		}
		
			
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
				this.modoHuidaActivado = true;
				this.tiempoModoHuida = this.cronometro;
			}
		}
	}

	public void movimientoX(int coorX, int coorY){
		this.coorXTemp = (this.pacman.xPac + .33*.9928*(this.ancho/52) -.9928*this.ancho/104) / (.9928*(this.ancho/52)) + .02;
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
					this.pacman.setXPac((int)(((int)coorXTemp)*.9928*(this.ancho/52)-.3*.9928*(this.ancho/52)+.9928*this.ancho/104));
					this.direccionPacman = "arr";
					this.peticionSubirBajar = false;
                }
                else if (Math.abs((int)coorXTemp+1 - coorXTemp) < 0.15){
					this.pacman.setXPac((int)(((int)coorXTemp+1)*.9928*(this.ancho/52)-.3*.9928*(this.ancho/52)+.9928*this.ancho/104));
					this.direccionPacman = "arr";
					this.peticionSubirBajar = false;
                }
              
			 }
			 else if (!this.peticionSubirBajarDir && this.matrizPista[coorY + 1][coorX] != 1 && coorXTemp%1 < .2){

                if (Math.abs((int)coorXTemp - coorXTemp) < 0.15){
					this.pacman.setXPac((int)(((int)coorXTemp)*.9928*(this.ancho/52)-.3*.9928*(this.ancho/52)+.9928*this.ancho/104));
					this.direccionPacman = "aba";
					this.peticionSubirBajar = false;
                }
                else if (Math.abs((int)coorXTemp+1 - coorXTemp) < 0.15){
					this.pacman.setXPac((int)(((int)coorXTemp+1)*.9928*(this.ancho/52)-.3*.9928*(this.ancho/52)+.9928*this.ancho/104));
					this.direccionPacman = "aba";
					this.peticionSubirBajar = false;
                }
          
			 }	
		}

		this.coorX = coorX;
	}

	

	public void movimientoY (int coorX, int coorY){
		this.coorYTemp = (this.pacman.yPac + .39*.9928*(this.alto/31) -.985*this.alto/62) / (.985*(this.alto/31));
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
					this.pacman.setYPac((int)(((int)coorYTemp)*.9928*(this.alto/31)-.3*.9928*(this.alto/31)+.9928*this.alto/62));
					this.direccionPacman = "der";
					this.peticionIzqDer = false;
				}
				else if (Math.abs((int)coorYTemp+1 - coorYTemp) < .15){
					this.pacman.setYPac((int)(((int)coorYTemp+1)*.9928*(this.alto/31)-.3*.9928*(this.alto/31)+.9928*this.alto/62));
					this.direccionPacman = "der";
					this.peticionIzqDer = false;
				}
			
			 }
			 else if (!this.peticionIzqDerDir && this.matrizPista[coorY][this.coorX-1] != 1 && coorX > 1 && coorYTemp%1 < .2){
				if (Math.abs((int)coorYTemp - coorYTemp) < .15){
					this.pacman.setYPac((int)(((int)coorYTemp)*.9928*(this.alto/31)-.3*.9928*(this.alto/31)+.9928*this.alto/62));
					this.direccionPacman = "izq";
					this.peticionIzqDer = false;
				}
				else if (Math.abs((int)coorYTemp+1 - coorYTemp) < .15){
					this.pacman.setYPac((int)(((int)coorYTemp+1)*.9928*(this.alto/31)-.3*.9928*(this.alto/31)+.9928*this.alto/62));
					this.direccionPacman = "izq";
					this.peticionIzqDer = false;
				}
			 }
		}
		this.coorY = coorY;	
	}

	public void escucharTeclas(double velocidad){

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
			// if (this.matrizPista[this.coorY][this.coorX+1] == 1 && this.direccionTmp == "der") this.pared = true;
			// else if (this.matrizPista[this.coorY][this.coorX-1] == 1 && this.direccionTmp == "izq") this.pared = true;
			// else if (this.matrizPista[this.coorY+1][this.coorX] == 1 && this.direccionTmp == "aba") this.pared = true;
			// else if (this.matrizPista[this.coorY-1][this.coorX] == 1 && this.direccionTmp == "arr") this.pared = true;
			// else this.pared = false;

			if (this.direccionPacman == "der" && this.matrizPista[this.coorY][this.coorX + 1] != 1  && this.coorX < 51){
				this.pacman.xPac += velocidad;
			}	
			else if (this.direccionPacman == "izq" && this.matrizPista[this.coorY][this.coorX - 1] != 1 ){
				this.pacman.xPac -= velocidad;
			}
			else if (this.direccionPacman == "arr" && this.matrizPista[this.coorY - 1][this.coorX] != 1 ){
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

		if (this.contador % 12 == 0 && !this.pared) this.abiertoCerrado = false;

		else if (this.contador % 25 == 0) {
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
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
