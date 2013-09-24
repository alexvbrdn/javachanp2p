package data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Collections;

import connection.Connection;

public class IdentiteReseau {

	public IdentiteReseau(String IP, int port, String pseudo){
		this.pseudo = pseudo;
		this.IP = IP;
		this.port = port;
	}
	
	private String pseudo;
	private String IP;
	private int port;
	
	
	/**
	 * HEADER:
	 * 	1:le type INDENTITE
	 * 	2:la taille du pseudo
	 * 
	 * BODY:
	 * 	4prochain  IP
	 * 	2prochain port
	 * 	pseudo
	 * 
	 * 
	 * @return
	 */
	public InputStream getInputStream(){
		
		byte taille_pseudo = (byte) pseudo.length();
		
		byte[] buffer = new byte[taille_pseudo + 
		                         2 + //header
		                         4 + //ip
		                         2 ];//port
		
		buffer[0] = Connection.TYPE_IDENTITE;
		buffer[1] = taille_pseudo;
		String[] str_ip = IP.split("\\.");

		for(int i = 0 ; i < 4 ; i++){
			buffer[i + 2] = Byte.decode(str_ip[i]);
		}
		buffer[6] = (byte) (port / 256);
		buffer[7] = (byte) (port % 256);
		
		for( int i = 0 ; i < taille_pseudo ; i++ ){
			buffer[i + 8] = (byte) pseudo.charAt(i);
		}
		
		return new ByteArrayInputStream( buffer );
	}
	
	/**
	 * decode un paquet identite à partir d'un flux d'entré et le retire du flux 
	 * on part du principe qu'il manque le premier octet (le type du paquet)
	 * @param in le flux d'entré contenant le paquet a decoder
	 * @return l'identité reseau contenu dans le paquet
	 * @throws IOException 
	 */
	public static IdentiteReseau decode(InputStream in) throws IOException{
		byte[] size_pseudo = new byte[1]; 
		in.read(size_pseudo);
		
		byte[] ip_byte = new byte[4];
		in.read(ip_byte);
		String ip = (ip_byte[0] + 256)%256 + "." + (ip_byte[1] + 256)%256 + "." + (ip_byte[2] + 256)%256 + "." + (ip_byte[3] + 256)%256;
		
		byte[] port_byte = new byte[2];
		in.read(port_byte);
		int port_int = ((port_byte[0] + 256)%256)*256 + (port_byte[1] + 256)%256;
		
		byte[] pseudo = new byte[(size_pseudo[0] + 256)%256];
		in.read(pseudo);
		String pseudo_str = "";
		for(int i = 0 ; i < pseudo.length; i++)
			pseudo_str += (char) pseudo[i];
		
		return new IdentiteReseau(ip,port_int,pseudo_str);
	}
	
	/**
	 * Header:
	 * 	1 : TYPE_USER_LIST
	 *  2 : Nbr USERs
	 *  
	 *  Body:
	 *  le nbr de paquets identités
	 * 
	 * @return
	 */
	public static InputStream userListGetInputStream(ArrayList<IdentiteReseau> ids){

		byte[] buffer = new byte[2];
		buffer[0] = Connection.TYPE_LIST_USER;
		buffer[1] = (byte) ids.size();
		
		ArrayList<InputStream> inputstream = new ArrayList<InputStream>();
		inputstream.add( new ByteArrayInputStream(buffer));
		for(int i = 0; i < ids.size(); i++){
			inputstream.add(ids.get(i).getInputStream());
		}
		return new SequenceInputStream(Collections.enumeration(inputstream));
	}
	
	
	public static ArrayList<IdentiteReseau> decodeUserList(InputStream in) throws IOException {
		byte[] numberUser_byte = new byte[1];
		in.read(numberUser_byte);
		int numberUser_int = (numberUser_byte[0] + 256)%256;
		
		ArrayList<IdentiteReseau> listUser = new ArrayList<IdentiteReseau>();
		
		for(int i = 0; i < numberUser_int; i++){
			listUser.add(IdentiteReseau.decode(in));
		}
			
		return listUser;
	}

	public String getPseudo() {
		return pseudo;
	}

	public int getPort() {
		return port;
	}

	public String getIP(){
		return IP;
	}

	
}
