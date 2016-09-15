package aucklandRoadSystem;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RoadSystem extends GUI {

	private static NodeList nodesList;
	private static RoadList roadsList;
	private static SegmentsList segmentsList;
	private Trie nameTrie;
	private AStar aStarSearch;
	private ArtiPoints artiPts;
	private Location paneOrigin;
	private double scale = 5;
	private Point mousePoint;
	private Node searchStartNode;
	private Node searchEndNode;
	private boolean selectStart = true;

	public RoadSystem() {
		nodesList = new NodeList();
		roadsList = new RoadList();
		segmentsList = new SegmentsList();
		aStarSearch = new AStar();
		artiPts = new ArtiPoints();
		nameTrie = new Trie();
		paneOrigin = new Location(-120, 100);
	}

	@Override
	protected void onRoute() {
		if (searchStartNode == null || searchEndNode == null) {
			getTextOutputArea().setText("Please select 2 intersections");
		} else {
			getTextOutputArea()
					.setText(
							searchStartNode.toString() + " "
									+ searchEndNode.toString());
			Node end = aStarSearch.search(searchStartNode, searchEndNode);
			redraw();
			if(!end.equals(null)){
			Node nextNode = end.getPathFrom();
			boolean startFound = false;
			double totDist = 0;
			Map<String,Double> roadsDist = new HashMap<String,Double>();
			Segment tSeg;
			clearHighlights();
			while (!startFound) {
				end.setHighlight(true, Color.green);
				nextNode.setHighlight(true, Color.green);
				tSeg = end.isConnected(nextNode);
				tSeg.setHighlight(true);
				totDist += tSeg.getLength();
				String name = roadsList.getRoad(tSeg.getRoadID()).getLabel();
				if(!roadsDist.containsKey(name)){
					roadsDist.put(name, tSeg.getLength());
				}else{
					roadsDist.put(name, roadsDist.get(name)+tSeg.getLength());
				}
				if (nextNode.equals(searchStartNode)) {
					System.out.println("Start found");
					startFound = true;
				} else if (nextNode.getPathFrom() != null) {
					Node tempNode = nextNode.getPathFrom();
					end = nextNode;
					nextNode = tempNode;
				}
			}
			getTextOutputArea().setText(
					"Route found between " + searchStartNode.getNodeID()
							+ " & " + searchEndNode.getNodeID() + ": "
							+ totDist + "km\n" + roadsDist.toString());
			}else{
				getTextOutputArea().setText("No Route Found");
			}
		}
	}

	@Override
	protected void onArticulate() {
		getTextOutputArea().setText("Finding Articulation Points");
		HashSet<Node> artPts = artiPts.identifyArtPts(nodesList.getAllNodes());
		for(Node node: artPts){
			node.setHighlight(true, Color.YELLOW);
		}
		getTextOutputArea().setText(artPts.size()+" Articulation Points Found");
	}

	@Override
	protected void redraw(Graphics g) {
		ArrayList<Node> drawNodes = nodesList.getAllNodes();
		ArrayList<Segment> drawSegments = segmentsList.getAllSegments();
		for (Segment segment : drawSegments) {
			segment.draw(g, paneOrigin, scale);
		}
		for (Node node : drawNodes) {
			node.draw(g, paneOrigin, scale);
		}
	}

	@Override
	protected void onPress(MouseEvent e) {
		mousePoint = e.getPoint();
	}

	@Override
	protected void onDrag(MouseEvent e) {
		int dx = e.getX() - mousePoint.x;
		int dy = e.getY() - mousePoint.y;
		paneOrigin = paneOrigin.moveBy(-dx / 10, dy / 10);
		mousePoint = e.getPoint();
	}

	@Override
	protected void onScroll(MouseWheelEvent e) {
		if (e.getWheelRotation() < 0) {
			if (scale < 255) {
				scale += 10;
			}
		} else {
			if (scale > 5) {
				scale += -10;
			}
		}
		getTextOutputArea().setText("Scale: " + scale);
	}

	@Override
	protected void onClick(MouseEvent e) {
		clearHighlights();
		Point mousePoint = e.getPoint();
		Location mouseLoc = Location
				.newFromPoint(mousePoint, paneOrigin, scale);
		double distance = Double.MAX_VALUE;
		Node tempNode = new Node(0, 0, 0);
		for (Node node : nodesList.getAllNodes()) {
			if (mouseLoc.distance(node.getLoc()) < distance) {
				tempNode = node;
				distance = mouseLoc.distance(node.getLoc());
			}
		}
		if (selectStart) {
			searchStartNode = tempNode;
			selectStart = false;
			tempNode.setHighlight(true, Color.magenta.darker());
		} else {
			searchEndNode = tempNode;
			selectStart = true;
			tempNode.setHighlight(true, Color.ORANGE);
		}
		getTextOutputArea().setText(tempNode.printInfo());
	}

	private void clearHighlights() {
		nodesList.setHighlightsFalse(searchStartNode, searchEndNode);
		segmentsList.setHighlightsFalse();
	}

	@Override
	protected void onSearch() {
		clearHighlights();
		String input = getSearchBox().getText().toLowerCase();
		ArrayList<String> words = (ArrayList<String>) nameTrie.getWords(input);
		Set<Road> selectedRoads = new HashSet<Road>();
		for (Road road : roadsList.getAllRoads()) {
			if (road.getLabel().equals(input)) {
				selectedRoads.add(road);
			}
		}
		if (!selectedRoads.isEmpty()) {
			getTextOutputArea().setText("Found " + input);
			for (Road road : selectedRoads) {
				road.highlightAllSegments();
			}
		} else if (selectedRoads.isEmpty() && !words.isEmpty()) {
			getTextOutputArea().setText("Roads beginning with " + input + ": ");
			for (int i = 0; i < words.size() && i < 10; i++) {
				if (i != 0) {
					getTextOutputArea().append(", " + words.get(i));
				} else {
					getTextOutputArea().append(" " + words.get(i));
				}
			}
			for (Road road : roadsList.getAllRoads()) {
				if (road.getLabel().startsWith(input)) {
					road.highlightAllSegments();
				}
			}
		} else {
			getTextOutputArea().setText(
					"No roads found matching '" + input + "'");
		}
	}

	@Override
	protected void onMove(Move m) {
		double scaleFactor;
		if (scale <= 45) {
			scaleFactor = 1;
		} else if (scale > 45 && scale <= 115) {
			scaleFactor = 2;
		} else {
			scaleFactor = 3;
		}
		int posChange = 6 / (int) scaleFactor;
		switch (m) {
		case NORTH:
			paneOrigin = paneOrigin.moveBy(0, posChange);
			break;
		case SOUTH:
			paneOrigin = paneOrigin.moveBy(0, -posChange);
			break;
		case WEST:
			paneOrigin = paneOrigin.moveBy(-posChange, 0);
			break;
		case EAST:
			paneOrigin = paneOrigin.moveBy(posChange, 0);
			break;
		case ZOOM_IN:
			if (scale < 255) {
				scale += 10 * scaleFactor;
			}
			break;
		case ZOOM_OUT:
			if (scale > 5) {
				scale += -10 * scaleFactor;
			}
			break;
		}
		getTextOutputArea().setText("Scale: " + scale);
	}

	@Override
	protected void onLoad(File nodes, File roads, File segments, File polygons) {
		nodesList.initialiseNodeList(nodes);
		roadsList.initialiseNodeList(roads);
		segmentsList.initialiseSegmentsList(segments);
		nameTrie.initialiseTrie(roadsList.getAllRoads());
		// polyList.initialisePolyList(polygons);
	}

	public static NodeList getNodesList() {
		return nodesList;
	}

	public static RoadList getRoadsList() {
		return roadsList;
	}

	public SegmentsList getSegmentsList() {
		return segmentsList;
	}

	public static void main(String[] args) {
		new RoadSystem();
	}

}
