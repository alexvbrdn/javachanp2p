package connection;
import java.io.IOException;
import java.net.UnknownHostException;

import data.IdentiteReseau;
import data.Message;


public class ChatRoom {
	public ChatRoom(String IP, int port, IdentiteReseau id){
		this.port = port;
		try {
			Thread t = new Thread(new Client(IP,port, this));
			t.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		Thread srv = new Thread(new Serveur(10,this));
		srv.run();
	}
	
	public int port;
	
	private void getListUtilisateur(){
		
	}
	
	public void addMessage(Message msg){
		
	}
}
