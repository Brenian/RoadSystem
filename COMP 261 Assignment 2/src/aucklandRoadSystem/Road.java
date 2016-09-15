package aucklandRoadSystem;

import java.util.ArrayList;

/**
 * An object representing a road with road ID, type of road, its name, the city
 * it's in, whether or not it's one way, it's speed class and whether or not it
 * allows cars, pedestrians or cyclists
 * 
 * @author Brendan Julian
 *
 */
public class Road {
	private int roadID;
	private int type;
	private String name;
	private String city;
	private boolean oneWay;
	private int speed;
	private int roadClass;
	private boolean notForCar;
	private boolean notForPed;
	private boolean notForCyc;
	private ArrayList<Segment> roadSegments = new ArrayList<Segment>();

	public Road(int roadID, int type, String label, String city, boolean oneWay,
			int speed, int roadClass, boolean notForCar, boolean notForPede,
			boolean notForBicy) {
		this.roadID = roadID;
		this.type = type;
		this.name = label;
		this.city = city;
		this.oneWay = oneWay;
		this.speed = speed;
		this.roadClass = roadClass;
		this.notForCar = notForCar;
		this.notForPed = notForPede;
		this.notForCyc = notForBicy;
	}

	public int getRoadID() {
		return roadID;
	}

	public int getType() {
		return type;
	}

	public String getLabel() {
		return name;
	}

	public String getCity() {
		return city;
	}

	public boolean getOneWay() {
		return oneWay;
	}

	public int getSpeed() {
		return speed;
	}

	public int getRoadClass() {
		return roadClass;
	}

	public boolean getNotForCar() {
		return notForCar;
	}

	public boolean getNotForPede() {
		return notForPed;
	}

	public boolean getNotForBicy() {
		return notForCyc;
	}

	public ArrayList<Segment> getRoadSegments() {
		return roadSegments;
	}

	public void addRoadSegment(Segment roadSegment) {
		roadSegments.add(roadSegment);
	}
	
	public void highlightAllSegments(){
		for(Segment segment: roadSegments){
			segment.setHighlight(true);
		}
	}
	
	@Override
	public String toString(){
		return (roadID+" "+type+" "+name+" "+city+" "+oneWay+" "+speed+" "+roadClass+" "+notForCar+" "+notForPed+" "+notForCyc);
	}
}
