package data;

import java.io.ByteArrayInputStream;
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
}
