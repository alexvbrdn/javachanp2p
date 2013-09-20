import java.io.IOException;
import java.net.UnknownHostException;

import connection.Client;
import connection.Serveur;
import data.IdentiteReseau;


public class ChatRoom {
	public ChatRoom(String IP, int port, IdentiteReseau id){
		this.port = port;
		try {
			Thread t = new Thread(new Client(IP,port));
			t.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		Thread srv = new Thread(new Serveur(port));
		srv.run();
	}
	
	public int port;
	
	private void getListUtilisateur(){
		
	}
}
