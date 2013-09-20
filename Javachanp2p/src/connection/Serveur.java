package connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur implements Runnable {

	public Serveur(int port){
		this.port = port;
	}
	
	public static boolean running = true;
	private ServerSocket socketserver;
	private int port;
	
	public void run() {
		running = true;

		try {

			socketserver = new ServerSocket(port);
			
			while(running){
				Socket socketduserveur  = socketserver.accept();
				Thread t = new Thread(new Connection(socketduserveur.getInputStream(), socketduserveur.getOutputStream()));
	            t.start();
			}		

		}catch (IOException e) {
			e.printStackTrace();
		}		
	}
}