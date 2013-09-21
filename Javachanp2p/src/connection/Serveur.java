package connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur implements Runnable {

	public Serveur(int port, ChatRoom chr){
		this.port = port;
		this.chr = chr;
	}
	
	public static boolean running = true;
	private ServerSocket socketserver;
	private int port;
	private ChatRoom chr;
	
	public void run() {
		running = true;

		try {

			socketserver = new ServerSocket(port);
			
			while(running){
				Socket socketduserveur  = socketserver.accept();
				Thread t = new Thread(new Connection(socketduserveur.getInputStream(), socketduserveur.getOutputStream(), chr));
	            t.start();
			}		

		}catch (IOException e) {
			e.printStackTrace();
		}		
	}
}