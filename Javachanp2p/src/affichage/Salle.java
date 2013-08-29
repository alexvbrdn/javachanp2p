package affichage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Salle extends JFrame implements ActionListener{
	private JTextArea textMessage = new JTextArea("");
	private JButton boutonImage = new JButton("Selectionner une image");
	private JButton boutonEnvoyer = new JButton("Envoyer");
	public Salle(String pseudo,String ipServeur){
		this.setTitle(pseudo+"@"+ipServeur);
		this.setSize(700, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
	    panel.setLayout(new GridBagLayout());
	    
	    
	    JEditorPane top = new JEditorPane();
	    top.setEditable(false);
	    top.setContentType("text/html");

	    String text = "<table width=\"100%\"><tr><td align=\"left\" width=\"32%\"><strong color=\"blue\">IP: " + ipServeur + "</strong></td>"
	    + "<td align=\"center\" width=\"34%\"><strong color=\"red\">" + pseudo + "</strong></td>"
	    + "<td align=\"right\"  width=\"32%\"><strong color=\"green\">U: 0</strong></td></tr></table>";

	    top.setText(text);

	    GridBagConstraints constraints = new GridBagConstraints();
	    constraints.gridx = 0;
	    constraints.gridy = 0;
	    constraints.fill = GridBagConstraints.HORIZONTAL;
	    constraints.weightx = 1.0;
	    constraints.insets.bottom = 5;

	    panel.add(top, constraints);
	    
	    GridLayout gl = new GridLayout(1, 1);
	    gl.setHgap(5);
	    gl.setVgap(5);
	    JPanel bottom = new JPanel();
	    bottom.setLayout(gl);
	    bottom.add(textMessage);
	    bottom.add(boutonImage);
	    bottom.add(boutonEnvoyer);
	    
	    boutonImage.addActionListener(this);
	    boutonEnvoyer.addActionListener(this);
		
		this.getContentPane().add(panel, BorderLayout.NORTH);
		this.getContentPane().add(bottom, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object obj = arg0.getSource();
		if(obj.equals(boutonEnvoyer)){
			
		}
		else{
			boutonImage.setText(selectImage(boutonImage));
		}
		
	}
	public String selectImage(Component parent)
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
	
}
