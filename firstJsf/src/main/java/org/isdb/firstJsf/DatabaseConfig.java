package org.isdb.firstJsf;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConfig {

	static final String URL = "jdbc:oracle:thin:@//localhost:1521/ORCLPDB"; // SID
	static final String USERNAME = "orclpdbuser";
	static final String PASSWORD = "isdb62";

	public static Connection getConnection() throws Exception {
		Class.forName("oracle.jdbc.OracleDriver");
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
}
