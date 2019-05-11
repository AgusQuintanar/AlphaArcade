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


public class JuegoPacMan extends JPanel implements KeyListener{
	private Pista pista;
	private PacMan pacman;
	private FantasmaBlinky fantasmaBlinky;
	private FantasmaPinky fantasmaPinky;
	private FantasmaClyde fantasmaClyde;
	private FantasmaInky fantasmaInky;
	private TableroPacMan tableroPacMan;
	private Thread hiloTick,
				   hiloRender;

	private boolean pellet,
					jugar,
					pausa;
	
	private double ancho, 
					alto;
	
	private int contador,
				vidas,
				coorXPacMan,
				coorYPacMan,
				puntaje,
				puntosRestantes;
	private long tiempoDeInicio,
				 cronometro;
				
	private String direccionPresionada,
				   direccionPacMan;
	private int[][] matrizPista;


	public JuegoPacMan(double ancho, TableroPacMan tableroPacMan) {
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

		this.tableroPacMan = tableroPacMan;
		this.tableroPacMan.setBackground(Color.BLACK);

		//System.out.println(this.ancho + ", " + this.alto);
		
		this.setFocusable(true);
		this.addKeyListener(this);
		this.contador = 0;
		this.puntaje = 0;
		this.puntosRestantes = 1;

		this.direccionPresionada = "";
		
		this.fantasmaBlinky = new FantasmaBlinky((int)(this.ancho/2-this.ancho/104), (int)((11)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 3);
		this.fantasmaPinky = new FantasmaPinky((int)(this.ancho/2-this.ancho/104), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 3);
		this.fantasmaClyde = new FantasmaClyde((int)(this.ancho/2-this.ancho/104+this.ancho/26), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 3);
		this.fantasmaInky = new FantasmaInky((int)(this.ancho/2-this.ancho/104-this.ancho/26), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 3);

		this.cronometro = 0;

		this.pellet = false;

		this.vidas = 3;
		this.jugar = false;
		this.pausa = false;

		this.hiloTick = new Thread(new Runnable(){
			@Override
			public void run() {
				long lastTime = System.nanoTime();
				final double amountOfTicks = 60.0;
				double ns = 1000000000 / amountOfTicks, 
					delta = 0.0;
				int fps = 0;
				long timer = System.currentTimeMillis();
	
				while (vidas >= 0 && puntosRestantes > 0) {
					cronometro = (timer - tiempoDeInicio)/1000;
					long now = System.nanoTime();
					delta += (now - lastTime) / ns;
					lastTime = now;
					while (delta >= 1) {
						if(!pausa) tick();
						fps++;
						delta--;	
					}
					if (System.currentTimeMillis() - timer > 1000) {
						timer += 1000;
						fps = 0;
					}
				}
			}
		});

		this.hiloRender = new Thread(new Runnable(){
			@Override
			public void run() {
				long lastTime = System.nanoTime();
				final double amountOfTicks = 60.0;
				double ns = 1000000000 / amountOfTicks, 
					delta = 0.0;
				int fps = 0;
				long timer = System.currentTimeMillis();

				while (vidas >= 0 && puntosRestantes > 0) {
					synchronized (hiloTick) {
						cronometro = (timer - tiempoDeInicio)/1000;

						long now = System.nanoTime();
						delta += (now - lastTime) / ns;
						lastTime = now;
						while (delta >= 1) {
							if(!pausa) render();
							fps++;
							delta--;	
						}
						if (System.currentTimeMillis() - timer > 1000) {
							timer += 1000;
							fps = 0;
						}
					}		
				}
				if (puntosRestantes <= 0){
					System.out.println("Haz ganado");
				}
				if (vidas == 0){
					System.out.println("Haz perdido");
				}
				
			}	
		});
	

	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.pista.pintaPista(g);
		this.pista.pintarPuntitos(g, this.contador, this.matrizPista);
		this.pacman.pintaPacman(g, this.contador);
		this.fantasmaBlinky.pintaFantasma(g, this.contador);
		this.fantasmaPinky.pintaFantasma(g, this.contador);
		this.fantasmaClyde.pintaFantasma(g, this.contador);
		this.fantasmaInky.pintaFantasma(g, this.contador);		
	}

	public void contarPuntosRestantes(){
		this.puntosRestantes = 1;
		for (int i=0; i<this.matrizPista.length - 1; i++){
			for (int j=0; j<this.matrizPista[i].length - 1; j++){
				if (this.matrizPista[i][j] == 0){
					this.puntosRestantes += 1;
				}
			}
		}
		this.puntosRestantes -= 1;
	}

	public void reinicioDePosiciones(boolean muerte) {
		if (muerte) this.vidas -= 1;
		System.out.println("Alcanzado por fantasma");
				this.fantasmaBlinky = new FantasmaBlinky((int)(this.ancho/2-this.ancho/104), (int)((11)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 3);
				this.fantasmaPinky = new FantasmaPinky((int)(this.ancho/2-this.ancho/104), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 2.5);
				this.fantasmaClyde = new FantasmaClyde((int)(this.ancho/2-this.ancho/104+this.ancho/26), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 2.5);
				this.fantasmaInky = new FantasmaInky((int)(this.ancho/2-this.ancho/104-this.ancho/26), (int)((14)*.9928*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 2.5);
				this.tiempoDeInicio = System.currentTimeMillis();

				this.jugar = false;

				this.direccionPacMan = "der"; //ver si poner un setter mejor
				this.pacman = new PacMan((int) (this.ancho/2-this.ancho/104), (int)((17)*.985*(this.alto/31)-.3*.985*(this.alto/31)+.985*this.alto/62), this.ancho, this.alto, this.matrizPista);
	}

	private void tick() {
		this.contador++;
		this.pacman.moverPacMan(this.direccionPresionada);
		this.coorXPacMan = this.pacman.getCoorX();
		this.coorYPacMan = this.pacman.getCoorY();
		this.direccionPacMan = this.pacman.getDireccionPacMan();
		contarPuntosRestantes();

		this.tableroPacMan.setPuntaje(this.puntaje);
		this.tableroPacMan.setVidasRestantes(this.vidas);

		String puntoComido = this.pacman.comerPuntitos(this.matrizPista);
		if (puntoComido == "pellet"){
			this.pellet = true;
			this.puntaje += 50;
		}
		else if (puntoComido == "punto"){
			this.puntaje += 10;
		}
		
		boolean blinky = false,
				pinky = false,
				inky = false,
				clyde = false;

		blinky = this.fantasmaBlinky.comportamientoFantasma(this.cronometro, this.coorXPacMan, this.coorYPacMan, this.direccionPacMan, this.pellet, this.pacman.getCoorXTemp(), this.pacman.getCoorYTemp());
		pinky = this.fantasmaPinky.comportamientoFantasma(this.cronometro, this.coorXPacMan, this.coorYPacMan, this.direccionPacMan, this.pellet, this.pacman.getCoorXTemp(), this.pacman.getCoorYTemp());
		inky = this.fantasmaInky.comportamientoFantasma(this.cronometro, this.coorXPacMan, this.coorYPacMan, this.direccionPacMan, this.pellet, this.pacman.getCoorXTemp(), this.pacman.getCoorYTemp(), this.fantasmaBlinky.getCoorXF(), this.fantasmaBlinky.getCoorYF());
		clyde = this.fantasmaClyde.comportamientoFantasma(this.cronometro, this.coorXPacMan, this.coorYPacMan, this.direccionPacMan, this.pellet, this.pacman.getCoorXTemp(), this.pacman.getCoorYTemp());
		this.pellet = false;

		if (blinky || pinky || inky || clyde) reinicioDePosiciones(true);
			
	}

	private void render() {
		this.repaint();
		this.tableroPacMan.repaint();
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

		else if (e.getKeyCode() == KeyEvent.VK_ENTER && !this.jugar){
			this.jugar = true;
			System.out.println("Jugar activado");
			this.tiempoDeInicio = System.currentTimeMillis();
			this.hiloTick.start();
			this.hiloRender.start();
		}
		else if (e.getKeyCode() == KeyEvent.VK_P && !this.pausa){
			System.out.println("Pausa activada");
			this.pausa = true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_P && this.pausa){
			System.out.println("Pausa desactivada");
			this.pausa = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}