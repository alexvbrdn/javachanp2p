package connection;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import data.Message;

public class Reception implements Runnable{

	private InputStream input;
	private boolean run;

	public Reception(InputStream input){
		this.input = input;
	}

	@Override
	public void run() {
		run = true;
		String rawmsg = "";
		String messageinfo = "";
		try {
			while(true){

				byte buf[] = new byte[1024];

				try {
					while(input.read(buf)!=-1){
						rawmsg += new String(buf);
						if(rawmsg.contains("</message>")){
							String[] tab = rawmsg.split("</message>");
							rawmsg = tab[1];
							messageinfo = tab[0] + "</message>";
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println(decodeMessage(messageinfo,rawmsg).getMessage());
				break;
			}




			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	private Message decodeMessage(String info, String image){
		String text = getToken(info,"<text>","</text>");
		Boolean haveAImage = Boolean.parseBoolean(getToken(info,"<image>","</image>"));
		if(haveAImage){
			BufferedImage img = null;
			try {
				img = ImageIO.read(new ByteArrayInputStream(image.getBytes()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new Message(text,img);
		}
		else{
			return new Message(text);
		}
	}

	private String getToken(String text, String tokendebut, String tokenfin){
		Boolean find = false;
		System.out.println(text);
		int i1;
		for(i1 = 0; i1 < text.length() && !find; i1++){
			for(int j = 0; j < tokendebut.length(); j++){
				if(text.charAt(i1 + j) == tokendebut.charAt(j)){
					find = true;
				}else{
					find = false;
					break;
				}
			}
		}
		i1 += tokendebut.length()-1;
		System.out.println(i1);
		find = false;
		int i2;
		for(i2 = text.length() - 1; i2 > 0 && !find; i2--){
			for(int j = tokenfin.length() - 1; j > 0; j--){
				if(text.charAt(i2 - tokenfin.length() - 1 + j ) == tokenfin.charAt(j)){
					find = true;
				}else{
					find = false;
					break;
				}
			}
		}
		i2 -= tokenfin.length();
		System.out.println(i2);
		return text.substring(i1,i2);
	}
}
