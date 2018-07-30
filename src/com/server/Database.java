package com.server;

import java.sql.Connection;

public interface Database {

	public Connection getConnection() throws Exception;
	public void createTable();
	public void closeConnection();
	public int countTable();
	public void getTable();
	public void dropTable();
	void insertTable(int id, String name, String path, long size);
	
}
