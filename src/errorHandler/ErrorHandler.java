package errorHandler;

import javax.swing.JOptionPane;

public class ErrorHandler {
	
	public static void handle(Errors e) {
		JOptionPane.showMessageDialog(null, message(e), "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	private static String message(Errors e) {
		if(e ==  Errors.SQL) return "INVALID DATA";
		else if(e == Errors.DBS) return "CAN'T CONNECT TO DATABASE";
		else if(e == Errors.SORT) return "previse sortiranja";
		return null;
	}
}
