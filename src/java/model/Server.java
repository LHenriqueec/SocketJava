package model;

import java.io.Serializable;
import java.util.function.Consumer;

public class Server extends NetworkConnection {
	

	private String host;
	private int port;
	
	public Server(int port, Consumer<Serializable> consumer) {
		super(consumer);
		this.port = port;
	}
	
	@Override
	protected boolean isServer() {
		return true;
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
