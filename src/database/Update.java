package database;


import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Update { 
   
    public static Connection connect() {  
        // SQLite connection string  
        String url = "jdbc:sqlite:bin/piratesdb.db";  
        Connection conn = null;  
        try {  
            conn = DriverManager.getConnection(url);  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
        return conn;  
    }  
   

    public  void UpdateDeaths(int id){     	
 	 
   	 
   	 String sql = "UPDATE players SET deaths = deaths + 1 WHERE id = ?"; 
          
        try {  
            Connection conn = connect();  
            PreparedStatement pstmt = conn.prepareStatement(sql);  
            pstmt.setInt(1, id);
            pstmt.executeUpdate();                
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    } 
    
    public  void UpdateDistance(int id, int dist){     	
    	 
      	 
      	 String sql = "UPDATE players SET distance = ? WHERE id = ?"; 
             
           try {  
               Connection conn = connect();  
               PreparedStatement pstmt = conn.prepareStatement(sql);  
               pstmt.setInt(1, dist);
               pstmt.setInt(2, id);  
               pstmt.executeUpdate();                
           } catch (SQLException e) {  
               System.out.println(e.getMessage());  
           }  
       } 
    	
    	
   
}  