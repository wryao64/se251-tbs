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
			for (int col = 0; col < _seats.length; col++) {
				_seats[row][col] = true;
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
