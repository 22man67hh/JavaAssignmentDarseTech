
package servlet;


import java.sql.*;
public class conn {
    
    private static String url="jdbc:mysql://localhost:3306/book";
    private static String user="root";
    private static String password="";
    
   public static Connection getConnection() throws SQLException, ClassNotFoundException{
	   try {
		   
		   Class.forName("com.mysql.cj.jdbc.Driver");
	        return DriverManager.getConnection(url, user, password);
	    } catch (SQLException e) {
	        System.err.println("Database connection failed: " + e.getMessage());
	        throw e;
	    }   }
}
