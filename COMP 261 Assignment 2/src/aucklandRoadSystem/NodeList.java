package aucklandRoadSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NodeList {
	private Map<Integer, Node> nodeMap;
	private ArrayList<Node> nodeList;

	public NodeList() {
		nodeMap = new HashMap<Integer, Node>();
		nodeList = new ArrayList<Node>();
	}

	public void initialiseNodeList(File fName) {
		try {
			// Read file line by line
			File nodeFile = new File(fName.getAbsolutePath());
			BufferedReader data = new BufferedReader(new FileReader(nodeFile));
			String line;
			while ((line = data.readLine()) != null) {
				// Process each line using split method
				String[] values = line.split("\t");
				int nodeID = Integer.parseInt(values[0]);
				double lat = Double.parseDouble(values[1]);
				double lon = Double.parseDouble(values[2]);
				Node tempNode = new Node(nodeID, lat, lon);
				nodeMap.put(nodeID, tempNode);
				nodeList.add(tempNode);
			}
			data.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Node getNode(int nodeID) {
		if (nodeMap.containsKey(nodeID)) {
			return nodeMap.get(nodeID);
		} else {
			return null;
		}
	}

	public ArrayList<Node> getAllNodes() {
		return nodeList;
	}

	public void setHighlightsFalse(Node searchStartNode, Node searchEndNode) {
		for (Node node : nodeList) {
			if (!node.equals(searchStartNode) && !node.equals(searchEndNode)) {
				node.setHighlight(false, null);
			}
		}
	}
}
