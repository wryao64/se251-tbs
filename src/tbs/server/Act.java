package tbs.server;

public class Act {
	private String _actID;
	private String _title;
	private String _artistID;
	private int _minutesDuration;
	private static int actCount = 0;
	
	public Act(String title, String artistID, int minutesDuration) {
		_actID = "ACT" + ++actCount;
		_title = title;
		_artistID = artistID;
		_minutesDuration = minutesDuration;
	}
	
	public String getID() {
		return _actID;
	}
}
