package tbs.server;

import java.util.List;
import java.util.Vector;

public class Performance {
	private String _performanceID;
	private String _actID;
	private String _theatreID;
	private String _startTimeStr;
	private String _premiumPriceStr;
	private String _cheapSeatsStr;
	private static int performanceCount = 0;
	private boolean[][] _seats;
	
	public Performance(String actID, String theatreID, String startTimeStr, 
			String premiumPriceStr, String cheapSeatsStr) {
		_performanceID = "P" + ++performanceCount;
		_actID = actID;
		_theatreID = theatreID;
		_startTimeStr = startTimeStr;
		_premiumPriceStr = premiumPriceStr;
		_cheapSeatsStr = cheapSeatsStr;
		//_seats = _theatreID.getSeats(theatreID);
	}
	
	public String getID() {
		return _performanceID;
	}
	
	public String getActID() {
		return _actID;
	}
	
	public List<String> checkAvailableSeats() {
		List<String> availableSeatList = new Vector<String>();
		
		return availableSeatList;
	}
}
