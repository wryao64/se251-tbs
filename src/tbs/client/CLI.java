package tbs.client;

import java.util.List;
import java.util.Collections;

import tbs.server.TBSServer;
import tbs.server.TBSServerImpl;

public class CLI {
	public static void main(String[] args) {
		String path = "theatres1.csv";
		if (args.length > 0) {
			path = args[0]; // This allows a different file to be specified as an argument, but the default is theatres2.csv
		}
		TBSServer server = new TBSServerImpl();
		String result = server.initialise(path);
		System.out.println("Result from initialisation is {" + result + "}");  // Put in { } to make empty strings easier to see.
		server.dump(); // Implement dump() to print something useful here to determine whether your initialise method has worked.
		
//		String artistID1 = server.addArtist("Ewan");
//		System.out.println("Result from adding artist 'Ewan' is {" + artistID1 + "}");
//		server.dump(); // Check that the server has been updated
		
//		String actID1 = server.addAct("Lecture 3b: Making Objects", artistID1, 50); // this also checks that the artist ID is used properly
//		System.out.println("Result from adding act to artist 'Ewan' is {" + actID1 + "}");
//		server.dump();
		
		//-------------------- TESTING -----------------------
		
		//-----------getTheatreIDs()-----------------------
		List<String> theatreIDs = server.getTheatreIDs();
		
//		for (String t : theatreIDs) {
//			System.out.println(t);
//		}
//		server.dump();

		//-------------addArtist()--------------------
		String artistID1 = server.addArtist("Ewan");
		String artistID2 = server.addArtist("Someone");
		String artistID3 = server.addArtist(" Ewan ");
		String artistID4 = server.addArtist("");
		String artistID5 = server.addArtist("BTS");
		
		System.out.println("Result from adding artist 'Ewan' is {" + artistID1 + "}");
//		System.out.println("Result from adding artist 'Someone' is {" + artistID2 + "}");
//		System.out.println("Result from adding artist 'Ewan' is {" + artistID3 + "}");
//		System.out.println("Result from adding artist '' is {" + artistID4 + "}");
//		System.out.println("Result from adding artist 'BTS' is {" + artistID5 + "}");
		
//		server.dump();
		
		//-----------getArtistIDs()--------------------------
		List<String> artistIDs = server.getArtistIDs();
		
//		for (String a : artistIDs) {
//			System.out.println(a);
//		}
		
		//-------------getArtistNames()--------------------------
		List<String> artistNames = server.getArtistNames();
		
//		for (String a : artistNames) {
//			System.out.println(a);
//		}
		
		//------------addAct()-------------------------
		String actID1 = server.addAct("Act 1", "A1", 5);
		String actID2 = server.addAct("Act 2", "A2", 10);
		String actID3 = server.addAct("The Show", "A1", 10);
		
//		System.out.println(actID1);
//		System.out.println(actID2);
		
		//------------getActIDsForArtist()---------------
		List<String> acts1 = server.getActIDsForArtist("A1");
		
		for (String a : acts1) {
			System.out.println("A1:" + a);
		}
		
		//------------schedulePerformance()-------------
		String performanceID1 = server.schedulePerformance("ACT1", "T1", 
				"2018-03-31T11:00", "$10", "$5");
		String performanceID2 = server.schedulePerformance("ACT2", "T1", 
				"2018-04-01T11:00", "$10", "$5");
		String performanceID3 = server.schedulePerformance("ACT1", "T1", 
				"2018-03-02T11:00", "$10", "$5");
		
//		System.out.println(performanceID1);
//		System.out.println(performanceID2);
//		System.out.println(performanceID3);
		
		//-------------getPeformanceIDsForAct()--------
		List<String> performances1 = server.getPeformanceIDsForAct("ACT1");
		
		for (String p : performances1) { 
			System.out.println(p);
		}
		
		//--------------issueTicket()------------------
		String ticket1 = server.issueTicket(performanceID1, 1, 1);
		String ticket2 = server.issueTicket(performanceID1, 5, 3);
		String ticket3 = server.issueTicket(performanceID1, 2, 1);
		String ticket4 = server.issueTicket(performanceID1, 1, 2);
		String ticket5 = server.issueTicket(performanceID1, 6, 5);
		
		String ticket6 = server.issueTicket(performanceID3, 1, 3);
		String ticket7 = server.issueTicket(performanceID3, 4, 2);
		String ticket8 = server.issueTicket(performanceID3, 5, 1);
		
		System.out.println(ticket1);
		
		//--------------seatsAvailable()------------
		List<String> seatList = server.seatsAvailable(performanceID1);
		
		//System.out.println(seatList);
		
		//--------------salesReport()------------
		List<String> salesReport = server.salesReport(actID1);
		
		System.out.println(salesReport);
	}
}
