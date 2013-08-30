package connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

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
			transfert(message.aEnvoyer(), out, false);
			if(message.aUneImage()) transfert(message.getImageInputStream(), out, false);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void transfert(InputStream in, OutputStream out, boolean closeOnExit) throws IOException
    {
        byte buf[] = new byte[1024];
        
        int n;
        while((n=in.read(buf))!=-1)
            out.write(buf,0,n);
        
        if (closeOnExit)
        {
            in.close();
            out.close();
        }
    }
}
