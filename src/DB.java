import java.sql.*;

import javax.swing.JOptionPane;

public class DB {
	private Connection cn;
	private Statement st;
	private ResultSet rs;

	public DB(String path, String nameDB, String login, String pass)
			throws SQLException {
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		cn = DriverManager.getConnection(path + nameDB, login, pass);
		st = cn.createStatement();
	}

	public void update(String sql) throws SQLException {
			st.executeUpdate(sql);
	}

	public ResultSet query(String sql) throws SQLException {
		rs = st.executeQuery(sql);
		return rs;
	}

	public void close() throws SQLException {
			st.close();
			cn.close();
	}

	public void showDatabaseMetaData() {
		try {
			DatabaseMetaData dbmd = cn.getMetaData();
			System.out.println(dbmd.getDatabaseProductName());
			System.out.println(dbmd.getDatabaseProductVersion());
			System.out.println(dbmd.getDriverName());
			System.out.println(dbmd.getDriverVersion());
		} catch (SQLException ex) {
			System.out.println("Error in showDatabaseMetaData " + ex);
		}
	}

	public void showResultSet(ResultSet rs) {
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				System.out.print(rsmd.getColumnName(i) + "\t");
			}
			while (rs.next()) {
				System.out.println();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					System.out.print(rs.getString(i) + "\t");
				}
			}
			System.out.println();
		} catch (SQLException ex) {
			System.out.println("Error in showResultSet " + ex);
		}
	}
	public int getScore(String login){
		int score = 0;
		try {
			ResultSet data = query("SELECT score FROM user Where login='"
							+ login + "';");
			if (data.next()) {
				score = data.getInt("score");
			}
		}
			catch(SQLException ex){
			}
		return score;
	}
	
	public void setScore(String login, int score){
		try {
			update("UPDATE user SET score="+score+" Where login='"+ login + "';");
		}
			catch(SQLException ex){
				ex.printStackTrace();
			}
		
	}
	
		
	
}