import connection.ChatRoom;
import data.IdentiteReseau;
import affichage.Fenetre;


public class Main {

	public static void main(String[] args) {
		//new Fenetre();
		new ChatRoom("127.0.0.1",2007, new IdentiteReseau("admin"));
	}

}