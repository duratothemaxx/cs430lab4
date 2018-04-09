package lab4;

public class SQLStatementBuilder {

	public SQLStatementBuilder() {		
	}
	
	public String bookExistQuery(BorrowedTuple b) {		
		
		return "SELECT * FROM book b where b.ISBN='" + b.getISBN() + "';";
	}
	
	public String verifyChekout_dateQuery(BorrowedTuple b) {		
		return "SELECT * from borrowed b where b.ISBN='" 
		+ b.getISBN() + "' and b.MemberID='" + b.getID() + "';";
	}
	
	public String numCopiesQuery(BorrowedTuple b) {
		return "SELECT * FROM stored_on s where s.ISBN='"
				+ b.getISBN() + "';";
	}
	
	public String decrementCopiesStatement(BorrowedTuple b) {
		return "UPDATE stored_on s SET s.copies=s.copies-1 "
				+ "and s.ISBN='" + b.getISBN() + "';";
	}
	
	public String createInsertStatement(BorrowedTuple b) {
		return "INSERT INTO borrowed (MemberID,ISBN,Checkout_date) VALUES('"
				+ b.getID() + "','" + b.getISBN() + "','"
				+ b.getCheckout_date() + "');";
	}
	
	public String updateCheckinRecord(BorrowedTuple b) {
		return "UPDATE borrowed b set b.Checkin_date='"
				+ b.getCheckin_date() + "' WHERE b.ISBN='"
				+ b.getISBN() + "' and b.MemberID='"
				+ b.getID() + "' and b.Checkin_date IS NULL;";
	}
}
