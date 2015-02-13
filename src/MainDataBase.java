import java.sql.SQLException;
import java.util.Random;




public class MainDataBase {

	public static void main(String[] args) throws SQLException {
		Login login = new Login();
		 DB db = new DB("jdbc:mysql://localhost/", "", "root", "5813"); 
	       // db.update("drop database authorization;");
	        db.update("create database IF NOT EXISTS authorization;");
	        db.update("use authorization;");
	        db.update("create table IF NOT EXISTS user(id_user int(10) AUTO_INCREMENT, login varchar(50) NOT NULL, password varchar(20) NOT NULL, PRIMARY KEY(id_user), score int(10));");
	        db.showResultSet(db.query("SELECT * FROM user;"));
	}

}
