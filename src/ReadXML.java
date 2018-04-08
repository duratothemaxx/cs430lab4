import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadXML {

	private ArrayList<BorrowedTuple> borrowedBooks;

	public ReadXML() {
		borrowedBooks = new ArrayList<BorrowedTuple>();
	}

	public String formatDate(String date) {
		if (date.equals("N/A"))
			return "N/A";

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate fixedDate = LocalDate.parse(date, formatter);

		return fixedDate.toString();
	}

	public void printBooks() {
		for (BorrowedTuple b : borrowedBooks)
			System.out.println(b);
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
					// System.out.println("MemberID : " + ((Node)
					// memberIdNodeList.item(0)).getNodeValue().trim());

					NodeList secnoElementList = sectionNode.getElementsByTagName("ISBN");
					Element secnoElmnt = (Element) secnoElementList.item(0);
					NodeList secno = secnoElmnt.getChildNodes();
					// System.out.println("ISBN : " + ((Node) secno.item(0)).getNodeValue().trim());

					NodeList codateElementList = sectionNode.getElementsByTagName("Checkout_date");
					Element codElmnt = (Element) codateElementList.item(0);
					NodeList cod = codElmnt.getChildNodes();
					// System.out.println("Checkout_date : " + formatDate(((Node)
					// cod.item(0)).getNodeValue().trim()));

					NodeList cidateElementList = sectionNode.getElementsByTagName("Checkin_date");
					Element cidElmnt = (Element) cidateElementList.item(0);
					NodeList cid = cidElmnt.getChildNodes();
					// System.out.println("Checkin_date : " + formatDate(((Node)
					// cid.item(0)).getNodeValue().trim()));
					
					borrowedBooks.add(new BorrowedTuple(
							((Node) memberIdNodeList.item(0)).getNodeValue().trim().toString(),
							((Node) secno.item(0)).getNodeValue().trim().toString(),
							formatDate(((Node) cod.item(0)).getNodeValue().trim()),
							formatDate(((Node) cid.item(0)).getNodeValue().trim())
							));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
