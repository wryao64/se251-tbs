package tbs.server;

public class Theatre {
	private String _theatreID;
	private int _dimensions;
	private int _area;
	private boolean[][] _seats;
	
	public Theatre(String theatreID, int dimensions, int area) {
		_theatreID = theatreID;
		_dimensions = dimensions;
		_area = area;
		initialiseSeats();
	}
	
	private void initialiseSeats() {
		_seats = new boolean[_dimensions][_dimensions];
		for (int row = 0; row < _seats.length; row++) {
			for (int seatPos = 0; seatPos < _seats.length; seatPos++) {
				_seats[row][seatPos] = true;
			}
		}
	}
	
	public boolean[][] getSeats() {
		return _seats;
	}
	
	public String getID() {
		return _theatreID;
	}
}
