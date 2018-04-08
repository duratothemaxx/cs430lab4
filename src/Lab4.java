import java.sql.*;
import java.util.ArrayList;

public class Lab4 {
	
	private SQLStatementBuilder builder;
	
	public Lab4() {
		builder = new SQLStatementBuilder();		
	}

	public String sqlConnect(String query, int type) {

		Connection con = null;

		try {
			Statement stmt;
			ResultSet rs;

			// Register the JDBC driver for MySQL.
			Class.forName("com.mysql.jdbc.Driver");

			// Define URL of database server for
			// database named 'user' on the faure.
			String url = "jdbc:mysql://localhost:18081/jcedward";

			// Get a connection to the database
			con = DriverManager.getConnection(url, "jcedward", "830594668");

			// Display URL and connection information
			//System.out.println("URL: " + url);
			//System.out.println("Connection: " + con);

			// Get a Statement object
			stmt = con.createStatement();
			
			// query if book exists
			if (type == 1) {
				try {
					rs = stmt.executeQuery(query);
					rs.next();
					return(rs.getString("b.ISBN"));
				} catch (Exception e) {
					System.out.print(e);
					System.out.println("ISBN does not exist");
				}				
			}
			// query if a checkout record exists
			else if (type == 2) {
				try {
					rs = stmt.executeQuery(query);
					rs.next();
					return rs.getString("b.ISBN") + rs.getString("b.MemberID");					
				} catch (Exception e) {
					System.out.print(e);
					System.out.println("No book checkout record");
				}	
				
				

				
			}
			else {
				// nothing
				return "";
			}
			


			con.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
		
		return "";
	}
	
	public void updateBooks(ArrayList<BorrowedTuple> books) {
		
		// we now have all the books read in from the XML file
		// one by one we need to update the borrowed table
		
		
		
		for (BorrowedTuple b : books) {
			// first we need to check that the book exists
			String bookExistQuery = builder.bookExistQuery(b);

			System.out.println(bookExistQuery);
			
			if(!sqlConnect(bookExistQuery,1).isEmpty()) {
				
				System.out.println("Book Exists");
				// book exists, now see if the record is a checkin or checkout
				if(b.getCheckin_date().equals("N/A")) {
					
				}				
				else {
					// ok its a checkin record, we must check to see
					// if the corresponding checkout record exists
					// check for ISBN and MemberID
					String verifyCheckout = builder.verifyChekout_dateQuery(b);
					System.out.println(" " + verifyCheckout);
					System.out.println(sqlConnect(verifyCheckout, 2));					
				}
				
			}
			else {
				System.err.println("Book does not exit, moving on to next record");
			}
			
			
			
			
			
			
			
			
			
			
		}
		
		
		
		
	}
	


	

	public static void main(String args[]) {
		
		Lab4 lab4 = new Lab4();
		ReadXML reader = new ReadXML();
		reader.readXML(args[0]);
		//reader.printBooks();
		lab4.updateBooks(reader.getBorrowedBooksList());

	}// end main

}
