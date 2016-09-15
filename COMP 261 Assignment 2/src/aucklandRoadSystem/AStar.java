package aucklandRoadSystem;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class AStar {

	public AStar() {

	}

	public Node search(Node startNode, Node endNode) {
		// Minor change on Dijkstra's algorithm:
		// Initialise: for all nodes visited=false, pathFrom=null
		Queue<Node> fringe = new PriorityQueue<Node>(new NodeComparator());
		List<Node> nodesList = RoadSystem.getNodesList().getAllNodes();
		for (Node node : nodesList) {
			node.setVisited(false);
			node.setPathFrom(null);
			node.setEstCost(node.getLoc().distance(endNode.getLoc()));
		}
		// enqueue(<start, null, 0, estimate(start, goal ) , fringe)
		for (Segment segment : startNode.getSegments()) {
			Node temp = segment.getOtherEnd(startNode);
			temp.setPathFrom(startNode);
			fringe.add(temp);
		}
		Node from = startNode;
		// Repeat until fringe is empty:
		while (!fringe.isEmpty()) {
//			printQueue(fringe);
			// <node, from, costToHere, totalCostToGoal>=dequeue(fringe)
			Node tempNode = fringe.poll();
			// If not node.visited then
				if (!tempNode.isVisited()) {
					// node.visited=true, node.pathFrom=from,
					// node.cost=costToHere
					tempNode.setVisited(true);
					//tempNode.setPathFrom(from);
					tempNode.setActCost(tempNode.isConnected(tempNode.getPathFrom()).getLength());
					from = tempNode;
				}
				// If node = goal then exit
				if (tempNode.equals(endNode)) {
					return endNode;// <---------------
				}
				// for each edge to neigh out of node
				List<Node> neighbours = tempNode.getNeighbours();
				for (Node neighbour : neighbours) {
					// if not neigh.visited then
					if (!neighbour.isVisited()) {
						neighbour.setPathFrom(from);
						// costToNeigh=costToHere + edge.weight
						neighbour.setActCost(tempNode.getActCost()
								+ neighbour.isConnected(neighbour.getPathFrom()).getLength());
						// estTotal=costToNeigh + estimate(neighbour, goal )
						neighbour.setEstCost(neighbour.getActCost()
								+ neighbour.getEstCost());
						// fringe.enqueue(<neighbour, node, costToNeigh,
						// estTotal>)
						fringe.add(neighbour);
					}
					//
					// •fringe = priority queue, ordered by total cost to Goal
					// •estimate(node, goal) must be admissible and consistent.
				}
		}
		return null;
	}

	private void printQueue(Queue<Node> fringe) {
		System.out.println("Printing Queue");
		int i = 0;
		for(Node node: fringe){
			System.out.println(i+": "+node.toString()+" Est Cost: "+node.getEstCost());
			i++;
		}
		System.out.println("Done");
	}

	static class NodeComparator implements Comparator<Node> {
		@Override
		public int compare(Node x, Node y) {
			if (x.getEstCost() < y.getEstCost()) {
				return -1;
			}
			if (x.getEstCost() > y.getEstCost()) {
				return 1;
			}
			return 0;
		}
	}
}
