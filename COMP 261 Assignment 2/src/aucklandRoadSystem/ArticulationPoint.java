package aucklandRoadSystem;

import java.util.PriorityQueue;

public class ArticulationPoint {
//	Stack elements contain: 
//		node: graph node to be processed
//		reach: local variable to store current reach back level
//		as well as:
//		parent:   stack element we came from:
//		(a) to not revisit its graph node, 
//		(b) to update its reach
//		depth: that the node will have, if visited via this stack element
//		children: queue of unvisited neighbours to be processed in turn
	
	private Node node;
	private int reach;
	private ArticulationPoint parent;
	private int depth;
	private PriorityQueue<ArticulationPoint> children;
	private boolean visited;

	public ArticulationPoint(Node node, int reach, int depth){
		this.node = node;
		this.reach = reach;
		this.depth = depth;
		visited = false;
	}
	
	public int getNumChildren(){
		return children.size();
	}
	
	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public int getReach() {
		return reach;
	}

	public void setReach(int reach) {
		this.reach = reach;
	}

	public ArticulationPoint getParent() {
		return parent;
	}

	public void setParent(ArticulationPoint parent) {
		this.parent = parent;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public PriorityQueue<ArticulationPoint> getChildren() {
		return children;
	}

	public void setChildren(PriorityQueue<ArticulationPoint> children) {
		this.children = children;
	}

	public void addChild(ArticulationPoint aP) {
		children.add(aP);
	}
}
