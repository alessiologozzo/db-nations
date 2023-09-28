package db_nations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class Server {
	private final String localHost = "jdbc:mysql://localhost:";
	private String username, password, port, database;
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public Server(String username, String password, String port, String database) {
		this.username = username;
		this.password = password;
		this.port = port;
		this.database = database;

		start();
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getPort() {
		return port;
	}

	public String getDatabase() {
		return database;
	}

	public void resetServer(String username, String password, String port, String database) {
		this.username = username;
		this.password = password;
		this.port = port;
		this.database = database;

		stop();
		start();
	}

	public void start() {
		try {
			connection = DriverManager.getConnection((localHost + port + "/" + database), username, password);
		} catch (Exception e) {
			System.err.println("Errore durante l'apertura della connessione.");
		}
	}

	public void stop() {
		try {
			connection.close();
		} catch (Exception e) {
			System.err.println("Errore durante la chiusura della connessione.");
		}
	}

	public ResultSet executeQuery(String query) {
		try {
			if (preparedStatement != null)
				if (!preparedStatement.isClosed())
					preparedStatement.close();

			if (resultSet != null)
				if (!resultSet.isClosed())
					resultSet.close();

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
		} catch (Exception e) {
			System.err.println("Errore durante l'esecuzione della query.");
		}

		return resultSet;
	}

	public ResultSet executeParameterizedQuery(String query, List<String> parameters) {
		try {
			if (preparedStatement != null)
				if (!preparedStatement.isClosed())
					preparedStatement.close();

			if (resultSet != null)
				if (!resultSet.isClosed())
					resultSet.close();

			preparedStatement = connection.prepareStatement(query);
			for (int i = 0; i < parameters.size(); i++)
				preparedStatement.setString(i + 1, parameters.get(i));

			resultSet = preparedStatement.executeQuery();
		} catch (Exception e) {
			System.err.println("Errore durante l'esecuzione della query.");
		}

		return resultSet;
	}
}
