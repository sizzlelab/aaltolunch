package org.sizzle.aaltolunch.datatype;

/**
 * @author Nalin Chaudhary
 */
public class FriendSelection implements Comparable {

	private String uid = "";
	private String name = "";
	private String avtarLink = "";
	private String placeToEat = "";
	private String timeToEat = "";
	private String hourToEat = "";
	private String minToEat = "";
	private int totalMins = 0;
	
	public FriendSelection(String uid, String name, String avtarLink,
			String placeToEat, String timeToEat) {

		this.uid = uid;
		this.name = name;
		this.avtarLink = avtarLink;
		this.placeToEat = placeToEat;
		this.timeToEat = timeToEat;
		this.hourToEat = (timeToEat.length() > 0) ? timeToEat.substring(0, timeToEat.indexOf(":")) : "";
		this.minToEat = (timeToEat.length() > 0) ? timeToEat.substring(timeToEat.indexOf(":") + 1) : "";
		this.totalMins = ((Integer.parseInt(this.hourToEat) * 60) + Integer.parseInt(this.minToEat));
	}
	
	public String getUid() {
		return uid;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAvtarLink() {
		return avtarLink;
	}
	
	public String getPlaceToEat() {
		return placeToEat;
	}
	
	public String getTimeToEat() {
		return timeToEat;
	}
	
	public String getHourToEat() {
		return hourToEat;
	}
	
	public String getMinToEat() {
		return minToEat;
	}

	public int getTotalMins() {
		return totalMins;
	}

	public int compareTo(Object o) {
		return (this.totalMins - ((FriendSelection)o).getTotalMins());
	}
}
