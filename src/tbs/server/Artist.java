package tbs.server;

public class Artist {
	private String _name;
	private String _artistID;
	private static int artistCount = 0;
	
	public Artist(String name) {
		_name = name;
		_artistID = "" + ++artistCount;
		//System.out.println(artistCount);
	}
	
	public String getID() {
		return this._artistID;
	}
}
