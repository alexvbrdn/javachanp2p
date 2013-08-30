package data;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.imageio.ImageIO;

public class Message {
	public Message(String str){
		text = str;
		haveAImage = false;
	}
	
	public Message(String str, BufferedImage img){
		text = str;
		image = img;
		haveAImage = true;
	}
	
	private Date date;
	private String text;
	private BufferedImage image;
	private boolean haveAImage;
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
		return haveAImage;
	}
	
	//pour l'émission
	public InputStream aEnvoyer(){
		String packet = "";
		packet = packet + "<message>";
		{
			packet = packet + "<text>";
			packet = packet + text;
			packet = packet + "</text>";
		}
		{
			packet = packet + "<date>";
			packet = packet + date;
			packet = packet + "</date>";
		}
		{
			packet = packet + "<image>";
			packet = packet + haveAImage;
			packet = packet + "</image>";
		}
		packet = packet + "</message>";
		return new ByteArrayInputStream(packet.getBytes());
	}
 
	public InputStream getImageInputStream(){
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", os);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ByteArrayInputStream(os.toByteArray());
	}
	public String getMessage(){
		return this.text;
	}
}
