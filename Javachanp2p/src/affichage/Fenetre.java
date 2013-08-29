package affichage;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
 
public class Fenetre extends JFrame implements ActionListener{
	private JPanel pan = new JPanel();
	private JTextField textPseudo = new JTextField("");
	private JLabel labelPseudo = new JLabel("Pseudo: ");
	private JButton boutonCreer = new JButton("Créer une salle");
	private JButton boutonRejoindre = new JButton("Rejoindre une salle");
 
	public Fenetre(){
		this.setTitle("Chat P2P");
		this.setSize(300, 150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		boutonCreer.addActionListener(this);
		boutonRejoindre.addActionListener(this);
		
		textPseudo.setPreferredSize(new Dimension(150, 30));
		pan.add(labelPseudo);
		pan.add(textPseudo);
		pan.add(boutonCreer);
		pan.add(boutonRejoindre);
		this.setContentPane(pan);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object obj = arg0.getSource();
		if(obj.equals(boutonCreer)){
			//Créer une salle
			System.out.println("Créer une salle");
		}
		else{
			//Rejoindre une salle
			System.out.println("Rejoindre une salle");
			JOptionPane jop = new JOptionPane();
		    String ipServeur = jop.showInputDialog(null, "Veuillez entrez l'adresse IP du serveur", "IP:port", JOptionPane.QUESTION_MESSAGE);
		    System.out.println(ipServeur);
		}
		
	}      
}