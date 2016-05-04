package model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public abstract class NetworkConnection {

	private Connection conn;
	private StringProperty status;
	private Consumer<Serializable> consumer;

	public NetworkConnection(Consumer<Serializable> consumer) {
		this.consumer = consumer;
		status = new SimpleStringProperty();
		conn = new Connection();
	}

	public void startConnection() {
		conn.restart();
	}

	public void sendData(Serializable message) throws Exception {
		if (isServer()) {
			conn.out.writeUTF("Server: " + message.toString());
		} else {
			conn.out.writeUTF("Client: " + message.toString());
		}
		
		conn.out.flush();
	}

	public StringProperty statusProperty() {
		return status;
	}

	abstract boolean isServer();

	abstract String getHost();

	abstract int getPort();

	// Classe que implementa uma nova Conexão
	private class Connection extends Service<Void> {

		private ObjectOutputStream out;
		private Socket socket;

		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {
				{
					status.bind(messageProperty());
					updateMessage("Offline");
				}

				@Override
				protected Void call() throws Exception {
					try (ServerSocket server = isServer() ? new ServerSocket(getPort()) : null) {
						Socket socketServer = isServer() ? server.accept() : new Socket(getHost(), getPort());
						ObjectOutputStream output = new ObjectOutputStream(socketServer.getOutputStream());
						ObjectInputStream in = new ObjectInputStream(socketServer.getInputStream());

						socketServer.setTcpNoDelay(true);

						out = output;
						socket = socketServer;
						sendData("Conectado...");

						while (true) {
							consumer.accept(in.readUTF());
						}
					}
				}

				@Override
				protected void running() {
					if (isServer()) {
						updateMessage("Server: Online ");
					} else {
						updateMessage("Client: online");
					}
				}
				
				@Override
				protected void finalize() throws Throwable {
					socket.close();
				}
			};
		}

	}
}
