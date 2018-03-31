package tbs.server;

import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TBSServerImpl implements TBSServer {
	List<String[]> theatreList = new Vector<String[]>();
	//List<String[]> artistList = new Vector<String[]>();
	
	public String initialise(String path) {
		String line = "";
		String separator = "\t";
		
		try {
			BufferedReader data = new BufferedReader(new FileReader(path));
			
			// read & store theatre data from CSV file
			while ((line = data.readLine()) != null) {
				String[] theatre = line.split(separator);
				// Theatre ID at index 1
				// Theatre dimension at index 2
				// Theatre area at index 3
				theatreList.add(theatre);
			}
			
			return "";
		} catch (FileNotFoundException e) {
			return "ERROR File Not Found";
		} catch (IOException e) {
			return "ERROR IO Exception";
		}
	}

	public List<String> getTheatreIDs() {
		List<String> theatreIDs = new Vector<String>();
		
		// stores theatre IDs
		for (String[] t : theatreList) {
			theatreIDs.add(t[1]);
		}
		
		Collections.sort(theatreIDs);
		
		return theatreIDs;
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
		Artist artist = new Artist(name);
		String artistID = artist.getID();

		return artistID;
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
		
		return null;
	}
}
