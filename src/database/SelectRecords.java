package database;
import java.sql.DriverManager;
import java.awt.Cursor;
import java.sql.Connection;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;
import java.util.ArrayList;

import org.sqlite.SQLiteException;

  
   
public class SelectRecords {  
   
    private Connection connect() {  
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
   

    public void selectAll(){  
        String sql = "SELECT * FROM players";  
          
        try {  
            Connection conn = this.connect();  
            Statement stmt  = conn.createStatement();  
            ResultSet rs    = stmt.executeQuery(sql);  
              
            // loop through the result set  
            while (rs.next()) {  
                System.out.println(rs.getInt("id") +  "\t" +   
                                   rs.getString("name") + "\t" +  
                                   rs.getDouble("capacity"));  
            }  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }  



    public String getName(int id){   
        String sql = "SELECT id, name FROM players WHERE id = "+id;
        String name = "";        
        try {  
            Connection conn = this.connect();  
            Statement stmt  = conn.createStatement();  
            ResultSet rs    = stmt.executeQuery(sql);
            
            while (rs.next()) { name = rs.getString("name");}  
            
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }
		return name;  
    }  



    public int getUserId(String name){   
        String sql = "SELECT * FROM players WHERE name = '"+ name +"'";
        int gid = 0;
        
        try {  
            Connection conn = this.connect();  
            Statement stmt  = conn.createStatement();  
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {   gid = rs.getInt("id"); }  
            
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }
		return gid;  
    }  




   
}  