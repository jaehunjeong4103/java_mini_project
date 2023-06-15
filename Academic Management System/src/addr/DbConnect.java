package addr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {
	String url = "jdbc:mysql://localhost/address";
	String user = "root";
	String password = "1111";
	Connection conn;	
	
	public DbConnect(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("JDBC드라이버를 설치했습니다.");
		}
		catch(ClassNotFoundException e) {
			System.out.println("드라이버를 찾을 수 없습니다.");
		}		
	
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("데이터베이스를 연결했습니다");
		} catch (SQLException e) {
			System.out.println("데이터베이스 연결에 실패했습니다.");
			e.printStackTrace();
		}		
		// TODO Auto-generated constructor stub
	}
}
