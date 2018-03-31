package tbs.server;

import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TBSServerImpl implements TBSServer {
	private List<String[]> theatreList = new Vector<String[]>();
	private List<Artist> artistList = new Vector<Artist>();
	
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
		List<String> artistIDs = new Vector<String>();
		
		// stores artist IDs
		for (Artist a : artistList) {
			artistIDs.add(a.getID());
		}
		
		Collections.sort(artistIDs);
		
		return artistIDs;
	}

	public List<String> getArtistNames() {
		List<String> artistNames = new Vector<String>();
		
		// stores artist IDs
		for (Artist a : artistList) {
			artistNames.add(a.getName());
		}
		
		Collections.sort(artistNames);
		
		return artistNames;
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
		if (name.equals("")) {
			return "ERROR no name entered";
		}
		
		name = name.toLowerCase().trim();
		
		// checks if artist already exists
		for (Artist a : artistList) {
			if (name.equals(a.getName())) {
				return "ERROR artist already exists";
			}
		}
		
		Artist artist = new Artist(name);
		artistList.add(artist);

		return artist.getID();
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
		// prints all artists' IDs
		if (artistList.size() != 0) {
			for (Artist a : artistList) {
				System.out.println(a.getName());
			}
		}
		return null;
	}
}
