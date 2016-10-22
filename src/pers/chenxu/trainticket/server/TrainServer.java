package pers.chenxu.trainticket.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

public class TrainServer {

	private int clientCt=0;
	
	public static void main(String []args) throws SQLException{
		TrainServer server=new TrainServer(); 
		
	}
	
	public TrainServer(){
		ServerSocket serverSocket;
			try {
				serverSocket = new ServerSocket(8000);
				System.out.println("server started");
				while(true){
					Socket socket=serverSocket.accept();
					clientCt++;
					System.out.println("No."+clientCt+" client join in");
					
					InetAddress inetAddress=socket.getInetAddress();
					
					SingleServer sv=new SingleServer(socket);
					Thread t=new Thread(sv);
					t.start();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
}
