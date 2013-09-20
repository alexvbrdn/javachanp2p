package data;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.imageio.ImageIO;

public class Message {
	public Message(String str){
		text = str;
		type = TYPE_MESSAGE_TEXT;
	}
	
	public Message(String str, BufferedImage img){
		text = str;
		image = img;
		type = str.equals("") ? TYPE_MESSAGE_IMAGE : TYPE_MESSAGE_TEXT_IMAGE;
	}
	
	/**
	 * Differents type de message possible : à deplacer ou il y aurat toute les types de communications
	 */
	public static final byte TYPE_MESSAGE_TEXT = 0;
	public static final byte TYPE_MESSAGE_TEXT_IMAGE = 1;
	public static final byte TYPE_MESSAGE_IMAGE = 2;
	
	private Date date;
	private String text;
	private BufferedImage image;
	private byte type;
	private long ID = 0;
	
	public void genereDate(){
		date = new Date();
	}
	
	public void setDate(){
		
	}
	
	public void genereID(){
		 ID = (long) (Math.random() * 1000000000000000000L);
	}
	
	public void setID(long ID){
		this.ID = ID;
	}
	
	
	public boolean aUneImage(){
		return type == TYPE_MESSAGE_IMAGE || type == TYPE_MESSAGE_TEXT_IMAGE;
	}
	
	/**
	 * encodage :
	 * HEADER:
	 *	1er octet : TYPE_MESSAGE
	 *	SI text alors :
	 *			2 prochains octets taille du message
	 *	SI IMAGE alors :
	 *			3 prochains octets taille de l'image
	 * BODY :
	 * 	TEXT
	 * 	IMAGE 	
	 * @return
	 */
	public InputStream getBytes(){ //implementation du text seul
		int taille_text = text.length();
		
		byte taille_text_1 = (byte) (text.length() % 256);
		byte taille_text_2 = (byte) (text.length() / 256);
		
		byte[] buffer = new byte[taille_text + 3];
		buffer[0] = type;
		buffer[1] = taille_text_2;
		buffer[2] = taille_text_1;
		
		for( int i = 0 ; i < taille_text; i++){
			buffer[i + 3] = (byte) text.charAt(i);
		}
		return new ByteArrayInputStream( buffer );
	}
 
	public InputStream getImageInputStream(){
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "jpg", os);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ByteArrayInputStream(os.toByteArray());
	}
	public String getMessage(){
		return this.text;
	}
	public BufferedImage getImage(){
		return this.image;
	}
}
