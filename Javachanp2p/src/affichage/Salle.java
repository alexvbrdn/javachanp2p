package affichage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import connection.ChatRoom;
import data.Message;

public class Salle extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea textMessage = new JTextArea("");
	private JButton boutonImage = new JButton("Selectionner une image");
	private JButton boutonEnvoyer = new JButton("Envoyer");
	private JLabel textIp = new JLabel();
	private JLabel textPseudo = new JLabel();
	private JLabel textUsers = new JLabel();
	private String cheminImage = null;

	private JPanel content = new JPanel();
	
	private String pseudo;
	private ChatRoom chatroom;
	
	public Salle(ChatRoom chatroom){
		this.chatroom = chatroom;
		this.pseudo = chatroom.getIdentite().getPseudo();
		
		this.setTitle(pseudo+"@" + "Ici bientot le nom du chat !");
		this.setSize(700, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		GridLayout gl = new GridLayout(1, 3);
	    JPanel top = new JPanel();
	    top.setBackground(Color.gray);
	    top.setLayout(gl);
	    top.add(textIp);
	    top.add(textPseudo);
	    top.add(textUsers);
	    textIp.setText(chatroom.getIdentite().getIP());//notre ip le serveur n'existe pas c'est du p2p
	    textIp.setHorizontalAlignment(SwingConstants.LEFT);
	    textIp.setFont(new Font("Arial", Font.PLAIN, 15));
	    textIp.setForeground(Color.blue);
	    textPseudo.setText(pseudo);
	    textPseudo.setHorizontalAlignment(SwingConstants.CENTER);
	    textPseudo.setFont(new Font("Arial", Font.PLAIN, 15));
	    textPseudo.setForeground(Color.red);
	    textUsers.setText("1");
	    textUsers.setHorizontalAlignment(SwingConstants.RIGHT);
	    textUsers.setFont(new Font("Arial", Font.PLAIN, 15));
	    textUsers.setForeground(Color.green);
	    
	    GridLayout gl1 = new GridLayout(1, 1);
	    gl1.setHgap(5);
	    gl1.setVgap(5);
	    JPanel bottom = new JPanel();
	    bottom.setLayout(gl1);
	    bottom.add(textMessage);
	    bottom.add(boutonImage);
	    bottom.add(boutonEnvoyer);
	    
	    JScrollPane pictureScrollPane = new JScrollPane(this.content);
	    
	
	    
	    
	    boutonImage.addActionListener(this);
	    boutonEnvoyer.addActionListener(this);
		
		this.getContentPane().add(top, BorderLayout.NORTH);
		this.getContentPane().add(bottom, BorderLayout.SOUTH);
		this.getContentPane().add(pictureScrollPane, BorderLayout.CENTER);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object obj = arg0.getSource();
		if(obj.equals(boutonEnvoyer)){
			System.out.println("*** Salle : " + pseudo + " nouveau message créé : " + textMessage.getText());
			//System.out.println(this.cheminImage);
			
			envoyerMessage(textMessage.getText(), this.cheminImage);
			textMessage.setText("");
		}
		else{
			String cheminImage = selectImage(boutonImage);
			if(cheminImage == null){
			}
			else{
				boutonImage.setText(cheminImage);
				this.cheminImage = cheminImage;
			}
		}
		
	}
	private String selectImage(Component parent)
	{
		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Image (jpg, jpeg, gif, png)", "jpg", "gif", "jpeg", "png");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileFilter(filter);

		if(fc.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION){
			return fc.getSelectedFile().getAbsolutePath();
		}
	    return null;
	}
	private void envoyerMessage(String message, String image)
	{
		if(image==null){
			if(message == null){
				return;
			}
			Message msg = new Message(message);
			this.content.add(new JLabel(message));
			chatroom.sendMessage(msg);
		}
		else{
			try {
				new Message(message, ImageIO.read(new File(image)));
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void addMessage(Message msg) {			
		this.content.add(new JLabel(msg.getMessage()));
		System.out.println("***Salle : " + pseudo + " ajout d'un nouveau message : " + msg.getMessage());
		this.repaint();
	}
}