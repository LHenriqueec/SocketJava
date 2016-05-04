package model;

import java.io.Serializable;
import java.util.function.Consumer;

public class Client extends NetworkConnection {
	
	private String host;
	private int port;
	
	public Client(String host, int port, Consumer<Serializable> consumer) {
		super(consumer);
		this.host = host;
		this.port = port;
	}

	@Override
	protected boolean isServer() {
		return false;
	}

	@Override
	protected String getHost() {
		return host;
	}

	@Override
	protected int getPort() {
		return port;
	}
	
}
