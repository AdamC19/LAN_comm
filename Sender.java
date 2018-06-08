import java.net.*;
import java.io.*;
import jssc.*;


public class Sender {
	private static final int BEGIN 		= 0xA5;
	private static final int DFLT_PORT 	= 5555;
	private static int count 			= 0;
	private SerialPort port;
	private int portNumber;
	private ServerSocket socket;
	private Socket client;
	private PrintWriter out;
	private BufferedReader in;
	private int baud = 115200;

	public Sender() throws IOException{
		this(DFLT_PORT);
	}
	public Sender(int portNumber) throws IOException{
		portNumber 	= portNumber;
		socket 		= new ServerSocket(portNumber);
		client 		= socket.accept();
		out 		= new PrintWriter(client.getOutputStream(), true);
		in 			= new BufferedReader(new InputStreamReader(client.getInputStream()));
	}
	public int getPortNumber(){
		return portNumber;
	}
	public void setPortNumber(int n){
		portNumber = n;
	}
	public ServerSocket getServerSocket(){
		return socket;
	}
	public Socket getClient(){
		return client;
	}
	public void send(String data) throws IOException{
		out.println(data);
	}
	public BufferedReader getClientReader(){
		return in;
	}
	public String getLine() throws IOException{
		return in.readLine();
	}
	private boolean sendMessage(String s) throws SerialPortException { 
		boolean retval = false;
		if(retval = port.isOpened())
 			port.writeBytes(s.getBytes()); 	// 

 		return retval;
 	} 
	private void findArduinoPort(){ 
		// For each attached serial port 
		for(String s: SerialPortList.getPortNames()) { 
			SerialPort sp = new SerialPort(s); 
			try { 
				// Open the port 
				sp.openPort(); 
				sp.setParams(baud, 8, 1, 0);
				sp.setDTR(false);
				port = sp;

			}catch(SerialPortException e) {
				e.printStackTrace();
				continue;
			}catch(SerialPortTimeoutException e) {
				try{sp.closePort();
				}catch(Exception e1){e1.printStackTrace();}
				continue;
			}catch (InterruptedException e) { 
				e.printStackTrace(); 
			}

		}
		if(port == null)
			System.out.println("Couldn't find Arduino :(");
	}



	public static void main(String[] args) throws IOException {

		if (args.length != 1) {
			System.err.println("Usage: java Sender <port number>");
			System.exit(1);
		}

		int portNumber 	= Integer.parseInt(args[0]);
		Sender sender 	= new Sender(portNumber);

		try {
			String inputLine;
			boolean begin = false;
			while(!begin){
			    inputLine   = sender.getLine(); //getClientReader().readLine();
			    begin       = inputLine.equals(String.valueOf(BEGIN));
			}
			for(count = 0; count < 50; count++){
			    sender.send("Number " + count);
			}
			sender.send("DONE");
		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port "
		    + portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}
}
