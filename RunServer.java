// package LAN_comm;
import java.io.*;

public class RunServer{
	private static int 	waitForBegin 	= 250;
	private static long time 			= 0;
	private static int 	iterations 		= 100;

	public static void main(String[] args) throws IOException, InterruptedException{

		if (args.length != 1) {
			System.err.println("Usage: java Sender <port number>");
			System.exit(1);
		}

		int portNumber 	= Integer.parseInt(args[0]); 	// 
		Sender sender 	= new Sender(portNumber); 		// create the sender


		try {
			String inputLine;
			boolean begin 	= false;
			boolean done 	= false;
			while(!done){
			// wait for client to send a BEGIN command
				while(!begin){
					time 		= System.currentTimeMillis();
				    if(  ((time/1000) % 3) == 0)
				    	System.out.println("Still waiting for client...");
				    
				    inputLine   = sender.getLine(); //getClientReader().readLine();
				    begin       = inputLine.equals(String.valueOf(Commands.BEGIN));
				    Thread.sleep(waitForBegin);

				}
				for(int count = 0; count < iterations; count++){
					time 		= System.currentTimeMillis();
				    sender.sendToClient("Time\t: " + time);
				    Thread.sleep(500);
				}
				sender.sendToClient("DONE");
				begin = false; 	// reset
			}
		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port "
		    + portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}