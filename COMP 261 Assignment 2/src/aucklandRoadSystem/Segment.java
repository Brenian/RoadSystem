package aucklandRoadSystem;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 * An object representing a road segment with ID of the road it belongs to,
 * length of the road, the 2 nodes it is connected to, the list of coordinates
 * needed to draw the road and if it is currently highlighted
 * 
 * @author Brendan Julian
 *
 */
public class Segment {
	private int roadID;
	private Double length;
	private Node n1;
	private Node n2;
	private ArrayList<Location> coordinates = new ArrayList<Location>();
	private boolean highlighted;

	public Segment(int roadID, double length, Node n1, Node n2,
			ArrayList<Location> coords) {
		this.roadID = roadID;
		this.length = length;
		this.n1 = n1;
		this.n2 = n2;
		coordinates = coords;
	}

	public void draw(Graphics g, Location origin, double scale) {
		for (int i = 0; i < coordinates.size() - 1; i++) {
			Point pos = coordinates.get(i).asPoint(origin, scale);
			Point pos2 = coordinates.get(i + 1).asPoint(origin, scale);
			Graphics2D g2 = (Graphics2D) g;
			if (highlighted) {
				g2.setColor(Color.green.darker());
				g2.setStroke(new BasicStroke(3));
				g2.drawLine(pos.x, pos.y, pos2.x, pos2.y);
			} else {
				g2.setColor(Color.red.darker());
				g2.setStroke(new BasicStroke(1));
				g2.drawLine(pos.x, pos.y, pos2.x, pos2.y);
			}
		}
	}

	public int getRoadID() {
		return roadID;
	}

	public double getLength() {
		return length;
	}

	public Node getN1() {
		return n1;
	}

	public Node getN2() {
		return n2;
	}
	
	public Node getOtherEnd(Node thisEnd){
		if(thisEnd.equals(n1)){
			return n2;
		}else{
			return n1;
		}
	}

	public ArrayList<Location> getCoordinates() {
		return coordinates;
	}

	public void setHighlight(boolean highlight) {
		highlighted = highlight;
	}

	public boolean isHighlighted() {
		return highlighted;
	}

	@Override
	public String toString() {
		String output = (roadID + " " + length + " " + n1.getNodeID() + " "
				+ n2.getNodeID() + " Number of Points: " + coordinates.size());
		for (Location coord : coordinates) {
			output += (" " + coord.toString());
		}
		return output;
	}
}
