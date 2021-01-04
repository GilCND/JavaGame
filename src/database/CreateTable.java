package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;  
import java.sql.Statement;

import pref.pref;

 
   
public class CreateTable {  

    
	public static void createNewTable() {
    	
    	
        // SQLite connection string  
        String url = "jdbc:sqlite:"+pref.GAME_PATH+"/piratesdb.db";  
          
        // SQL statement for creating a new table  
        String sql = "CREATE TABLE IF NOT EXISTS players (\n"  
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"     
                + " name text, \n"
                + " deaths int NOT NULL, \n"
                + " distance int NOT NULL"  
                + ");";  
          
        try{  
            Connection conn = DriverManager.getConnection(url);  
            Statement stmt = conn.createStatement();  
            stmt.execute(sql);  
            System.out.println("Connection Created Table."); 
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }  
   
}  