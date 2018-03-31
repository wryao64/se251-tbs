package tbs.server;

public class Artist {
	private String _name;
	private String _artistID;
	private static int artistCount = 0;
	
	public Artist(String name) {
		_name = name;
		_artistID = "A" + ++artistCount;
		//System.out.println(artistCount);
	}
	
	public String getName() {
		return _name;
	}
	
	public String getID() {
		return _artistID;
	}
}
