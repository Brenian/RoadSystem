package aucklandRoadSystem;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * An object representing a node with Node ID, X and Y coordinates stored as a
 * location and a List of connected segments
 * 
 * @author Brendan Julian
 *
 */
public class Node {
	private int nodeID;
	private double lat;// y
	private double lon;// x
	private Location loc;
	private ArrayList<Segment> connectedSegments = new ArrayList<Segment>();
	private boolean highlighted;
	private Color highlightColor;
	private boolean visited;
	private Node pathFrom;
	private double estCost;
	private double actCost;
	private int depth;

	public Node(int nodeID, double lat, double lon) {
		this.nodeID = nodeID;
		this.lat = lat;
		this.lon = lon;
		this.loc = Location.newFromLatLon(lat, lon);
	}

	public void draw(Graphics g, Location origin, double scale) {
		g.setColor(Color.blue);
		int highSize = 0;
		if (highlighted) {
			g.setColor(highlightColor);
			highSize = 5;
		}
		int diam = (2 * (int) scale / 30) + 1 + highSize;
		Point pos = loc.asPoint(origin, scale);
		g.fillOval(pos.x - (diam / 2), pos.y - (diam / 2), diam, diam);
	}
	
	public Segment isConnected(Node n){
		for(Segment segment: connectedSegments){
			if(segment.getN1().equals(n)||segment.getN2().equals(n)){
				return segment;
			}
		}
		return null;
	}
	
	public ArrayList<Node> getNeighbours(){
		ArrayList<Node> neighbours = new ArrayList<Node>();
		for(Segment segment: connectedSegments){
			neighbours.add(segment.getOtherEnd(this));
		}
		return neighbours;
	}

	public int getNodeID() {
		return nodeID;
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location l) {
		loc = l;
	}

	public ArrayList<Segment> getSegments() {
		return connectedSegments;
	}

	public void addSegment(Segment s) {
		connectedSegments.add(s);
	}

	public void setHighlight(boolean highlight, Color color) {
		highlighted = highlight;
		highlightColor = color;
	}

	public boolean isHighlighted() {
		return highlighted;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public Node getPathFrom() {
		return pathFrom;
	}

	public void setPathFrom(Node pathFrom) {
		this.pathFrom = pathFrom;
	}

	public double getEstCost() {
		return estCost;
	}

	public void setEstCost(double estCost) {
		this.estCost = estCost;
	}

	public double getActCost() {
		return actCost;
	}

	public void setActCost(double actCost) {
		this.actCost = actCost;
	}

	public String printInfo() {
		String output = ("Intersection Node ID: "+nodeID + " " + loc.toString() + "\nConnected Roads: ");
		Set<String> roadSet = new HashSet<String>();
		for (Segment segment : connectedSegments) {
			roadSet.add(RoadSystem.getRoadsList().getRoad(segment.getRoadID()).getLabel());
			segment.setHighlight(true);
		}
		output += roadSet.toString();
		return output;
	}

	@Override
	public String toString() {
		return (nodeID + " " + lat + " " + lon + " " + loc.toString());
	}

	public String printNodePath(String path) {
		if(!pathFrom.equals(null)){
			path+=" "+pathFrom.printNodePath(path);
		}
		return path;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}
	
}
