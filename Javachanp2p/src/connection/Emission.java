package connection;

import java.io.IOException;
import java.io.OutputStream;
import data.Message;

public class Emission implements Runnable {

	private OutputStream out;
	private Message message;


	public Emission(OutputStream out, Message message) {
		this.out = out;
		this.message = message;
	}


	public void run() {
		
		try {
			Flux.transfert(message.aEnvoyer(), out, false);
			if(message.aUneImage()) Flux.transfert(message.getImageInputStream(), out, false);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
