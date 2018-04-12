package tbs.server;

import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class TBSServerImpl implements TBSServer {
	// Theatre ID at index 1
	// Theatre dimension at index 2
	// Theatre area at index 3
	private static final int T_ID_IND = 1;
	private static final int T_DIM_IND = 2;
	private static final int T_AREA_IND = 3;
	
	private TreeSet<String> theatreIDList = new TreeSet<String>();
	private TreeMap<String, Integer> theatreDimensionList = new TreeMap<String, Integer>();
	private TreeMap<String, Integer> theatreAreaList = new TreeMap<String, Integer>();
	
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
			while ((line = data.readLine()) != null) {
				String[] theatreData = line.split(separator);
				
				try {
					String theatreID = theatreData[T_ID_IND];
					Integer dimensions = Integer.valueOf(theatreData[T_DIM_IND]);
					String strArea = theatreData[T_AREA_IND];
					strArea = strArea.substring(0, strArea.length() - 1);
					Integer area = Integer.valueOf(strArea);
					theatreIDList.add(theatreID);
					theatreDimensionList.put(theatreID, dimensions);
					theatreAreaList.put(theatreID, area);
				} catch (NumberFormatException e) {
					return "ERROR invalid dimension or area data";
				}
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
		
		for (String t : theatreIDList) {
			theatreIDs.add(t);
		}
		
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
		List<String> performancesForAct = new Vector<String>();
		
		if (actID.equals("")) {
			performancesForAct.add("ERROR missing act ID");
			return performancesForAct;
		}
		
		if (!checkActExists(actID)) {
			performancesForAct.add("ERROR act does not exist");
			return performancesForAct;
		} else {
			for (Performance p : performanceList) { 
				if (actID.equals(p.getActID())) { 
					performancesForAct.add(p.getID()); 
				} 
			} 
		}
		
		return performancesForAct;
	}

	public List<String> getTicketIDsForPerformance(String performanceID) {
		List<String> ticketsForPerformance = new Vector<String>();
		
		if (performanceID.equals("")) {
			ticketsForPerformance.add("ERROR missing performance ID");
			return ticketsForPerformance;
		}
		
		if (!checkPerformanceExists(performanceID)) {
			ticketsForPerformance.add("ERROR performance does not exist");
			return ticketsForPerformance;
		}
		
		for (Ticket t : ticketList) {
			if (t.getPerformanceID().equals(performanceID)) {
				ticketsForPerformance.add(t.getID());
			}
		}
		
		return ticketsForPerformance;
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
		
		if (!checkTimeFormat(startTimeStr)) {
			return "ERROR time format is invalid";
		}
		
		if (!checkPriceFormat(premiumPriceStr) || !checkPriceFormat(cheapSeatsStr)) {
			return "ERROR price format is invalid";
		}
		
		Performance performance = new Performance(actID, theatreID, startTimeStr, premiumPriceStr, cheapSeatsStr);
		Theatre perfTheatre = findTheatre(theatreID);
		performance.setSeats(perfTheatre);
		
		performanceList.add(performance);
		
		return performance.getID();
	}

	public String issueTicket(String performanceID, int rowNumber, int seatNumber) {
		if (!checkPerformanceExists(performanceID)) {
			return "ERROR performance does not exist";
		}
		
		Performance thisPerf = findPerformance(performanceID);
		
		if (!thisPerf.checkIfSeatExists(rowNumber, seatNumber)) {
			return "ERROR seat does not exist";
		}

		if (thisPerf.checkIfSeatIsSold(rowNumber, seatNumber)) {
			return "ERROR seat is taken";
		}
		
		Ticket ticket = new Ticket(performanceID, rowNumber, seatNumber);
		ticketList.add(ticket);
		
		// sets seat to be taken
		thisPerf.seatSold(rowNumber, seatNumber);
		
		return ticket.getID();
	}

	public List<String> seatsAvailable(String performanceID) {
		List<String> availableSeatList = new Vector<String>();

		Performance performance = findPerformance(performanceID);
		availableSeatList = performance.checkAvailableSeats();

		return availableSeatList;
	}

	public List<String> salesReport(String actID) {
		List<String> salesReport = new Vector<String>();
		
		if (!checkActExists(actID)) {
			salesReport.add("ERROR act does not exist");
			return salesReport;
		}
		
		List<String> performanceIDs = getPeformanceIDsForAct(actID);
		
		for (String p : performanceIDs) {
			Performance performance = findPerformance(p);
			salesReport.add(performance.performanceDetails());
		}
		
		return salesReport;
	}

	public List<String> dump() {
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
	
	public boolean checkPerformanceExists(String performanceID) {
		for (Performance p : performanceList) {
			if (performanceID.equals(p.getID())) {
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
	
	public Theatre findTheatre(String theatreID) {
		for (Theatre t : theatreList) {
			if (t.getID().equals(theatreID)) {
				return t;
			}
		}
		
		return null;
	}
	
	public Performance findPerformance(String performanceID) {
		for (Performance p : performanceList) {
			if (p.getID().equals(performanceID)) {
				return p;
			}
		}
		
		return null;
	}
	
	public boolean checkPriceFormat(String price) {
		for (int i = 0; i < price.length(); i++) {
			if (i == 0) {
				if (price.charAt(i) != '$') {
					return false;
				}
			} else {
				if (!Character.isDigit(price.charAt(i))) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public boolean checkTimeFormat(String time) {
		try {
			LocalDateTime.parse(time);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}
}
