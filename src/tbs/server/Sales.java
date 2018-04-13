package tbs.server;

public class Sales {
	private String _performanceID;
	private String _startTimeStr;
	private int _premiumPrice;
	private int _cheapPrice;
	private int _premTicketsSold = 0;
	private int _cheapTicketsSold = 0;
	
	public Sales(String performanceID, String startTimeStr, String premiumPriceStr, String cheapPriceStr) {
		_performanceID = performanceID;
		_startTimeStr = startTimeStr;
		_premiumPrice = Integer.parseInt(premiumPriceStr.substring(1));
		_cheapPrice = Integer.parseInt(cheapPriceStr.substring(1));
	}
	
	public void ticketSold(int rowNumber, double premSeatRows) {
		if (rowNumber <= premSeatRows) {
			_premTicketsSold++;
		} else {
			_cheapTicketsSold++;
		}
	}
	
	private String calculatePriceOfTicketsSold() {
		String dollar = "$";
		int totalPrice = _premTicketsSold * _premiumPrice + _cheapTicketsSold * _cheapPrice;
		
		return dollar + totalPrice;
	}
	
	public String performanceDetails() {
		int ticketsSold = _premTicketsSold + _cheapTicketsSold;
		String totalPrice = calculatePriceOfTicketsSold();

		return _performanceID + "\t" + _startTimeStr + "\t" + ticketsSold + "\t" + totalPrice;
	}
}
