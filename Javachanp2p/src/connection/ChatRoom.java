package connection;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import affichage.Salle;
import data.IdentiteReseau;
import data.Message;


public class ChatRoom {

	public ChatRoom(String IP, int port, IdentiteReseau id){
		this.id = id;
		this.connections = new ArrayList<Connection>();
		this.identites = new ArrayList<IdentiteReseau>();
		
		System.out.println("***ChatRoom : Création d'un client");
		connection(new IdentiteReseau(IP,port));
		for(int i = 0; i < 34678567;i++) i=i+0;//wait a little
		updateUsers();
		
		Thread srv = new Thread(new Serveur(id.getPort(),this));
		srv.start();
		
		salle = new Salle(this);
	}
	
	public ChatRoom(IdentiteReseau id){
		this.id = id;
		this.connections = new ArrayList<Connection>();
		this.identites = new ArrayList<IdentiteReseau>();
		System.out.println("***ChatRoom : Création d'un serveur port:" + id.getPort());
		
		Thread srv = new Thread(new Serveur(id.getPort(),this));
		srv.start();
		
		salle = new Salle(this);
	}
	
	private void updateUsers() {
		if(!connections.isEmpty()){
			try {
				connections.get(0).requestListUser();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("Chat vide");
		}
	}

	private IdentiteReseau id;
	private ArrayList<Connection> connections;
	private ArrayList<IdentiteReseau> identites;
	private Salle salle;
	
	private void connection(IdentiteReseau id){
		try {
			Thread t = new Thread(new Client(id.getIP(),id.getPort(), this));
			t.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}
	
	public void addMessage(Message msg){
		salle.addMessage(msg);
		System.out.println(msg.getAuthor().getPseudo() + " : " + msg.getMessage());		
	}

	public void addIdentite(Connection connection, IdentiteReseau identite){
		connections.add(connection);
		identites.add(identite);
		System.out.println("id : "+ identite.getPseudo() + " ip : " + identite.getIP() + " port : " + identite.getPort());
	}
	
	public void sendMessage(Message msg){
		//System.out.println("***debug sendmessage taille:"+connections.size());
		//on signe le message
		msg.setAuthor(id);
		for(int i = 0 ; i < connections.size(); i++){
			try {
				connections.get(i).sendMessage(msg);
			} catch (IOException e) {
				//probleme avec cette connection ? test puis retrer possible de la liste
			}
		}
	}

	public IdentiteReseau getIdentite() {
		return id;
	}

	public ArrayList<IdentiteReseau> getCurrentUser() {
		return identites;
	}

	/**
	 * on creé toutes les nouvelles connections inhexistantes
	 * @param userList
	 */
	public void addUserList(ArrayList<IdentiteReseau> userList) {
		for(int i = 0; i < userList.size(); i ++){
			IdentiteReseau iden = userList.get(i);
			if(!identites.contains(iden) && !iden.equals(id)){
				connection(userList.get(i));
			}
		}		
	}
}