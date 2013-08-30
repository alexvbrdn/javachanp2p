package connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {

	public static void main(String[] zero) {

		ServerSocket socketserver  ;
		

		try {

			socketserver = new ServerSocket(2009);
			while(true){
				Socket socketduserveur  = socketserver.accept();
				Thread t = new Thread(new Reception(socketduserveur.getInputStream()));
	            t.start();
			}
			

		}catch (IOException e) {
			e.printStackTrace();
		}
	}

}