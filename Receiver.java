// package LAN_comm;
import java.net.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;


public class Receiver extends Object{

	
	private 				String 			hostName 	= "127.0.0.1"	;
	private 				int 			portNumber 	= 0				;
	private 				Socket  		sender 						; 
	private 				PrintWriter 	outToServer					;
	private					BufferedReader 	inFromServer 				;

	public Receiver() throws UnknownHostException, 
							 IOException, 
							 SecurityException, 
							 IllegalArgumentException{
		this("127.0.0.1",5555);
	}

	public Receiver(String hostName, int portNumber) throws UnknownHostException, 
															IOException, 
															SecurityException, 
															IllegalArgumentException
	{
		hostName 		= hostName;
		portNumber 		= portNumber;
		sender	 		= new Socket(hostName, portNumber);
		outToServer 	= new PrintWriter(sender.getOutputStream(), true); 						// write to the socket server
		inFromServer 	= new BufferedReader(new InputStreamReader(sender.getInputStream()));	// input from socket server
	}

	public String 	getHostName()	{return hostName;}
	public int 		getPortNumber() {return portNumber;}


	public void 	setHostName 	(String newHostName) throws UnknownHostException, IOException, SecurityException, IllegalArgumentException{
		hostName 	= newHostName;
		sender 		= new Socket(hostName, portNumber); 	// reinitialize the Socket
	}
	public void 	setPortNumber 	(int 	newPortNum) throws UnknownHostException, IOException, SecurityException, IllegalArgumentException{
		portNumber 	= newPortNum	;
		sender 		= new Socket(hostName, portNumber); 	// reinitialize the Socket
	}

	public String readFromServer() throws IOException{
		return inFromServer.readLine();
	}

	public void sendToServer(String data) throws IOException{
		outToServer.println(data);
	}
	
}