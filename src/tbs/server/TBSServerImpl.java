package tbs.server;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TBSServerImpl implements TBSServer {
	public String initialise(String path) {
		try {
			BufferedReader theatreData = new BufferedReader(new FileReader(path));
			
			// read data here
			
			return "";
		} catch (FileNotFoundException e) {
			return "ERROR File Not Found";
		} catch (IOException e) {
			return "ERROR IO Exception";
		}
	}

	public List<String> getTheatreIDs() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getArtistIDs() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getArtistNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getActIDsForArtist(String artistID) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getPeformanceIDsForAct(String actID) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getTicketIDsForPerformance(String performanceID) {
		// TODO Auto-generated method stub
		return null;
	}

	public String addArtist(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public String addAct(String title, String artistID, int minutesDuration) {
		// TODO Auto-generated method stub
		return null;
	}

	public String schedulePerformance(String actID, String theatreID, String startTimeStr, String premiumPriceStr,
			String cheapSeatsStr) {
		// TODO Auto-generated method stub
		return null;
	}

	public String issueTicket(String performanceID, int rowNumber, int seatNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> seatsAvailable(String performanceID) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> salesReport(String actID) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> dump() {
		// TODO Auto-generated method stub
		return null;
	}
}
