package affichage;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import connection.Serveur;
 
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
		String pseudo = textPseudo.getText();
		Object obj = arg0.getSource();
		if(obj.equals(boutonCreer)){
			//Créer une salle
			System.out.println("Créer une salle");
			JOptionPane jop = new JOptionPane();
		    String port = jop.showInputDialog(null, "Veuillez entrez le port du serveur", "Port", JOptionPane.QUESTION_MESSAGE);
		    try {
		        int x = Integer.parseInt(port);
		        System.out.println(x);
		    }
		    catch(NumberFormatException e) {
		    	e.printStackTrace();
		    	System.exit(0);
		    }
			String ipServeur = "127.0.0.1"+":"+port;
			new Salle(pseudo, ipServeur);
			//new Serveur(Integer.parseInt(port));
		}
		else{
			//Rejoindre une salle
			System.out.println("Rejoindre une salle");
			JOptionPane jop = new JOptionPane();
		    String ipServeur = jop.showInputDialog(null, "Veuillez entrez l'adresse IP du serveur", "IP:port", JOptionPane.QUESTION_MESSAGE);
		    System.out.println(ipServeur);
		    new Salle(pseudo, ipServeur);
		}
		
	}      
}