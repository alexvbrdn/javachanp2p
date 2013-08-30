package connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import data.Message;


public class Client implements Runnable{


	public static void main(String[] zero) {
		Socket socket = null;
		try {

			System.out.println("Demande de connexion");
			socket = new Socket("127.0.0.1",2009);
			System.out.println("Connexion établie avec le serveur, authentification"); // Si le message s'affiche c'est que je suis connecté

			Thread t1 = new Thread(new Emission(socket.getOutputStream(), new Message("lol")));
			t1.start();



		} catch (UnknownHostException e) {
			System.err.println("Impossible de se connecter à l'adresse "+socket.getLocalAddress());
		} catch (IOException e) {
			System.err.println("Aucun serveur à l'écoute du port "+socket.getLocalPort());
		}



	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}