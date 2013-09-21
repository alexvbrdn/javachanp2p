package data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import connection.Connection;

public class IdentiteReseau {

	public IdentiteReseau(String pseudo){
		this.pseudo = pseudo;
	}
	
	private String pseudo;
	
	
	/**
	 * HEADER:
	 * 	le type INDENTITE
	 * 	la taille du pseudo
	 * 
	 * 
	 * 
	 * @return
	 */
	public InputStream getBytesInputStream(){
		
		byte taille_pseudo = (byte) pseudo.length();
		
		byte[] buffer = new byte[taille_pseudo + 2];
		
		buffer[0] = Connection.TYPE_IDENTITE;
		buffer[1] = taille_pseudo;
		for( int i = 0 ; i < taille_pseudo ; i++ ){
			buffer[i + 2] = (byte) pseudo.charAt(i);
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
		byte[] size = new byte[1]; 
		in.read(size);
		
		byte[] pseudo = new byte[size[0]];
		in.read(pseudo);
		String pseudo_str = "";
		for(int i = 0 ; i < pseudo.length; i++)
			pseudo_str += (char) pseudo[i];
		
		return new IdentiteReseau(pseudo_str);
	}
}
