
// Agustin Quintanar y Julio Arath Rosales
// A01636142 y A01630738

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class JuegoPacMan extends JPanel implements KeyListener {
	private Pista pista;
	private PacMan pacman;
	private FantasmaBlinky fantasmaBlinky;
	private FantasmaPinky fantasmaPinky;
	private FantasmaClyde fantasmaClyde;
	private FantasmaInky fantasmaInky;
	private TableroPacMan tableroPacMan;
	private Thread hiloTick, hiloRender;

	private boolean pellet,
					jugar, 
					pausa,
					pacManMuerto;

	private double ancho,
				   alto,
				   velocidadGlobal;

	private int contador,
				contadorMuertePacManInicial, 
				vidas, 
				coorXPacMan, 
				coorYPacMan, 
				puntaje, 
				puntosRestantes, 
				fantasmasComidos,
				nivel;
				

	private long tiempoDeInicio, 
				 cronometro;

	private String direccionPresionada, 
				   direccionPacMan;
	private int[][] matrizPista;

	public JuegoPacMan(double ancho, TableroPacMan tableroPacMan) {
		super();
		int anchoOriginal = 1890, altoOriginal = 1131;
		double proporcionAncho = ancho / anchoOriginal; // Considerando un ancho del 100%
		// System.out.println(ancho + " " + alto);
		this.ancho = (int) (anchoOriginal * proporcionAncho);
		this.alto = (int) (altoOriginal * proporcionAncho);
		// System.out.println(proporcionAncho);
		// System.out.println(("ancho: " + this.ancho) + " alto: " + this.alto);
		this.setPreferredSize(new Dimension((int) this.ancho, (int) this.alto));
		this.setBackground(Color.BLACK);

		this.pista = new Pista(this.ancho, this.alto);
		this.matrizPista = pista.getPista();

		this.pacman = new PacMan((int) (this.ancho / 2 - this.ancho / 104),
				(int) ((23) * .993 * (this.alto / 31) - .3 * .993 * (this.alto / 31) + .993 * this.alto / 62),
				this.ancho, this.alto, this.matrizPista);

		this.coorXPacMan = this.pacman.getCoorX();
		this.coorYPacMan = this.pacman.getCoorY();
		this.direccionPacMan = this.pacman.getDireccionPacMan();

		this.tableroPacMan = tableroPacMan;
		this.tableroPacMan.setBackground(Color.BLACK);

		this.velocidadGlobal = 4;

		this.contadorMuertePacManInicial = 0;
		this.pacManMuerto = false;

		// System.out.println(this.ancho + ", " + this.alto);

		this.setFocusable(true);
		this.addKeyListener(this);
		this.contador = 0;
		this.puntaje = 0;
		this.puntosRestantes = 1;

		this.direccionPresionada = "izq";

		this.fantasmaBlinky = new FantasmaBlinky((int) (this.ancho / 2 - this.ancho / 104),
				(int) ((11) * .9928 * (this.alto / 31) - .3 * .985 * (this.alto / 31) + .985 * this.alto / 62),
				this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 3);
		this.fantasmaPinky = new FantasmaPinky((int) (this.ancho / 2 - this.ancho / 104),
				(int) ((14) * .9928 * (this.alto / 31) - .3 * .985 * (this.alto / 31) + .985 * this.alto / 62),
				this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 3);
		this.fantasmaClyde = new FantasmaClyde((int) (this.ancho / 2 - this.ancho / 104 + this.ancho / 26),
				(int) ((14) * .9928 * (this.alto / 31) - .3 * .985 * (this.alto / 31) + .985 * this.alto / 62),
				this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 3);
		this.fantasmaInky = new FantasmaInky((int) (this.ancho / 2 - this.ancho / 104 - this.ancho / 26),
				(int) ((14) * .9928 * (this.alto / 31) - .3 * .985 * (this.alto / 31) + .985 * this.alto / 62),
				this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 3);

		this.cronometro = 0;

		this.pellet = false;
		this.fantasmasComidos = 0;

		this.vidas = 3;
		this.nivel = 1;
		this.jugar = false;
		this.pausa = false;

		this.hiloTick = new Thread(new Runnable() {
			@Override
			public void run() {
				long lastTime = System.nanoTime();
				final double amountOfTicks = 60.0;
				double ns = 1000000000 / amountOfTicks, delta = 0.0;
				int fps = 0;
				long timer = System.currentTimeMillis();

				while (vidas > 0) {
					cronometro = (timer - tiempoDeInicio) / 1000;
					long now = System.nanoTime();
					delta += (now - lastTime) / ns;
					lastTime = now;
					while (delta >= 1) {
						if (!pausa && cronometro > 1)
							tick();
						fps++;
						delta--;
					}
					if (System.currentTimeMillis() - timer > 1000) {
						timer += 1000;
						fps = 0;
					}

					if (puntosRestantes == 0){
						nivel += 1;
						System.out.println("Haz pasado al nivel: " + nivel);
						pista = new Pista(ancho, alto);
						matrizPista = pista.getPista();
						contarPuntosRestantes();
						reinicioDePosiciones(false);
					}
				}
				System.out.println("Game Over");
				
			}
		});

		this.hiloRender = new Thread(new Runnable() {
			@Override
			public void run() {
				long lastTime = System.nanoTime();
				final double amountOfTicks = 60.0;
				double ns = 1000000000 / amountOfTicks, delta = 0.0;
				int fps = 0;
				long timer = System.currentTimeMillis();

				while (vidas >= 0) {
					synchronized (hiloTick) {
						cronometro = (timer - tiempoDeInicio) / 1000;

						long now = System.nanoTime();
						delta += (now - lastTime) / ns;
						lastTime = now;
						while (delta >= 1) {
							if (!pausa && cronometro > 1)
								render();
							fps++;
							delta--;
						}
						if (System.currentTimeMillis() - timer > 1000) {
							timer += 1000;
							fps = 0;
						}
					}
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

	public void contarPuntosRestantes() {
		this.puntosRestantes = 1;
		for (int i = 0; i < this.matrizPista.length - 1; i++) {
			for (int j = 0; j < this.matrizPista[i].length - 1; j++) {
				if (this.matrizPista[i][j] == 0) {
					this.puntosRestantes += 1;
				}
			}
		}
		this.puntosRestantes -= 1;
	}
	

	public void reinicioDePosiciones(boolean muerte) {
		if (muerte) this.vidas -= 1;

		this.fantasmaBlinky = new FantasmaBlinky((int) (this.ancho / 2 - this.ancho / 104),
				(int) ((11) * .9928 * (this.alto / 31) - .3 * .985 * (this.alto / 31) + .985 * this.alto / 62),
				this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 3);
		this.fantasmaPinky = new FantasmaPinky((int) (this.ancho / 2 - this.ancho / 104),
				(int) ((14) * .9928 * (this.alto / 31) - .3 * .985 * (this.alto / 31) + .985 * this.alto / 62),
				this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 2.5);
		this.fantasmaClyde = new FantasmaClyde((int) (this.ancho / 2 - this.ancho / 104 + this.ancho / 26),
				(int) ((14) * .9928 * (this.alto / 31) - .3 * .985 * (this.alto / 31) + .985 * this.alto / 62),
				this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 2.5);
		this.fantasmaInky = new FantasmaInky((int) (this.ancho / 2 - this.ancho / 104 - this.ancho / 26),
				(int) ((14) * .9928 * (this.alto / 31) - .3 * .985 * (this.alto / 31) + .985 * this.alto / 62),
				this.ancho, this.alto, this.matrizPista, this.direccionPacMan, 2.5);
		this.tiempoDeInicio = System.currentTimeMillis();

		this.direccionPresionada = "izq";

		this.pacman = new PacMan((int) (this.ancho / 2 - this.ancho / 104),
				(int) ((23) * .993 * (this.alto / 31) - .3 * .993 * (this.alto / 31) + .993 * this.alto / 62),
				this.ancho, this.alto, this.matrizPista);

		render();
	}

	private void tick() {
		this.contador++;
		if(this.pacManMuerto){
			animarMuertePacMan();
		}
		else {
			correrJuego();
		}

		

	}

	public void animarMuertePacMan() {
		System.out.println(this.contador - this.contadorMuertePacManInicial);
		if (this.contador - this.contadorMuertePacManInicial == 300){
			this.pacManMuerto = false;
			reinicioDePosiciones(true);
		} 
	}

	public void correrJuego() {

		this.pacman.moverPacMan(this.direccionPresionada, this.velocidadGlobal, (this.fantasmaBlinky.getModoHuidaActivado() || this.fantasmaPinky.getModoHuidaActivado() || this.fantasmaClyde.getModoHuidaActivado() || this.fantasmaInky.getModoHuidaActivado()));
		this.coorXPacMan = this.pacman.getCoorX();
		this.coorYPacMan = this.pacman.getCoorY();
		this.direccionPacMan = this.pacman.getDireccionPacMan();
		contarPuntosRestantes();

		this.tableroPacMan.setPuntaje(this.puntaje);
		this.tableroPacMan.setVidasRestantes(this.vidas);

		String puntoComido = this.pacman.comerPuntitos(this.matrizPista);
		if (puntoComido == "pellet") {
			this.pellet = true;
			this.puntaje += 50;
			this.fantasmasComidos = 0;
		} else if (puntoComido == "punto") {
			this.puntaje += 10;
		}

		String blinky = "", pinky = "", inky = "", clyde = "";

		blinky = this.fantasmaBlinky.comportamientoFantasma(this.cronometro, this.coorXPacMan, this.coorYPacMan,
				this.direccionPacMan, this.pellet, this.pacman.getCoorXTemp(), this.pacman.getCoorYTemp(), this.velocidadGlobal);
		pinky = this.fantasmaPinky.comportamientoFantasma(this.cronometro, this.coorXPacMan, this.coorYPacMan,
				this.direccionPacMan, this.pellet, this.pacman.getCoorXTemp(), this.pacman.getCoorYTemp(), this.velocidadGlobal);
		inky = this.fantasmaInky.comportamientoFantasma(this.cronometro, this.coorXPacMan, this.coorYPacMan,
				this.direccionPacMan, this.pellet, this.pacman.getCoorXTemp(), this.pacman.getCoorYTemp(), this.velocidadGlobal,
				this.fantasmaBlinky.getCoorXF(), this.fantasmaBlinky.getCoorYF());
		clyde = this.fantasmaClyde.comportamientoFantasma(this.cronometro, this.coorXPacMan, this.coorYPacMan,
				this.direccionPacMan, this.pellet, this.pacman.getCoorXTemp(), this.pacman.getCoorYTemp(), this.velocidadGlobal);
		this.pellet = false;

		if (blinky == "tocado" || pinky == "tocado" || inky == "tocado" || clyde == "tocado"){
			this.pacManMuerto = true;
			this.contadorMuertePacManInicial = this.contador;
		}

		if (blinky == "comido") {
			generarPuntajePorFantasmaComido(this.fantasmaBlinky);
		}
		if (clyde == "comido") {
			generarPuntajePorFantasmaComido(this.fantasmaClyde);
		}
		if (pinky == "comido") {
			generarPuntajePorFantasmaComido(this.fantasmaPinky);
		}
		if (inky == "comido") {
			generarPuntajePorFantasmaComido(this.fantasmaInky);	
		}
	}

	public void generarPuntajePorFantasmaComido(Fantasma fantasma) {

		this.fantasmasComidos += 1;
		this.puntaje += Math.pow(2, this.fantasmasComidos)*100;
		fantasma.setPuntajePorFantasmaComido((int) Math.pow(2, this.fantasmasComidos) * 100);
		fantasma.setMostrarPuntaje(true);
		fantasma.setTiempoComidoInicial(this.cronometro);
		fantasma.setXFComido(fantasma.getXF());
		fantasma.setYFComido(fantasma.getYF());
	}

	private void render() {
		Toolkit.getDefaultToolkit().sync(); //Para disminuir el lag
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
		else if (e.getKeyCode() == KeyEvent.VK_P){
			
			this.pausa = !this.pausa;
		}
	
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
