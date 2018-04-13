package tbs.server;

import java.util.List;
import java.util.Vector;

public class Performance {
	private static int performanceCount = 0;
	private String _performanceID;
	private String _actID;
	private String _theatreID;
	private String _startTimeStr;
	private String _premiumPriceStr;
	private String _cheapSeatsStr;
	private boolean[][] _seats;
	private Sales _sales;
	
	public Performance(String actID, String theatreID, Integer theatreDim, String startTimeStr, 
			String premiumPriceStr, String cheapSeatsStr) {
		_performanceID = "P" + ++performanceCount;
		_actID = actID;
		_theatreID = theatreID;
		_startTimeStr = startTimeStr;
		_premiumPriceStr = premiumPriceStr;
		_cheapSeatsStr = cheapSeatsStr;
		_seats = new boolean[theatreDim][theatreDim];
		this.setSeats();
		_sales = new Sales(_performanceID, _startTimeStr, _premiumPriceStr, _cheapSeatsStr);
	}
	
	private void setSeats() {
		for (int row = 0; row < _seats.length; row++) {
			for (int seatPos = 0; seatPos < _seats.length; seatPos++) {
				// all seats are available (true)
				_seats[row][seatPos] = true;
			}
		}
	}
	
	public String getPerformanceID() {
		return _performanceID;
	}
	
	public String getActID() {
		return _actID;
	}
	
	public Sales getSales() {
		return _sales;
	}
	
	public boolean checkIfSeatExists(int rowNumber, int seatNumber) {
		// rowNumber/seatNumber corresponds to that number - 1 as arrays start at 0,
		// but rows/seats start at 1
		if ((rowNumber - 1) >= _seats.length || (rowNumber - 1) < 0 || 
				(seatNumber - 1) >= _seats.length || (seatNumber - 1) < 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean checkIfSeatIsAvailable(int rowNumber, int seatNumber) {
		// rowNumber/seatNumber corresponds to that number - 1 as arrays start at 0,
		// but rows/seats start at 1
		if (_seats[rowNumber - 1][seatNumber - 1] == false) {
			return false;
		} else {
			return true;
		}
	}
	
	public List<String> findAvailableSeats() {
		List<String> availableSeatList = new Vector<String>();
		
		// finds all seats that are available
		for (int row = 0; row < _seats.length; row++) {
			for (int seatPos = 0; seatPos < _seats.length; seatPos++) {
				if (_seats[row][seatPos] == true) {
					availableSeatList.add((row + 1) + "\t" + (seatPos + 1));
				}
			}
		}
		
		return availableSeatList;
	}
	
	public Ticket newTicket(String ticketID, int rowNumber, int seatNumber) {
		return new Ticket(ticketID, rowNumber, seatNumber);
	}
	
	public class Ticket {
		private String _ticketID;
		private int _rowNumber;
		private int _seatNumber;
		
		public Ticket(String ticketID, int rowNumber, int seatNumber) {
			_ticketID = ticketID;
			_rowNumber = rowNumber;
			_seatNumber = seatNumber;
			seatSold(_rowNumber, _seatNumber);
		}
		
		public String getPerformanceID() {
			return _performanceID;
		}
		
		public String getTicketID() {
			return _ticketID;
		}
		
		private void seatSold(int rowNumber, int seatNumber) {
			_seats[rowNumber - 1][seatNumber - 1] = false;
			
			// premium seats are in the front half of the theatre
			double premSeatRows = Math.floor(_seats.length / 2);
			
			_sales.ticketSold(rowNumber, premSeatRows);
		}
	}
}