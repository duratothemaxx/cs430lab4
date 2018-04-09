package lab4;

import java.sql.*;
import java.util.ArrayList;

public class Lab4 {
	
	private SQLStatementBuilder builder;
	private Connection con;
	
	public Lab4() {
		builder = new SQLStatementBuilder();		
	}
	
	public String sqlConnect(String query, int type) {

		con = null;

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
			// System.out.println("URL: " + url);
			// System.out.println("Connection: " + con);

			// Get a Statement object
			stmt = con.createStatement();

			// query if book exists
			if (type == 1) {
				try {
					rs = stmt.executeQuery(query);
					rs.next();
					return (rs.getString("b.ISBN"));
				} catch (Exception e) {
					System.err.println(e);
					System.err.println("ISBN does not exist");
				}
			}
			// query if a checkout record exists
			else if (type == 2) {
				try {
					rs = stmt.executeQuery(query);
					rs.next();
					return rs.getString("b.ISBN") + ", " + rs.getString("b.MemberID");
				} catch (Exception e) {
					System.err.println(e);
					System.err.println("No book checkout record");
				}
			}
			// query stored on table for number of copies
			else if (type == 3) {
				try {
					rs = stmt.executeQuery(query);
					rs.next();
					return rs.getString("s.copies");
				} catch (Exception e) {
					System.err.println(e);
					System.err.println("no copies of book exist");
				}
			}
			// insert into borrowed
			else if (type == 4) {
				return "" + stmt.executeUpdate(query);

			}
			// update borrowed, checkin record
			else if (type == 5) {
				return "" + stmt.executeUpdate(query);
			} else {
				// nothing
				return "";
			}

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
			//System.out.println("Book exist query: " + bookExistQuery);

			System.out.println("Record in question");
			System.out.println(b);
			
			if(!sqlConnect(bookExistQuery,1).isEmpty()) {
				
				System.out.flush();
				System.err.flush();
				//System.out.println("Book Exists");
				// book exists, now see if the record is a checkin or checkout
				if(b.getCheckin_date().equals("N/A")) {
					// ok its a checkout record
					// we know the book exists, so update the records
					// create new record in the borrowed table
					// update the stored_on.copies record (-1)
					// also check if the number of copies is 0,
					// in which case you won't be able to checkout the book
					//System.out.println(" Checkout Record");
					String queryCopies = builder.numCopiesQuery(b);
					//System.out.println(" Copies query: " + queryCopies);
					String numCopies = sqlConnect(queryCopies, 3);
					//System.out.println(" number of copies: " + numCopies);
					if(numCopies.isEmpty()) {
						System.err.println("No copies of book in library, can't process record...");
						continue;
					}
					//System.out.println(" insert into borrowed query:");
					String insertIntoBorrowed = builder.createInsertStatement(b);
					//System.out.println(" " + insertIntoBorrowed);
					if(sqlConnect(insertIntoBorrowed, 4).equals("1"))
						System.out.println("Checkout record created successfully");
					
				}				
				else {
					// ok its a checkin record, we must check to see
					// if the corresponding checkout record exists
					// check for ISBN and MemberID
					//System.out.println(" Checkin Record");
					//String verifyCheckout = builder.verifyChekout_dateQuery(b);
					//System.out.println(" " + verifyCheckout);
					//System.out.println(" verified book: " + sqlConnect(verifyCheckout, 2));
					String returnBookStmt = builder.updateCheckinRecord(b);
					//System.out.println(" Checkin update stmt: " + returnBookStmt);
					int result = Integer.parseInt(sqlConnect(returnBookStmt, 5));
					if(result == 1)
						System.out.println("Checkin record update successfully");
					if(result == 0)
						System.out.println("No record updated");
				}				
			}		
			System.out.println("----------------------------------------------");
		}
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	

	public static void main(String args[]) {		
		Lab4 lab4 = new Lab4();
		ReadXML reader = new ReadXML();
		reader.readXML(args[0]);
		//reader.printBooks();
		lab4.updateBooks(reader.getBorrowedBooksList());
	}
}
