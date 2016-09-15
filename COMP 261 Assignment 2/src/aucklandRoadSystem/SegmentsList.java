package aucklandRoadSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SegmentsList {

	private ArrayList<Segment> segments;

	public SegmentsList() {
		segments = new ArrayList<Segment>();
	}

	public void initialiseSegmentsList(File fName) {
		try {
			// Read file line by line
			File roadFile = new File(fName.getAbsolutePath());
			BufferedReader data = new BufferedReader(new FileReader(roadFile));
			// Dispose of first line
			String line = data.readLine();
			while ((line = data.readLine()) != null) {
				// Process each line using split method
				String[] values = line.split("\t");
				int roadID = Integer.parseInt(values[0]);
				double length = Double.parseDouble(values[1]);
				int nodeID1 = Integer.parseInt(values[2]);
				int nodeID2 = Integer.parseInt(values[3]);
				ArrayList<Location> tempLocs = new ArrayList<Location>();
				double tempCoord1;
				double tempCoord2;
				Location tempLocation;
				for (int i = 5; i < values.length; i += 2) {
					tempCoord1 = Double.parseDouble(values[i - 1]);
					tempCoord2 = Double.parseDouble(values[i]);
					tempLocation = Location.newFromLatLon(tempCoord1,
							tempCoord2);
					tempLocs.add(tempLocation);
				}
				Segment tempSegment = new Segment(roadID, length, RoadSystem
						.getNodesList().getNode(nodeID1), RoadSystem
						.getNodesList().getNode(nodeID2), tempLocs);
				RoadSystem.getNodesList().getNode(nodeID1)
						.addSegment(tempSegment);
				RoadSystem.getNodesList().getNode(nodeID2)
						.addSegment(tempSegment);
				RoadSystem.getRoadsList().getRoad(roadID)
						.addRoadSegment(tempSegment);
				segments.add(tempSegment);
			}
			data.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Segment> getAllSegments() {
		return segments;
	}

	public void setHighlightsFalse() {
		for (Segment segment : segments) {
			segment.setHighlight(false);
		}
	}
}