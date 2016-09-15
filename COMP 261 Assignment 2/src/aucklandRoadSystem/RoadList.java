package aucklandRoadSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoadList {

	private Map<Integer, Road> roadMap;
	private ArrayList<Road> roadList;
	
	public RoadList() {
		roadMap = new HashMap<Integer, Road>();
		roadList = new ArrayList<Road>();
	}
	
	public void initialiseNodeList(File fName){
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
				int type = Integer.parseInt(values[1]);
				String label = values[2];
				String city = values[3];
				int ow = Integer.parseInt(values[4]);
				boolean oneWay = false;
				if(ow == 1){oneWay = true;}
				int speed = Integer.parseInt(values[5]);
				int roadClass = Integer.parseInt(values[6]);
				int nfc = Integer.parseInt(values[7]);
				boolean notForCar = false;
				if(nfc == 1){oneWay = true;}
				int nfp = Integer.parseInt(values[8]);
				boolean notForPed = false;
				if(nfp == 1){oneWay = true;}
				int nfcy = Integer.parseInt(values[9]);
				boolean notForCyc = false;
				if(nfcy == 1){oneWay = true;}
				Road tempRoad = new Road(roadID, type, label, city, oneWay, speed, roadClass, notForCar, notForPed, notForCyc);
				roadMap.put(roadID, tempRoad);
				roadList.add(tempRoad);
			}
			data.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Road getRoad(int roadID){
		if(roadMap.containsKey(roadID)){
			return roadMap.get(roadID);
		}else{
			return null;
		}	
	}
	
	public ArrayList<Road> getAllRoads(){
		return roadList;
	}
}
