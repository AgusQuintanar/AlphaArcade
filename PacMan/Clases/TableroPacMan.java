import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class TableroPacMan extends JPanel {

    private int puntaje,
                vidasRestantes;
    private Font fuenteTablero;
    private Image iconoPacMan;
    public TableroPacMan(double ancho, double alto){
        super();
        System.out.println(alto);
        this.setVisible(true);
        this.setPreferredSize(new Dimension((int) ancho, (int) alto));
        this.setBackground(Color.BLACK);
        this.puntaje = 0;
        this.vidasRestantes = 3;
        this.iconoPacMan = new ImageIcon("PacMan2-izq.png").getImage();
        try {
            this.fuenteTablero = Font.createFont(Font.TRUETYPE_FONT, new File("LuckiestGuy-Regular.ttf")).deriveFont((float)alto*1f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(this.fuenteTablero);
       } catch (IOException|FontFormatException e) {
            System.out.println("Fuente no encontrada");
       }
    }

    public void paintComponent(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, (int)this.getWidth(), (int)this.getHeight());
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(this.fuenteTablero); 
    
        g.drawString("Puntaje: ", (int)(this.getWidth()/26), (int)(.85*this.getHeight()));
        g.setColor(new Color(255,255,70));
        g.drawString("                        " + Integer.toString(this.puntaje), (int)(this.getWidth()/26), (int)(.85*this.getHeight()));
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Vidas Restantes: ", (int)(this.getWidth()/2)-(int)(2*this.getHeight()), (int)(.85*this.getHeight()));

        pintarVidasRestantes(g);
        
    }

    public void setPuntaje(int puntaje){
        this.puntaje = puntaje;
    }

    public void setVidasRestantes(int vidas){
        this.vidasRestantes = vidas;
    }

    public void pintarVidasRestantes(Graphics g) {
        for (int i=0; i<this.vidasRestantes-1; i++){
            g.drawImage(this.iconoPacMan, (int)(this.getWidth()/1.33) + (int)(1.1*i*this.getHeight()) , (int)(.06*this.getHeight()) , (int)(.8*this.getHeight()), (int)(.9*this.getHeight()), this);

        }
    }

}