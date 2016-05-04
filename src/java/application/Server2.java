package application;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {

	public static void main(String[] args) throws Exception {
		
		
		
			try (ServerSocket server = new ServerSocket(5555);
					Socket socket = server.accept()) {
				
				
				System.out.println(socket.getLocalAddress());
				
			
		}

	}

}
