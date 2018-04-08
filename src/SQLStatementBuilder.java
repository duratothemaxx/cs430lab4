
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
	
	public String createInsertStatement(BorrowedTuple b) {
		
		
		
		
		
		return "";
	}

}
