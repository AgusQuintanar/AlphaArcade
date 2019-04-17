import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Pista extends JPanel implements Runnable, KeyListener {
	private PacMan pacman;
  private Thread hilo;
	private String direccionPacman,
									 direccionTmp = "arr";
	private boolean abiertoCerrado;
	private Image pista;
	private double ancho,
					alto;
	public int[][] matrizPista;	
	//ArrayList<ArrayList<String>> matrizPista;
	
	public Pista(int ancho, int alto) {
		super();
		this.ancho = ancho;
		this.alto = alto*.95;
		this.setPreferredSize(new Dimension((int)this.ancho,(int)this.alto));
		this.setBackground(Color.BLACK);
		this.pacman = new PacMan((int)(this.ancho/2-this.ancho/104), (int)(.95*this.alto/2-.95*this.alto/62), (int)(this.alto/31));
		this.direccionPacman = "";
		this.abiertoCerrado = true;
		this.direccionTmp = "arr";
		this.pista = new ImageIcon("Imagenes/PistaConPuntitos.png").getImage();
		this.setFocusable(true);
		this.addKeyListener(this);
		this.hilo = new Thread(this);
		this.hilo.start();
		this.matrizPista = new int[][] {
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,0,1,1,0,1,1,0,1,1,0,1,1,1,1,1,1,1,1,3,1,1,3,1,1,1,1,1,1,1,1,0,1,1,0,1,1,0,1,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,1,0,1,1,0,1,1,0,1,1,1,1,1,1,1,1,3,1,1,3,1,1,1,1,1,1,1,1,0,1,1,0,1,1,0,1,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,1,0,1,1,0,0,0,0,0,0,0,1,1,3,3,3,3,3,3,3,3,3,3,1,1,0,0,0,0,0,0,0,1,1,0,1,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,1,0,1,1,0,1,1,1,1,1,0,1,1,3,1,1,1,3,3,1,1,1,3,1,1,0,1,1,1,1,1,0,1,1,0,1,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,1,0,1,1,0,1,1,1,1,1,0,1,1,3,1,3,3,3,3,3,3,1,3,1,1,0,1,1,1,1,1,0,1,1,0,1,1,0,1,1,1,1,1,1},
			{2,2,2,2,2,2,0,0,0,0,0,0,0,1,1,0,0,0,0,3,3,3,1,3,3,3,3,3,3,1,3,3,3,0,0,0,0,1,1,0,0,0,0,0,0,0,2,2,2,2,2,2},
			{1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,1,3,1,3,3,3,3,3,3,1,3,1,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,1,3,1,1,1,1,1,1,1,1,3,1,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,3,3,3,3,3,3,3,3,3,3,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,3,1,1,1,1,1,1,1,1,3,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,3,1,1,1,1,1,1,1,1,3,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,3,0,0,0,1,1,0,0,0,3,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
			{1,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,1},
			{1,1,1,0,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,0,1,1,1},
			{1,1,1,0,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,0,1,1,1},
			{1,0,0,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
		};
		
	}
	
	// public void generarMatrizPista(){
	// 	this.matrizPista = new ArrayList<ArrayList<String>>();
	// 	try {
  //     BufferedReader br = new BufferedReader(new FileReader("Recursos/MatrizPista.csv"));
	// 		String fila;
  //     while((fila = br.readLine()) != null){
	// 			fila = fila.replace("???","");
	// 			System.out.println("Fila "+fila);
	// 			System.out.println(fila);
	// 			this.matrizPista.add(new ArrayList<String>(Arrays.asList(fila.split(","))));
  //     }
  //     br.close();
  //   }
  //   catch (FileNotFoundException ex){
  //     System.out.println("No se localizo el archivo "+ex);
  //   }
  //   catch (IOException ex){
  //     System.out.println("Ocurrio un error de I/O");
  //   }
  // System.out.println(this.matrizPista);
	// }

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.pista, (int)(this.ancho*.025/2), 0, (int)(this.ancho*.975), (int)(this.alto*.975), this);
		this.pacman.pintaPacman(g,this.abiertoCerrado, this.direccionTmp);
    }
    
	public void run(){
		int velocidad = 4;
		int contMiliSeg = 0;
		int coorX = 0,
				coorY = 0;
		while(true){
			coorX = (int) (((this.pacman.xPac+this.alto/62)*52)/this.ancho/.965-.5); 
			coorY = (int) (((this.pacman.yPac+this.alto/62)/.95*31)/this.alto);
			if(direccionPacman != "") //Si esta parado se guarda su posicion y direccion
				direccionTmp = direccionPacman;
			this.repaint();
			try {
				Thread.sleep(3);
				contMiliSeg += 3;
				if(contMiliSeg % 147 == 0) this.abiertoCerrado = false;

				if(coorX == 1 && coorY != 1 && coorY != 30){
					if(this.direccionPacman == "der") this.pacman.xPac += velocidad;
					else if(this.direccionPacman == "arr") this.pacman.yPac -= velocidad;
					else if(this.direccionPacman == "aba") this.pacman.yPac += velocidad;
				}
				else if(coorX == 51 && coorY != 1 && coorY != 30){
					if(this.direccionPacman == "izq") this.pacman.xPac -= velocidad;
					else if(this.direccionPacman == "arr") this.pacman.yPac -= velocidad;
					else if(this.direccionPacman == "aba") this.pacman.yPac += velocidad;
				}

				else if(coorX == 1 && coorY == 1){
					if(this.direccionPacman == "der") this.pacman.xPac += velocidad;
					else if(this.direccionPacman == "aba") this.pacman.yPac += velocidad;
				}

				else if(coorX == 1 && coorY == 30){
					if(this.direccionPacman == "der") this.pacman.xPac += velocidad;
					else if(this.direccionPacman == "arr") this.pacman.yPac -= velocidad;
				}

				else if(coorY == 1 && coorX != 1 && coorX != 51){
					if(this.direccionPacman == "der") this.pacman.xPac += velocidad;
					else if(this.direccionPacman == "izq") this.pacman.xPac -= velocidad;
					else if(this.direccionPacman == "aba") this.pacman.yPac += velocidad;
				}

				else if(coorY == 30 && coorX != 1 && coorX != 51){
					if(this.direccionPacman == "der") this.pacman.xPac += velocidad;
					else if(this.direccionPacman == "izq") this.pacman.xPac -= velocidad;
					else if(this.direccionPacman == "arr") this.pacman.yPac -= velocidad;
				}

				else if(coorX == 51 && coorY == 30){
					if(this.direccionPacman == "izq") this.pacman.xPac -= velocidad;
					else if(this.direccionPacman == "arr") this.pacman.yPac -= velocidad;
				}

				else if(coorX == 51 && coorY == 1){
					if(this.direccionPacman == "izq") this.pacman.xPac -= velocidad;
					else if(this.direccionPacman == "aba") this.pacman.yPac += velocidad;
				}

				else {
					if(this.direccionPacman == "der") this.pacman.xPac += velocidad;
					else if(this.direccionPacman == "izq") this.pacman.xPac -= velocidad;
					else if(this.direccionPacman == "arr") this.pacman.yPac -= velocidad;
					else if(this.direccionPacman == "aba") this.pacman.yPac += velocidad;
				}
				
				System.out.println("Coordenadas: "+ coorX + ", " + coorY);
				Thread.sleep(3);
				contMiliSeg += 3;
				if(contMiliSeg % 300 == 0){
					this.abiertoCerrado = true;
					contMiliSeg = 0; //Reinicia el contador
				}
				
				this.repaint();

		}catch(InterruptedException ex) {
			System.out.println("No pude despertar!");
		}
	}
}


	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) this.direccionPacman = "der";
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) this.direccionPacman = "izq";
		else if(e.getKeyCode() == KeyEvent.VK_UP) this.direccionPacman = "arr";
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) this.direccionPacman = "aba";
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) this.direccionPacman = "";
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) this.direccionPacman = "";
		else if(e.getKeyCode() == KeyEvent.VK_UP) this.direccionPacman = "";
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) this.direccionPacman = "";
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}