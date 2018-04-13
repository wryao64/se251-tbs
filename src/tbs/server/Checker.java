package tbs.server;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.TreeMap;
import java.util.TreeSet;

public class Checker {
	private boolean checkTimeFormat(String time) {
		try {
			LocalDateTime.parse(time);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
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
	
	private String checkTime(String time) {
		if (time.equals("")) {
			return "ERROR missing start time";
		} else if (!this.checkTimeFormat(time)) {
			return "ERROR time format is invalid";
		} else {
			return "";
		}
	}
	
	private String checkPrice(String premiumPrice, String cheapPrice) {
		if (premiumPrice.equals("") || cheapPrice.equals("")) {
			return "ERROR missing price data";
		} else if (!checkPriceFormat(premiumPrice) || !checkPriceFormat(cheapPrice)) {
			return "ERROR price format is invalid";
		} else {
			return "";
		}
	}
	
	public String checkName(String name, TreeMap<String, ?> map) {
		if (name.equals("")) {
			return "ERROR missing name";
		} else if (map.containsValue(name) || map.containsValue(name.toLowerCase())) {
			return "ERROR artist already exists";
		} else {
			return "";
		}
	}
	
	public String checkID(String ID, TreeMap<String, ?> map) {
		if (ID.equals("")) {
			return "ERROR missing ID";
		} else if (!map.containsKey(ID)) {
			return "ERROR ID does not exist";
		} else {
			return "";
		}
	}
	
	public String checkTheatreID(String theatreID, TreeSet<String> theatreIDSet) {
		if (theatreID.equals("")) {
			return "ERROR missing ID";
		} else if (!theatreIDSet.contains(theatreID)) {
			return "ERROR ID does not exist";
		} else {
			return "";
		}
	}
	
	public String checkParamsForPerformance(String actID, String theatreID, 
			String startTimeStr, String premiumPriceStr, String cheapSeatsStr,
			TreeMap<String, Act> actMap, TreeSet<String> theatreIDSet) {
		String actErrorMsg = this.checkID(actID, actMap);
		String theatreErrorMsg = this.checkTheatreID(theatreID, theatreIDSet);
		String timeErrorMsg = this.checkTime(startTimeStr);
		String priceErrorMsg = this.checkPrice(premiumPriceStr, cheapSeatsStr);
		
		if (actErrorMsg != "") {
			return actErrorMsg;
		} else if (theatreErrorMsg != "") {
			return theatreErrorMsg;
		} else if (timeErrorMsg != "") {
			return timeErrorMsg;
		} else if (priceErrorMsg != "") {
			return priceErrorMsg;
		} else {
			return "";
		}
	}
}