package tbs.server;

import java.util.Collection;
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

public class TBSServerImpl implements TBSServer {
	// Theatre ID at index 1
	private static final int T_ID_IND = 1;
	// Theatre dimension at index 2
	private static final int T_DIM_IND = 2;
	// Theatre area at index 3
	private static final int T_AREA_IND = 3;
	// Prefix for ID strings
	private static final String ARTIST_ID_BASE = "A";
	private static final String TICKET_ID_BASE = "TKT";
	
	Checker checker = new Checker();
	
	private TreeSet<String> theatreIDSet = new TreeSet<String>();
	// key: theatreID, value: dimension/area
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
				
				try { // checks that the dimensions and area are integers
					String theatreID = theatreData[T_ID_IND];
					Integer dimensions = Integer.valueOf(theatreData[T_DIM_IND]);
					String strArea = theatreData[T_AREA_IND];
					// gets rid of the " at the end of strArea
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
		
		// checks for missing or invalid ID
		String errorMsg = checker.checkID(artistID, artistMap);
		if (errorMsg != "") {
			actsForArtist.add(errorMsg);
			return actsForArtist;
		} else {
			Collection<Act> acts = actMap.values();
			
			// finds all acts for the given artist
			for (Act a : acts) {
				if (artistID.equals(a.getArtistID())) { 
					actsForArtist.add(a.getActID()); 
				}
			}
		}
		
		return actsForArtist;
	}

	public List<String> getPeformanceIDsForAct(String actID) {
		List<String> performancesForAct = new Vector<String>();
		
		// checks for missing or invalid ID
		String errorMsg = checker.checkID(actID, actMap);
		if (errorMsg != "") {
			performancesForAct.add(errorMsg);
			return performancesForAct;
		} else {
			Collection<Performance> performances = performanceMap.values();
			
			// finds all performances for the given act
			for (Performance p : performances) { 
				if (actID.equals(p.getActID())) { 
					performancesForAct.add(p.getPerformanceID()); 
				} 
			}
		}
		
		return performancesForAct;
	}

	public List<String> getTicketIDsForPerformance(String performanceID) {
		List<String> ticketsForPerformance = new Vector<String>();
		
		// checks for missing or invalid ID
		String errorMsg = checker.checkID(performanceID, performanceMap);
		if (errorMsg != "") {
			ticketsForPerformance.add(errorMsg);
			return ticketsForPerformance;
		} else {
			Collection<Ticket> tickets = ticketMap.values();
			
			// finds all tickets for the given performance
			for (Ticket t : tickets) {
				if (performanceID.equals(t.getPerformanceID())) {
					ticketsForPerformance.add(t.getTicketID());
				}
			}
		}
		
		return ticketsForPerformance;
	}

	public String addArtist(String name) {
		name = name.trim();
		
		// checks for missing or already existing name
		String errorMsg = checker.checkName(name, artistMap);
		if (errorMsg != "") {
			return errorMsg;
		} else {
			String artistID = ARTIST_ID_BASE + (artistMap.size() + 1);
			artistMap.put(artistID, name);
			return artistID;
		}
	}

	public String addAct(String title, String artistID, int minutesDuration) {
		// check for missing/invalid data
		String errorMsg = checker.checkParamsForAct(title, artistID, minutesDuration, artistMap);
		
		if (errorMsg != "") {
			return errorMsg;
		} else {
			Act act = new Act(title, artistID, minutesDuration);
			String actID = act.getActID();
			actMap.put(actID, act);
			
			return actID;
		}		
	}

	public String schedulePerformance(String actID, String theatreID, String startTimeStr,
			String premiumPriceStr, String cheapSeatsStr) {
		// checks for missing/invalid data & formatting
		String errorMsg = checker.checkParamsForPerformance(actID, theatreID, startTimeStr, 
				premiumPriceStr, cheapSeatsStr, actMap, theatreIDSet);
		
		if (!errorMsg.equals("")) {
			return errorMsg;
		} else {
			Integer theatreDim = theatreDimensionMap.get(theatreID);
			Performance performance = new Performance(actID, theatreID, theatreDim, startTimeStr, premiumPriceStr, cheapSeatsStr);
			String performanceID = performance.getPerformanceID();
			performanceMap.put(performanceID, performance);
			
			return performanceID;
		}
	}

	public String issueTicket(String performanceID, int rowNumber, int seatNumber) {
		Performance thisPerf = performanceMap.get(performanceID);
		// checks for missing/invalid data
		String errorMsg = checker.checkParamsForTicket(performanceID, rowNumber, seatNumber, 
				performanceMap, thisPerf);
		
		if (errorMsg != "") {
			return errorMsg;
		} else {
			String ticketID = TICKET_ID_BASE + (ticketMap.size() + 1);
			Ticket ticket = thisPerf.newTicket(ticketID, rowNumber, seatNumber);
			ticketMap.put(ticketID, ticket);
			
			// sets seat to be unavailable
			thisPerf.seatSold(rowNumber, seatNumber);
			
			return ticketID;
		}
	}

	public List<String> seatsAvailable(String performanceID) {
		List<String> availableSeatList = new Vector<String>();
		
		Performance performance = performanceMap.get(performanceID);
		availableSeatList = performance.findAvailableSeats();

		return availableSeatList;
	}

	public List<String> salesReport(String actID) {
		List<String> salesReport = new Vector<String>();
		
		// checks for missing/invalid ID
		String errorMsg = checker.checkID(actID, actMap);
		if (errorMsg != "") {
			salesReport.add(errorMsg);
			return salesReport;
		} else {
			List<String> performanceIDs = getPeformanceIDsForAct(actID);
			
			// finds all performances for the given act
			for (String p : performanceIDs) {
				Performance performance = performanceMap.get(p);
				salesReport.add(performance.performanceDetails());
			}
			
			return salesReport;
		}
	}

	public List<String> dump() {
		return null;
	}
}
