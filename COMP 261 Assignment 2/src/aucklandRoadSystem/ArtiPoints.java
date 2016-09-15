package aucklandRoadSystem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

public class ArtiPoints {

	private ArrayList<ArticulationPoint> articulationPoints;
	private ArrayList<ArticulationPoint> nodePoints;
	Stack<ArticulationPoint> apStack;
	private int max = Integer.MAX_VALUE;

	public ArtiPoints() {
		articulationPoints = new ArrayList<ArticulationPoint>();
		nodePoints = new ArrayList<ArticulationPoint>();
		apStack = new Stack<ArticulationPoint>();
	}

	public HashSet<Node> identifyArtPts(List<Node> nodes) {
		for (Node node : nodes) {
			node.setDepth(0);
			nodePoints.add(new ArticulationPoint(node, 0, max));
		}
		for (ArticulationPoint start : nodePoints) {
			start.setDepth(0);
			int numSubtrees = 0;
			addNeighbours(start);
			// for each neighbour of start
			for (ArticulationPoint neighbour : start.getChildren()) {
				// if neighbour.depth = Infinity then
				if (neighbour.getDepth() == max) {
					// iterArtPts( neighbour, start)
					iterArtPts(neighbour, start);
					// numSubtrees ++
					numSubtrees++;
				}
				// if numSubtrees > 1 then add start to articulationPoints
				if (numSubtrees > 1) {
					articulationPoints.add(start);
				}
			}
		}
		return artPtsToSet();
	}

//	iterArtPoints(firstNode, root):
//	  push (firstNode, 1, <root, 0, ->) onto stack
//	  while stack not empty
//	    elem = peek at stack,   node = elem.node
//	    if elem.children = null
//	      node.depth = elem.depth,  elem.reach = elem.depth
//	      elem.children = new queue
//	      for each neighbour  of  node 
//	        if neighbour != elem.parent.node then
//	          add neighbour to elem.children
//	else if elem.children not empty
//	  child = dequeue  elem.children
//	  if child.depth < infinity then elem.reach = min(elem.reach, child.depth)
//	  else push <child, node.depth+1, elem> onto stack
//	else
//	  if node != firstNode
//	    if elem.reach >= elem.parent.depth then
//	      add  elem.parent.node  to articulationPoints
//	    elem.parent.reach =  min (elem.parent.reach,   elem.reach)
//	  pop elem from stack
	private void iterArtPts(ArticulationPoint firstNode, ArticulationPoint start) {
		firstNode.setParent(start);
		apStack.push(firstNode);
		while(!apStack.isEmpty()){
			ArticulationPoint elem = apStack.peek();
			Node node = elem.getNode();
			if(elem.getChildren() == null){
				node.setDepth(elem.getDepth());
				elem.setReach(elem.getDepth());
				PriorityQueue<ArticulationPoint> children = new PriorityQueue<ArticulationPoint>(new ArtPtComparator());
				for(Node neighbour: node.getNeighbours()){
					if(findNeighbour(neighbour, elem) != null){
						children.add(findNeighbour(neighbour, elem));						
					}
				}
				elem.setChildren(children);
			}
			else if(!elem.getChildren().isEmpty()){
				ArticulationPoint child = elem.getChildren().poll();
				if(child.getDepth()<max){
					elem.setReach(Math.min(elem.getReach(), child.getDepth()));
				}else{
					child.setDepth(child.getDepth() + 1);
					apStack.push(child);
				}
			}
			else{
				if(!node.equals(start.getNode())){
					if(elem.getReach() >= elem.getParent().getDepth()){
						articulationPoints.add(elem.getParent());
					}
				}
				elem.getParent().setReach(Math.min(elem.getParent().getReach(),elem.getReach()));
			}
			apStack.pop();
		}
	}
	
	private ArticulationPoint findNeighbour(Node neighbour, ArticulationPoint elem) {
		for (ArticulationPoint aP : nodePoints) {
			if (neighbour.equals(aP.getNode()) && !neighbour.equals(elem.getParent().getNode())) {
				return aP;
			}
		}
		return null;
	}

	private HashSet<Node> artPtsToSet() {
		HashSet<Node> nodes = new HashSet<Node>();
		for (ArticulationPoint artPt : articulationPoints) {
			nodes.add(artPt.getNode());
		}
		return nodes;
	}

	private void addNeighbours(ArticulationPoint artPt) {
		PriorityQueue<ArticulationPoint> children = new PriorityQueue<ArticulationPoint>(new ArtPtComparator());
		ArrayList<Node> neighbours = artPt.getNode().getNeighbours();
		for (ArticulationPoint aP : nodePoints) {
			if (neighbours.contains(aP.getNode())) {
				children.add(aP);
			}
		}
		artPt.setChildren(children);
	}
}
