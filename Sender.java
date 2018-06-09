// package LAN_comm;
import java.net.*;
import java.io.*;
import jssc.*;


public class Sender {
	public	static final 	int 			BEGIN 		= 0xA5;
	private static final 	int 			DFLT_PORT 	= 5555;
	private static 			int 			count 		= 0;
	private 				SerialPort 		port;
	private 				int 			portNumber;
	private 				ServerSocket 	socket;
	private 				Socket 			client;
	private 				PrintWriter 	outToClient;
	private 				BufferedReader 	inFromClient;
	private 				int 			baud 		= 115200;

	public Sender() throws IOException{
		this(DFLT_PORT);
	}
	public Sender(int portNumber) throws IOException{
		portNumber 		= portNumber;
		socket 			= new ServerSocket(portNumber);
		client 			= socket.accept();
		outToClient 	= new PrintWriter(client.getOutputStream(), true);
		inFromClient 	= new BufferedReader(new InputStreamReader(client.getInputStream()));
	}
	// getter methods
	public int 				getPortNumber()		{return portNumber;}
	public BufferedReader 	getClientReader()	{return inFromClient;}
	public ServerSocket 	getServerSocket()	{return socket;}
	public Socket 			getClient()			{return client;}
	public int 				getBaud() 			{return baud;}


	// setter methods
	public void setPortNumber	(int n)			{portNumber = n;}
	public void setBaud			(int newBaud) 	{baud 		= newBaud;}


	public void sendToClient(String data) throws IOException{
		outToClient.println(data);
	}
	public String getLine() throws IOException{
		return inFromClient.readLine();
	}
	public boolean sendMessageToArduino(byte[] bytes) throws SerialPortException { 
		boolean retval = false;
		if(retval = port.isOpened())
 			port.writeBytes(bytes); 	// 

 		return retval;
 	} 
	public boolean findArduinoPort() throws SerialPortException, SerialPortTimeoutException, InterruptedException{ 
		// For each attached serial port 
		for(String s: SerialPortList.getPortNames()) { 
			SerialPort sp = new SerialPort(s); 
			 
			sp.openPort();
			sp.setDTR(false);
			port = sp;
			sp.closePort();
			return true;

			// catch(SerialPortException e) {
			// 	e.printStackTrace();
			// 	continue;
			// }catch(SerialPortTimeoutException e) {
			// 	try{sp.closePort();
			// 	}catch(Exception e1){e1.printStackTrace();}
			// 	continue;
			// }catch (InterruptedException e) { 
			// 	e.printStackTrace(); 
			// }

		}
		return (port == null);
	}
	public boolean beginArduinoComm() throws SerialPortException{
		return beginArduinoComm(this.baud);
	}
	public boolean beginArduinoComm(int baudRate) throws SerialPortException{
		if(port == null)
			return false;

		port.setParams(baudRate, 8, 1, 0);
		if(!port.isOpened())
			port.openPort();

		return port.isOpened();
	}

	
}
