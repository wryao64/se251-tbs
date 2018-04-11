package tbs.server;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class Theatre {
	private String _theatreID;
	private int _dimensions;
	private int _area;
	private boolean[][] _seats;
	
	public Theatre(String theatreID, int dimensions, int area) {
		_theatreID = theatreID;
		_dimensions = dimensions;
		_area = area;
		_seats = new boolean[dimensions][dimensions];
		for (int row = 0; row < _seats.length; row++) {
			for (int col = 0; col < _seats.length; col++) {
				_seats[row][col] = true;
			}
		}
	}
	
	public boolean[][] getSeats(String theatreID) {
		return _seats;
	}
	
	public String getID() {
		return _theatreID;
	}
}
