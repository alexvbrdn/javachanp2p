package connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Connection implements Runnable {

	public Connection(InputStream in, OutputStream out){
		this.in = in;
		this.out = out;
	}
	
	public static final byte TYPE_MESSAGE_TEXT = 0;
	public static final byte TYPE_MESSAGE_TEXT_IMAGE = 1;
	public static final byte TYPE_MESSAGE_IMAGE = 2;
	public static final byte TYPE_IDENTITE = 3;
	
	private InputStream in;
	private OutputStream out;
	
	
	@Override
	public void run() {
		// nouvelle connexion on envoie notre identité :
		try {
			out.write("connection".getBytes());

			byte[] buf = new byte[1];
			int n;
			n=in.read(buf);
			if(n != -1) {
				System.out.print(buf[0]);
				switch (buf[0]) {
				case TYPE_IDENTITE:
					in.read(buf);
					byte size = buf[0];
					String pseudo = "";
					for (byte i = 0 ; i < size ; i++){
						in.read(buf);
						pseudo += (char) buf[0];
					}
					break;
				}
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void sendIdentite(){
		
	}

}
