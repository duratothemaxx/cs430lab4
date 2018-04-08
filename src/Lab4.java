import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Lab4 {

	public void sqlConnect() {

		Connection con = null;

		try {
			Statement stmt;
			ResultSet rs;

			// Register the JDBC driver for MySQL.
			Class.forName("com.mysql.jdbc.Driver");

			// Define URL of database server for
			// database named 'user' on the faure.
			String url = "jdbc:mysql://localhost:18081/jcedward";

			// Get a connection to the database for a
			// user named 'user' with the password
			// 123456789.
			con = DriverManager.getConnection(url, "jcedward", "830594668");

			// Display URL and connection information
			System.out.println("URL: " + url);
			System.out.println("Connection: " + con);

			// Get a Statement object
			stmt = con.createStatement();

			try {
				rs = stmt.executeQuery("SELECT * FROM author");
				while (rs.next()) {
					System.out.println(rs.getString("authorID") + " " + rs.getString("last_name") + ", "
							+ rs.getString("first_name"));
				}
			} catch (Exception e) {
				System.out.print(e);
				System.out.println("No Author table to query");
			} // end catch

			con.close();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	


	

	public static void main(String args[]) {
		
		Lab4 lab4 = new Lab4();
		ReadXML reader = new ReadXML();
		lab4.sqlConnect();
		reader.readXML(args[0]);
		reader.printBooks();

	}// end main

}
