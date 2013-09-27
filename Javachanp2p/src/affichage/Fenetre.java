package affichage;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import connection.ChatRoom;
import data.IdentiteReseau;

/**
 * La class fenetre est instancié au demarage.
 * Elle ouvre une fenetre permetant de ce connecter à des salles ou bien créer des serveurs.
 * Elle peut donc instancier des ChatRooms.
 * 
 * @author Ogre
 *
 */
public class Fenetre extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pan = new JPanel();
	private JTextField textPseudo = new JTextField("");
	private JLabel labelPseudo = new JLabel("Pseudo: ");
	private JLabel erreurDisplay = new JLabel();
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

		pan.add(erreurDisplay);
		this.setContentPane(pan);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String pseudo = textPseudo.getText();
		if(!validationPseudo(pseudo)){
			return;
		}
		Object obj = arg0.getSource();
		if(obj.equals(boutonCreer)){
			//Créer une salle
			String port = JOptionPane.showInputDialog(null, "Veuillez entrez le port du serveur", "Port", JOptionPane.QUESTION_MESSAGE);
			try {
				int port_int = Integer.parseInt(port);
				new ChatRoom(new IdentiteReseau(port_int,pseudo));
			}
			catch(NumberFormatException | UnknownHostException e) {
				erreurDisplay.setText(e.getMessage());
			}

		}
		else{
			//Rejoindre une salle
			String ipServeur = JOptionPane.showInputDialog(null, "Veuillez entrez l'adresse IP du serveur", "IP:port", JOptionPane.QUESTION_MESSAGE);
			if(ipServeur != null){
				try {
					new ChatRoom(ipServeur.split(":")[0], Integer.parseInt(ipServeur.split(":")[1]), new IdentiteReseau(4011,pseudo));
				} catch (NumberFormatException | IOException e) {
					erreurDisplay.setText(e.getMessage());
				}
			}
		}
		erreurDisplay.setText("");
	}
	
	/**
	 * Test le pseudo
	 * Valeurs arbitraires
	 * @param pseudo
	 * @return
	 */
	private boolean validationPseudo(String pseudo) {
		return (pseudo.length() > 5)&&(pseudo.length() < 20);
	}      
}