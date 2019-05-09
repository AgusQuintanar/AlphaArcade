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

public class JuegoPacMan extends JPanel implements Runnable, KeyListener {
	private Pista pista;
	private PacMan pacman;
	private FantasmaBlinky fantasmaBlinky;
	private FantasmaPinky fantasmaPinky;
	private FantasmaClyde fantasmaClyde;
	private FantasmaInky fantasmaInky;
	private Thread hilo;

	private boolean salioPinky,
					salioBlinky,
					salioClyde,
					salioInky,
					modoHuidaActivado,
					jugar;
	
	private double ancho, 
					alto;
	
	private int contador,
				vidas,
				coorXPacMan,
				coorYPacMan;
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
	private String direccionPresionada,
				   direccionPacMan;
	private int[][] matrizPista;


	public JuegoPacMan(double ancho) {
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

		this.pista = new Pista(this.ancho, this.alto);
		this.matrizPista = pista.getPista();


		this.pacman = new PacMan((int) (this.ancho/2-this.ancho/104), (int)((17)*.985*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista);

		this.coorXPacMan = this.pacman.getCoorX();
		this.coorYPacMan = this.pacman.getCoorY();
		this.direccionPacMan = this.pacman.getDireccionPacMan();

		//System.out.println(this.ancho + ", " + this.alto);
		
		this.setFocusable(true);
		this.addKeyListener(this);
		this.contador = 0;

		this.direccionPresionada = "";
		

		this.fantasmaBlinky = new FantasmaBlinky((int)(this.ancho/2-this.ancho/104), (int)((11)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 3);
		this.fantasmaPinky = new FantasmaPinky((int)(this.ancho/2-this.ancho/104), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 3);
		this.fantasmaClyde = new FantasmaClyde((int)(this.ancho/2-this.ancho/104+this.ancho/26), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 3);
		this.fantasmaInky = new FantasmaInky((int)(this.ancho/2-this.ancho/104-this.ancho/26), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 3);

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
		this.pista.pintaPista(g);
		this.pista.pintarPuntitos(g, this.contador, this.matrizPista);
		this.pacman.pintaPacman(g, this.contador);
		this.fantasmaBlinky.pintaFantasma(g);
		this.fantasmaPinky.pintaFantasma(g);
		this.fantasmaClyde.pintaFantasma(g);
		this.fantasmaInky.pintaFantasma(g);		
	}

	public void run() {
		
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks, 
			   delta = 0.0;
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
		this.contador++;
		this.pacman.moverPacMan(this.direccionPresionada);
		this.coorXPacMan = this.pacman.getCoorX();
		this.coorYPacMan = this.pacman.getCoorY();
		this.direccionPacMan = this.pacman.getDireccionPacMan();

		String puntoComido = this.pacman.comerPuntitos();

		if (puntoComido == "pellet"){
			this.modoHuidaActivado = true;
			this.tiempoModoHuida = this.cronometro;	
		}

		this.tiempoBlinky = this.cronometro - this.tiempoInicioBlinky;
		this.tiempoPinky = this.cronometro - this.tiempoInicioPinky;
		this.tiempoInky = this.cronometro - this.tiempoInicioInky;
		this.tiempoClyde = this.cronometro - this.tiempoInicioClyde;

		if (this.cronometro < 13){
			if(this.tiempoPinky < 3) this.salioPinky = this.fantasmaPinky.salirDeLaCasa(false);
			if(!this.salioPinky && this.tiempoPinky >= 3) salioPinky = this.fantasmaPinky.salirDeLaCasa(true);
	
			if(this.tiempoInky < 7) this.salioInky = this.fantasmaInky.salirDeLaCasa(false);
			if(!this.salioInky && this.tiempoInky >= 7) salioInky = this.fantasmaInky.salirDeLaCasa(true);
	
			if(this.tiempoClyde < 4) this.salioClyde = this.fantasmaClyde.salirDeLaCasa(false);
			if(!this.salioClyde && this.tiempoClyde >= 4) salioClyde = this.fantasmaClyde.salirDeLaCasa(true);
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

		boolean blinky = false,
				pinky = false,
				inky = false,
				clyde = false;

		if (!this.modoHuidaActivado){
		
			if (cronometro < 7){
				if (salioBlinky) blinky = this.fantasmaBlinky.modoDispersion();
				if (salioPinky) pinky = this.fantasmaPinky.modoDispersion();
				if (salioInky) inky = this.fantasmaInky.modoDispersion();
				if (salioClyde) clyde = this.fantasmaClyde.modoDispersion();
			}
			else if (cronometro < 27){
				if (salioPinky) pinky = this.fantasmaPinky.modoPersecusion(this.coorXPacMan, this.coorYPacMan, this.direccionPacMan);
				if (salioInky) inky = this.fantasmaInky.modoPersecusion(this.coorXPacMan, this.coorYPacMan, this.direccionPacMan, this.fantasmaBlinky.getCoorXF(), this.fantasmaBlinky.getCoorYF());
				if (salioBlinky) blinky = this.fantasmaBlinky.modoPersecusion(this.coorXPacMan, this.coorYPacMan, this.direccionPacMan);
				if (salioClyde) clyde = this.fantasmaClyde.modoPersecusion(this.coorXPacMan, this.coorYPacMan, this.direccionPacMan);
			}
			else if (cronometro < 34){
				if (salioBlinky) blinky = this.fantasmaBlinky.modoDispersion();
				if (salioPinky) pinky = this.fantasmaPinky.modoDispersion();
				if (salioInky) inky = this.fantasmaInky.modoDispersion();
				if (salioClyde) clyde = this.fantasmaClyde.modoDispersion();
			}
			else if (cronometro < 54){
				if (salioPinky) pinky = this.fantasmaPinky.modoPersecusion(this.coorXPacMan, this.coorYPacMan, this.direccionPacMan);
				if (salioInky) inky = this.fantasmaInky.modoPersecusion(this.coorXPacMan, this.coorYPacMan, this.direccionPacMan, this.fantasmaBlinky.getCoorXF(), this.fantasmaBlinky.getCoorYF());
				if (salioBlinky) blinky = this.fantasmaBlinky.modoPersecusion(this.coorXPacMan, this.coorYPacMan, this.direccionPacMan);
				if (salioClyde) clyde = this.fantasmaClyde.modoPersecusion(this.coorXPacMan, this.coorYPacMan, this.direccionPacMan);
			}
			else if (cronometro < 61){
				if (salioBlinky) blinky = this.fantasmaBlinky.modoDispersion();
				if (salioPinky) pinky = this.fantasmaPinky.modoDispersion();
				if (salioInky) inky = this.fantasmaInky.modoDispersion();
				if (salioClyde) clyde = this.fantasmaClyde.modoDispersion();
			}
			else {
				if (salioPinky) pinky = this.fantasmaPinky.modoPersecusion(this.coorXPacMan, this.coorYPacMan, this.direccionPacMan);
				if (salioInky) inky = this.fantasmaInky.modoPersecusion(this.coorXPacMan, this.coorYPacMan, this.direccionPacMan, this.fantasmaBlinky.getCoorXF(), this.fantasmaBlinky.getCoorYF());
				if (salioBlinky) blinky = this.fantasmaBlinky.modoPersecusion(this.coorXPacMan, this.coorYPacMan, this.direccionPacMan);
				if (salioClyde) clyde = this.fantasmaClyde.modoPersecusion(this.coorXPacMan, this.coorYPacMan, this.direccionPacMan);
			}

			if (blinky || pinky || inky || clyde) {
				System.out.println("Alcanzado por fantasma");
				this.vidas -= 1;
				this.fantasmaBlinky = new FantasmaBlinky((int)(this.ancho/2-this.ancho/104), (int)((11)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 3);
				this.fantasmaPinky = new FantasmaPinky((int)(this.ancho/2-this.ancho/104), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 2.5);
				this.fantasmaClyde = new FantasmaClyde((int)(this.ancho/2-this.ancho/104+this.ancho/26), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 2.5);
				this.fantasmaInky = new FantasmaInky((int)(this.ancho/2-this.ancho/104-this.ancho/26), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 2.5);
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

				this.pacman = new PacMan((int) (this.ancho/2-this.ancho/104), (int)((17)*.985*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista);

				//System.out.println("Vidas restantes: " + this.vidas);

				try {
					Thread.sleep(2000);
				}
				catch (InterruptedException e){

				}
				

			}

		}
		else {
			       
			if (salioBlinky) blinky = this.fantasmaBlinky.modoHuida(this.coorXPacMan, this.coorYPacMan, this.direccionPacMan, this.tiempoModoHuida, this.contador);
			if (salioPinky) pinky = this.fantasmaPinky.modoHuida(this.coorXPacMan, this.coorYPacMan, this.direccionPacMan, this.tiempoModoHuida, this.contador);
			if (salioInky) inky = this.fantasmaInky.modoHuida(this.coorXPacMan, this.coorYPacMan, this.direccionPacMan, this.tiempoModoHuida, this.contador, this.fantasmaBlinky.getCoorXF(), this.fantasmaBlinky.getCoorYF());
			if (salioClyde) clyde = this.fantasmaClyde.modoHuida(this.coorXPacMan, this.coorYPacMan, this.direccionPacMan, this.tiempoModoHuida, this.contador);

			if (blinky) {
				//anmacion va aqui
				this.fantasmaBlinky = new FantasmaBlinky((int)(this.ancho/2-this.ancho/104), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 3);
				this.tiempoInicioBlinky = this.cronometro;
				this.tiempoBlinky = 0;
				this.salioBlinky = false;
			}
			
			if (pinky) {
				//anmacion va aqui
				this.fantasmaPinky = new FantasmaPinky((int)(this.ancho/2-this.ancho/104), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 3);
				this.tiempoInicioPinky = this.cronometro;
				this.tiempoPinky = 0;
				this.salioPinky = false;
			} 
			
			if (clyde) {
				//anmacion va aqui
				this.fantasmaClyde = new FantasmaClyde((int)(this.ancho/2-this.ancho/104+this.ancho/26), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 3);
				this.tiempoInicioClyde = this.cronometro;
				this.tiempoClyde = 0;
				this.salioClyde = false;
			} 
			
			if (inky) {
				//anmacion va aqui
				this.fantasmaInky = new FantasmaInky((int)(this.ancho/2-this.ancho/104-this.ancho/26), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 3);
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

	

	
	@Override
	public void keyPressed(KeyEvent e) {
	
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			this.direccionPresionada = "der";
		else if (e.getKeyCode() == KeyEvent.VK_LEFT)
			this.direccionPresionada = "izq";
		else if (e.getKeyCode() == KeyEvent.VK_UP)
			this.direccionPresionada = "arr";
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
			this.direccionPresionada = "aba";
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
