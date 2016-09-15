package aucklandRoadSystem;

import java.util.Comparator;

public class ArtPtComparator implements Comparator<ArticulationPoint> {
		@Override
		public int compare(ArticulationPoint x, ArticulationPoint y) {
			if (x.getDepth() < y.getDepth()) {
				return -1;
			}
			if (x.getDepth() > y.getDepth()) {
				return 1;
			}
			return 0;
		}
}
