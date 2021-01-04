package Server;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import database.Create;
import database.CreateTable;
import pref.pref;
public class gServer 
{	
	static int SERVER_PORT;
	
	public static void main(String[] args) throws IOException 
	{

		SERVER_PORT = 5556;
		//set up a server to listen for connections on a socket
		ServerSocket server = new ServerSocket(SERVER_PORT);
		System.out.println("Waiting for the client to connect...");
		
		File file = new File (pref.GAME_PATH+"/piratesdb.db");
		
		if(!file.exists()){
			Create.createNewDatabase(pref.GAME_PATH +"/piratesdb.db");
			CreateTable.createNewTable();		
		}
		
		
		while(true) 
		{
			Socket s = server.accept();
			//System.out.println("Client connected...");
			gService myService = new gService(s);
			Thread t = new Thread(myService);
			t.start();
		}
	}
}
