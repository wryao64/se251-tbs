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
	private int _premTicketsSold = 0;
	private int _cheapTicketsSold = 0;
	
	public Performance(String actID, String theatreID, String startTimeStr, 
			String premiumPriceStr, String cheapSeatsStr) {
		_performanceID = "P" + ++performanceCount;
		_actID = actID;
		_theatreID = theatreID;
		_startTimeStr = startTimeStr;
		_premiumPriceStr = premiumPriceStr;
		_cheapSeatsStr = cheapSeatsStr;
	}
	
	public void setSeats(Theatre theatre) {
		_seats = theatre.getSeats();
	}
	
	public String getID() {
		return _performanceID;
	}
	
	public String getActID() {
		return _actID;
	}
	
	public List<String> checkAvailableSeats() {
		List<String> availableSeatList = new Vector<String>();
		
		for (int row = 0; row < _seats.length; row++) {
			for (int seatPos = 0; seatPos < _seats.length; seatPos++) {
				if (_seats[row][seatPos] == true) {
					availableSeatList.add((row + 1) + "\t" + (seatPos + 1));
				}
			}
		}
		
		return availableSeatList;
	}
	
	public void seatSold(int rowNumber, int seatNumber) {
		_seats[rowNumber - 1][seatNumber - 1] = false;
		
		double premSeatRows = Math.floor(_seats.length / 2);
		
		if (rowNumber <= premSeatRows) {
			_premTicketsSold++;
		} else {
			_cheapTicketsSold++;
		}
	}
	
	public String calculatePriceOfTicketsSold() {
		String price = "$";
		
		int premiumPrice = Integer.parseInt(_premiumPriceStr.substring(1));
		int cheapPrice = Integer.parseInt(_cheapSeatsStr.substring(1));
		
		int totalPrice = _premTicketsSold * premiumPrice + _cheapTicketsSold * cheapPrice;
		
		return price + totalPrice;
	}
	
	public String performanceDetails() {
		int ticketsSold = _premTicketsSold + _cheapTicketsSold;
		
		String totalPrice = calculatePriceOfTicketsSold();

		return _performanceID + "\t" + _startTimeStr + "\t" + ticketsSold + "\t" + totalPrice;
	}
}
