package tbs.server;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import tbs.server.Performance.Ticket;

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
	private static final String ARTIST_ID_BASE = "A";
	private static final String TICKET_ID_BASE = "TKT";
	
	private TreeSet<String> theatreIDSet = new TreeSet<String>();
	private TreeMap<String, Integer> theatreDimensionMap = new TreeMap<String, Integer>();
	private TreeMap<String, Integer> theatreAreaMap = new TreeMap<String, Integer>();
	
	// key is artistID, value is artistName
	private TreeMap<String, String> artistMap = new TreeMap<String, String>();
	// key: actID, value: Act object
	private TreeMap<String, Act> actMap = new TreeMap<String, Act>();
	// key: performanceID, value: Performance object
	private TreeMap<String, Performance> performanceMap = new TreeMap<String, Performance>();
	// key: ticketID, value: Ticket object
	private TreeMap<String, Ticket> ticketMap = new TreeMap<String, Ticket>();
	
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
					theatreIDSet.add(theatreID);
					theatreDimensionMap.put(theatreID, dimensions);
					theatreAreaMap.put(theatreID, area);
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
		
		for (String t : theatreIDSet) {
			theatreIDs.add(t);
		}
		
		return theatreIDs;
	}

	public List<String> getArtistIDs() {
		List<String> artistIDs = new Vector<String>();
		
		Set<String> artistIDsSet = artistMap.keySet();
		for (String a : artistIDsSet) {
			artistIDs.add(a);
		}
		
		return artistIDs;
	}

	public List<String> getArtistNames() {
		List<String> artistNames = new Vector<String>();

		Collection<String> artistNamesColl = artistMap.values();
		for (String a : artistNamesColl) {
			artistNames.add(a);
		}
		
		return artistNames;
	}

	public List<String> getActIDsForArtist(String artistID) {
		List<String> actsForArtist = new Vector<String>();
		
		if (artistID.equals("")) {
			actsForArtist.add("ERROR missing artist ID");
			return actsForArtist;
		} else if (!artistMap.containsKey(artistID)) {  // checks if artist exists
			actsForArtist.add("ERROR artist does not exist");
			return actsForArtist;
		} else {
			Collection<Act> acts = actMap.values();
			for (Act a : acts) {
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
		} else if (!actMap.containsKey(actID)) {  // checks if act exists
			performancesForAct.add("ERROR act does not exist");
			return performancesForAct;
		} else {
			Collection<Performance> performances = performanceMap.values();
			for (Performance p : performances) { 
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
		} else if (!performanceMap.containsKey(performanceID)) { // check if performance exists
			ticketsForPerformance.add("ERROR performance does not exist");
			return ticketsForPerformance;
		} else {
			Collection<Ticket> tickets = ticketMap.values();
			for (Ticket t : tickets) {
				if (t.getPerformanceID().equals(performanceID)) {
					ticketsForPerformance.add(t.getID());
				}
			}
		}
		
		return ticketsForPerformance;
	}

	public String addArtist(String name) {
		if (name.equals("")) {
			return "ERROR no name entered";
		}
		
		name = name.trim();
		
		if (artistMap.containsValue(name) || artistMap.containsValue(name.toLowerCase())) {
			return "ERROR artist already exists";
		}
		
		String artistID = ARTIST_ID_BASE + (artistMap.size() + 1);
		artistMap.put(artistID, name);

		return artistID;
	}

	public String addAct(String title, String artistID, int minutesDuration) {
		if (title.equals("") || artistID.equals("")) {
			return "ERROR empty data";
		}
		if (minutesDuration <= 0) {
			return "ERROR act duration is invalid";
		}
		
		// checks if artist exists
		if (!artistMap.containsKey(artistID)) {
			return "ERROR artist does not exist";
		}
		
		Act act = new Act(title, artistID, minutesDuration);
		String actID = act.getID();
		actMap.put(actID, act);
		
		return actID;
	}

	public String schedulePerformance(String actID, String theatreID, String startTimeStr,
			String premiumPriceStr, String cheapSeatsStr) {
		// checks for errors (missing or non-existing data & formatting)
		String errorMsg = checkParamsForPerformance(actID, theatreID, startTimeStr, 
				premiumPriceStr, cheapSeatsStr);
		if (!errorMsg.equals("")) {
			return errorMsg;
		}
		
		Integer theatreDim = theatreDimensionMap.get(theatreID);
		Performance performance = new Performance(actID, theatreID, theatreDim, startTimeStr, premiumPriceStr, cheapSeatsStr);
		String performanceID = performance.getID();
		performanceMap.put(performanceID, performance);
		
		return performance.getID();
	}

	public String issueTicket(String performanceID, int rowNumber, int seatNumber) {
		// checks if performance exists
		if (!performanceMap.containsKey(performanceID)) {
			return "ERROR performance does not exist";
		}
		
		Performance thisPerf = performanceMap.get(performanceID);
		
		if (!thisPerf.checkIfSeatExists(rowNumber, seatNumber)) {
			return "ERROR seat does not exist";
		} else if (!thisPerf.checkIfSeatIsAvailable(rowNumber, seatNumber)) {
			return "ERROR seat is taken";
		}
		
		String ticketID = TICKET_ID_BASE + (ticketMap.size() + 1);
		Performance.Ticket ticket = thisPerf.newTicket(ticketID, performanceID, rowNumber, seatNumber);
		ticketMap.put(ticketID, ticket);
		
		// sets seat to be sold
		thisPerf.seatSold(rowNumber, seatNumber);
		
		return ticket.getID();
	}

	public List<String> seatsAvailable(String performanceID) {
		List<String> availableSeatList = new Vector<String>();

		Performance performance = performanceMap.get(performanceID);
		availableSeatList = performance.findAvailableSeats();

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
	
	private boolean checkPriceFormat(String price) {
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
	
	private boolean checkTimeFormat(String time) {
		try {
			LocalDateTime.parse(time);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}
	
	private String checkParamsForPerformance(String actID, String theatreID, 
			String startTimeStr, String premiumPriceStr, String cheapSeatsStr) {
		if (actID.equals("") || theatreID.equals("")) {
			return "ERROR missing ID";
		} else if (startTimeStr.equals("")) {
			return "ERROR missing start time";
		} else if (premiumPriceStr.equals("") || cheapSeatsStr.equals("")) {
			return "ERROR missing price data";
		}

		// checks if the act and theatre exists
		if (!actMap.containsKey(actID)) {
			return "ERROR act does not exist";
		} else if (!theatreIDSet.contains(theatreID)) {
			return "ERROR theatre does not exist";
		}
		
		if (!checkTimeFormat(startTimeStr)) {
			return "ERROR time format is invalid";
		}
		
		if (!checkPriceFormat(premiumPriceStr) || !checkPriceFormat(cheapSeatsStr)) {
			return "ERROR price format is invalid";
		}
		
		return "";
	}
}
