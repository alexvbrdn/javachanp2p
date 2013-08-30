package data;

import java.awt.image.BufferedImage;

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
	
	private String text;
	private BufferedImage image;
	private boolean haveAImage;
	private long ID = 0;
	
	public void genereID(){
		 ID = (long) (Math.random() * 1000000000000000000L);
	}
	
	public void setID(long ID){
		this.ID = ID;
	}
}
