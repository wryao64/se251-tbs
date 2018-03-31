package tbs.server;

public class Ticket {
	private static int ticketCount = 0;
	private String _ticketID;
	private int _rowNumber;
	private int _seatNumber;
	private String _performanceID;
	
	
	public Ticket(String performanceID, int rowNumber, int seatNumber) {
		_ticketID = "TKT" + ++ticketCount;
		_rowNumber = rowNumber;
		_seatNumber = seatNumber;
		_performanceID = performanceID;
	}
}
