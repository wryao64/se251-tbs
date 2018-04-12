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
	}
	
	public String getID() {
		return _theatreID;
	}
	
	public int getDimensions() {
		return _dimensions;
	}
}
