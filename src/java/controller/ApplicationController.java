package controller;

import java.io.Serializable;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Client;
import model.NetworkConnection;
import model.Server;

public class ApplicationController {
	 private boolean isServer = true;
//	private boolean isServer = false;
	
	private NetworkConnection server;
	private Consumer<Serializable> consumer;

	@FXML
	private Label lblStatus;
	@FXML
	private TextField txtMessage;
	@FXML
	private TextArea txaMessages;

	@FXML
	private void initialize() {
		consumer = message -> txaMessages.appendText(message.toString() + "\n");
		
		server = isServer ? new Server(5555, consumer) : new Client("localhost", 5555 , consumer);
		
		lblStatus.textProperty().bind(server.statusProperty());
		
	}

	@FXML
	private void onStart() {
		server.startConnection();
	}

	@FXML
	private void onSend() {
		try {
			server.sendData(txtMessage.getText());
			txtMessage.setText("");
		} catch (Exception e) {
			txaMessages.appendText("Falha ao enviar mensagem...\n" + "Erro: " + e.getMessage() + "\n\n");
			txtMessage.setText("");
		}
	}
}
