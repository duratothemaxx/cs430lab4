import java.io.File;
import java.sql.*;

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

	public void readXML(String fileName) {
		try {
			File file = new File(fileName);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nodeLst = doc.getElementsByTagName("Borrowed_by");

			for (int s = 0; s < nodeLst.getLength(); s++) {

				Node fstNode = nodeLst.item(s);

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					Element sectionNode = (Element) fstNode;

					NodeList memberIdElementList = sectionNode.getElementsByTagName("MemberID");
					Element memberIdElmnt = (Element) memberIdElementList.item(0);
					NodeList memberIdNodeList = memberIdElmnt.getChildNodes();
					System.out.println("MemberID : " + ((Node) memberIdNodeList.item(0)).getNodeValue().trim());

					NodeList secnoElementList = sectionNode.getElementsByTagName("ISBN");
					Element secnoElmnt = (Element) secnoElementList.item(0);
					NodeList secno = secnoElmnt.getChildNodes();
					System.out.println("ISBN : " + ((Node) secno.item(0)).getNodeValue().trim());

					NodeList codateElementList = sectionNode.getElementsByTagName("Checkout_date");
					Element codElmnt = (Element) codateElementList.item(0);
					NodeList cod = codElmnt.getChildNodes();
					System.out.println("Checkout_date : " + ((Node) cod.item(0)).getNodeValue().trim());

					NodeList cidateElementList = sectionNode.getElementsByTagName("Checkin_date");
					Element cidElmnt = (Element) cidateElementList.item(0);
					NodeList cid = cidElmnt.getChildNodes();
					System.out.println("Checkin_date : " + ((Node) cid.item(0)).getNodeValue().trim());

					System.out.println();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		
		Lab4 lab4 = new Lab4();
		lab4.sqlConnect();
		lab4.readXML(args[0]);

	}// end main

}
