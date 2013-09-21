package connection;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import data.IdentiteReseau;
import data.Message;

public class Connection implements Runnable {

	public Connection(InputStream in, OutputStream out, ChatRoom chr){
		this.in = in;
		this.out = out;
		this.chatroom = chr;
	}
	
	public static final byte TYPE_MESSAGE_TEXT = 0b0000001;
	public static final byte TYPE_MESSAGE_TEXT_IMAGE = 0b0000011;
	public static final byte TYPE_MESSAGE_IMAGE = 0b0000101;
	public static final byte TYPE_IDENTITE = 0b0000010;
	
	private InputStream in;
	private OutputStream out;
	private ChatRoom chatroom;
	
	
	@Override
	public void run() {
		// nouvelle connexion on envoie notre identité :
		try {
			transfert(chatroom.getIdentite().getInputStream());

			byte[] buf = new byte[1];
			int n;
			n=in.read(buf);
			if(n != -1) {
				System.out.println("type:" + buf[0]);
				switch (buf[0]) {
				case TYPE_IDENTITE:
					chatroom.addIdentite(this,IdentiteReseau.decode(in));
					break;
				case TYPE_MESSAGE_TEXT:
					chatroom.addMessage(Message.decode(in,TYPE_MESSAGE_TEXT));
					break;
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void transfert(InputStream in) throws IOException
    {
        byte buf[] = new byte[1024];
        
        int n;
        while((n=in.read(buf))!=-1)
            out.write(buf,0,n);
    }

	
	public void sendMessage(Message msg) throws IOException {
		transfert(msg.getInputStream());	
	}
	

}
