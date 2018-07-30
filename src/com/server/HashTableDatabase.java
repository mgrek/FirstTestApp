package com.server;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.main.Logs;

public class HashTableDatabase implements Database {

	private Connection connection;
	private Logs log;
	private final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS FILE (ID INT, NAME VARCHAR(200), PATH VARCHAR(200), SIZE INT)";
	private final static String GET_TABLE = "SELECT * FROM FILE";
	private final static String DROP_TABLE = "DROP TABLE FILE";
	private final static String INSERT_INTO = "INSERT INTO FILE (ID, NAME, PATH, SIZE) VALUES (?, ?, ?, ?)";
	private final static String CLEAR_TABLE = "";
	private final static String CHECK_IF_TABLE_EXISTS = "select count(*) from INFORMATION_SCHEMA.TABLES where TABLE_NAME='USER'";

	public HashTableDatabase(Logs log) throws Exception {
		this.log = log;
		this.createConnection();
	}

	@Override
	public Connection getConnection() throws Exception {
		// TODO Auto-generated method stub
		if (connection == null)
			this.createConnection();
		return connection;
	}

	private void createConnection() throws Exception {
		try {
			connection = DriverManager.getConnection(
					"jdbc:h2:file:./logs/hashtable", "omenibaev", "12345aef31");
			log.debug("Connection create");
		} catch (SQLException ex) {
			log.error(ex.toString());
			throw new Exception(ex);
		}
	}

	@Override
	public void createTable() {
		// TODO Auto-generated method stub
		try (Statement sql = connection.createStatement()) {
			sql.execute(CREATE_TABLE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub
		if (connection != null)
			try {
				connection.close();
				log.debug("Connection close");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				log.error(e);
				e.printStackTrace();
			}
	}

	@Override
	public int countTable() {
		int count = -1;
		try (Statement sql = connection.createStatement()) {
			ResultSet rs = sql.executeQuery(CHECK_IF_TABLE_EXISTS);
			if (rs.next())
				count = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("Count of USER table " + count);
		return count;
	}

	@Override
	public void insertTable(int id, String name, String path, long size) {
		// TODO Auto-generated method stub
		try (CallableStatement st = connection.prepareCall(INSERT_INTO)) {
			st.setInt(1, id);
			st.setString(2, name);
			st.setString(3, path);
			st.setDouble(4, size);
			st.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void getTable() {
		try (Statement st = connection.createStatement()) {
			ResultSet rs = st.executeQuery(GET_TABLE);
			while (rs.next())
				log.info("ID: " + rs.getInt(1) + "; Name: " + rs.getString(2)
						+ "; Path: " + rs.getString(3) + "; Size: "
						+ rs.getLong(4));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void dropTable() {
		// TODO Auto-generated method stub
		try (Statement sql = connection.createStatement()) {
			sql.execute(DROP_TABLE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
