
public class BorrowedTuple {
	
	private String MemberID;
	private String ISBN;
	private String Checkout_date;
	private String Checkin_date;

	public BorrowedTuple(String ID, String isbn, String out, String in) {
		this.MemberID = ID;
		this.ISBN = isbn;
		this.Checkout_date = out;
		this.Checkin_date = in;		
	}
	
	public String getID() { return MemberID; }
	public String getISBN() { return ISBN; }
	public String getCheckout_date() { return Checkout_date; }
	public String getCheckin_date() { return Checkin_date; }
	
	public String toString() {
		return ("MemberID: " + MemberID + 
				"\nISBN: " + ISBN +
				"\nCheckout_date: " + Checkout_date +
				"\nCheckin_date: " + Checkin_date +
				"\n");		
	}
}