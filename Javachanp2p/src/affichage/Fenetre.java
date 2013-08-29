package affichage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
 
public class Fenetre extends JFrame{
	private JPanel pan = new JPanel();
	private JButton boutonCreer = new JButton("Créer une salle");
	private JButton boutonRejoindre = new JButton("Rejoindre une salle");
 
	public Fenetre(){
		this.setTitle("Chat P2P");
		this.setSize(300, 150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		pan.add(boutonCreer);
		pan.add(boutonRejoindre);
		this.setContentPane(pan);
		this.setVisible(true);
	}      
}