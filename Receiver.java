import java.net.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;


public class Receiver extends Object{

	private static String defaultDirPath 	= "C:\\Users\\acordingley\\Documents\\";
	private static String defaultFilePath	= defaultDirPath + "data_output.txt";
	private static final int BEGIN 			= 0xA5;

	public static void main(String args[]){
		String hostName = args[0];
		int portNumber 	= Integer.parseInt(args[1]);
		File dataOutFile;
		PrintWriter dataOut;
		
		try(
			Socket sender	 	= new Socket(hostName, portNumber);
			PrintWriter out 	= new PrintWriter(sender.getOutputStream(), true); 						// write to the socket server
			BufferedReader in 	= new BufferedReader(new InputStreamReader(sender.getInputStream()));	// input from socket server
			BufferedReader stdIn= new BufferedReader(new InputStreamReader(System.in)); 				// input from client console
		){
			System.out.println("Connected to " + hostName + " on port "+ portNumber);

			// create a file for our data
			JFileChooser chooser = new JFileChooser(new File(defaultDirPath));
			int retval = chooser.showSaveDialog(null);
			if(retval == chooser.APPROVE_OPTION){
				dataOutFile = chooser.getSelectedFile();
			}else{
				dataOutFile = new File(defaultFilePath);
			}
			dataOut = new PrintWriter(dataOutFile);

			String userInput;
			String received;
			boolean ok = true;
			out.println(BEGIN); 		// send command to begin
			while( ok ){
				if( (received = in.readLine()) != null ) 
					System.out.println("received: " + received); 	// 
			}

		}catch(HeadlessException e){
			System.out.println("Headless Exception :(");
			System.out.println("File was instead created at "+ defaultFilePath);
		}catch(FileNotFoundException e){
			System.out.println("Could not open the file :(");
		}catch(SecurityException e){
			System.out.println("Access to the file was denied by security manager :(");
		}catch (Exception e) {
			System.out.println("Error occurred :(");
			e.printStackTrace();
		}
		
	}
}