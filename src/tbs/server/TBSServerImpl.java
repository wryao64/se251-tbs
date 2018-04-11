package tbs.server;

import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TBSServerImpl implements TBSServer {
	//private List<String[]> theatreList = new Vector<String[]>();
	private List<Theatre> theatreList = new Vector<Theatre>();
	private List<Artist> artistList = new Vector<Artist>();
	private List<Act> actList = new Vector<Act>();
	private List<Performance> performanceList = new Vector<Performance>();
	private List<Ticket> ticketList = new Vector<Ticket>();
	
	public String initialise(String path) {
		String line = "";
		String separator = "\t";
		
		try {
			BufferedReader data = new BufferedReader(new FileReader(path));
			
			// read & store theatre data from CSV file
//			while ((line = data.readLine()) != null) {
//				String[] theatre = line.split(separator);
//				// Theatre ID at index 1
//				// Theatre dimension at index 2
//				// Theatre area at index 3
//				theatreList.add(theatre);
//			}
			
			while ((line = data.readLine()) != null) {
				String[] theatreData = line.split(separator);
				String ID = theatreData[1];
				int dimensions = Integer.parseInt(theatreData[2]);
				int area = Integer.parseInt(theatreData[3]);
				// Theatre ID at index 1
				// Theatre dimension at index 2
				// Theatre area at index 3
				Theatre theatre = new Theatre(ID, dimensions, area);
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
//		for (String[] t : theatreList) {
//			theatreIDs.add(t[1]);
//		}
		for (Theatre t : theatreList) {
			theatreIDs.add(t.getID());
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
		List<String> actsForArtist = new Vector<String>();
		
		if (artistID.equals("")) {
			actsForArtist.add("ERROR missing artist ID");
			return actsForArtist;
		}
		
		// checks if the artist exists
		if (!checkArtistExists(artistID)) {
			actsForArtist.add("ERROR artist does not exist");
			return actsForArtist;
		} else {
			for (Act a : actList) { 
				if (artistID.equals(a.getArtistID())) { 
					actsForArtist.add(a.getID()); 
				} 
			} 
		}
		
		return actsForArtist;
	}

	public List<String> getPeformanceIDsForAct(String actID) {
		List<String> performanceForActs = new Vector<String>();
		
		if (actID.equals("")) {
			performanceForActs.add("ERROR missing act ID");
			return performanceForActs;
		}
		
		if (!checkActExists(actID)) {
			performanceForActs.add("ERROR act does not exist");
			return performanceForActs;
		} else {
			for (Performance p : performanceList) { 
				if (actID.equals(p.getActID())) { 
					performanceForActs.add(p.getID()); 
				} 
			} 
		}
		
		return performanceForActs;
	}

	public List<String> getTicketIDsForPerformance(String performanceID) {
		// TODO Auto-generated method stub
		return null;
	}

	public String addArtist(String name) {
		boolean artistExists = false;
		
		if (name.equals("")) {
			return "ERROR no name entered";
		}
		
		name = name.toLowerCase().trim();
		
		for (Artist a : artistList) {
			if (name.equals(a.getName())) {
				artistExists = true;
				break;
			}
		}
		
		if (artistExists) {
			return "ERROR artist already exists";
		}
		
		Artist artist = new Artist(name);
		artistList.add(artist);

		return artist.getID();
	}

	public String addAct(String title, String artistID, int minutesDuration) {
		boolean artistExists = false;
		
		if (title.equals("")) {
			return "ERROR missing title";
		} else if (artistID.equals("")) {
			return "ERROR missing artist ID";
		} 
		
		// checks if artist exists
		for (Artist a : artistList) {
			if (artistID.equals(a.getID())) {
				artistExists = true;
				break;
			}
		}
		if (!artistExists) {
			return "ERROR artist does not exist";
		}
		
		if (minutesDuration <= 0) {
			return "ERROR act duration is incorrect";
		}
		
		Act act = new Act(title, artistID, minutesDuration);
		actList.add(act);
		
		return act.getID();
	}

	public String schedulePerformance(String actID, String theatreID, String startTimeStr, String premiumPriceStr,
			String cheapSeatsStr) {
		//checks if any parameters are missing
		if (actID.equals("")) {
			return "ERROR missing act ID";
		} else if (theatreID.equals("")) {
			return "ERROR missing theatre ID";
		} else if (startTimeStr.equals("")) {
			return "ERROR missing start time";
		} else if (premiumPriceStr.equals("")) {
			return "ERROR missing premium price";
		} else if (cheapSeatsStr.equals("")) {
			return "ERROR missing cheap seat price";
		}
		
		if (!checkActExists(actID)) {
			return "ERROR act does not exist";
		} else if (!checkTheatreExists(theatreID)) {
			return "ERROR theatre does not exist";
		}
		// INCLUDE ERROR CHECKING FOR FORMAT OF OTHER PARAMETERS
		
		// ASSIGN PRICES TO SEATS
		
		Performance performance = new Performance(actID, theatreID, startTimeStr, premiumPriceStr, cheapSeatsStr);
		performanceList.add(performance);
		
		// CREATE SEATING ARRAY 
		
		return performance.getID();
	}

	public String issueTicket(String performanceID, int rowNumber, int seatNumber) {
		// will probably need to call seatsAvailable
		
		Ticket ticket = new Ticket("P1", 2, 2);
		ticketList.add(ticket);
		
		return null;
	}

	public List<String> seatsAvailable(String performanceID) {
		List<String> availableSeatList = new Vector<String>();
		//using performanceID, get theatreID
		for (Performance p : performanceList) {
			if (p.equals(performanceID)) {
				Performance performance = p;
				availableSeatList = performance.checkAvailableSeats();
				break;
			}
		}

		return availableSeatList;
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
	
	public boolean checkArtistExists(String artistID) {
		for (Artist a : artistList) {
			if (artistID.equals(a.getID())) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean checkActExists(String actID) {
		for (Act a : actList) {
			if (actID.equals(a.getID())) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean checkTheatreExists(String theatreID) {
		for (Theatre t : theatreList) {
			if (theatreID.equals(t.getID())) {
				return true;
			}
		}
		
		return false;
	}
	
//	public boolean checkExists(String ID, List<? extends Object> list) {
//		for (int i = 0; i < list.size(); i++) {
//			listItem = list.get(i);
//			if (ID.equals(listItem.getID())) {
//				return true;
//			}
//		}
//		
//		return false;
//	}
}
