import javax.swing.JFrame;

public class MainPacMan extends JFrame{
	public MainPacMan() {
		super("PacMan v1.0 by Agus Quintanar");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(new Pista());
		this.pack();
		this.setVisible(true);
		
	}
	public static void main(String[] args) {
		MainPacMan game =new MainPacMan();
	}

}
