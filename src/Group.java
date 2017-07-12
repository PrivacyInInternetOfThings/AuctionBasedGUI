
import java.util.ArrayList;


public class Group {

	ArrayList<Vehicle> vehicles = new ArrayList<>();
	ArrayList<Vehicle> sortedVehicles = new ArrayList<>();
	int id;
	boolean leadership = true;
	double totalLostPrivacy = 0;
	double totalGroupPrivacy = 0;
	
	public void setLeaderShip(boolean isLeadership) {
		this.leadership = isLeadership;
	}
	public Group(int id) {
		this.id = id;
	}

	public void addVehicle(Vehicle v) {
		v.groupOrder = vehicles.size();
		vehicles.add(v);
		if(vehicles.isEmpty()) {
			totalGroupPrivacy = totalLostPrivacy = 0;
		}
		totalGroupPrivacy += v.totalPrivacy;
		if(leadership)
			sortedVehicles = sortVehicles();
		else sortedVehicles = vehicles;
	}

	public ArrayList<Vehicle> sortVehicles() {
		ArrayList<Double> points = new ArrayList<>();
		ArrayList<Integer> indexes = new ArrayList<>();
		ArrayList<Vehicle> res = new ArrayList<>();
		for (int i = 0; i < vehicles.size(); ++i) {
			points.add(vehicles.get(i).calculateUtilityPoints());
		}

		int maxIndex = 0;
		double maxVal = 0;
		for (int i = 0; i < vehicles.size(); ++i) {
			for (int j = 0; j < vehicles.size(); ++j) {
				if (points.get(j) > maxVal) {
					maxVal = points.get(j);
					maxIndex = j;
				}
			}

			indexes.add(maxIndex);
			points.set(maxIndex, (double) 0);
		}

		for (int i = 0; i < vehicles.size(); ++i) {
			res.add(vehicles.get(indexes.get(i)));
		}

		return res;

	}

	public double makeOffer(double opponentOffer) {
		return sortedVehicles.get(0).makeOffer(opponentOffer, sortedVehicles.get(0).isTurn);
	}

	public boolean updateGroup(int outCar) {
		for (int i = 0; i <= outCar; i++) {
			System.out.println("Vehicle " + vehicles.get(0).id + " has passed");
			totalLostPrivacy += vehicles.get(0).lostPrivacy;
			vehicles.remove(0);
		}
		for (int i = 0; i < vehicles.size(); i++) {
			vehicles.get(i).groupOrder = i;
		}
		if(leadership)
			sortedVehicles = sortVehicles();
		else sortedVehicles = vehicles;
		System.out.println("Group Lost/Total Privacy: "+totalLostPrivacy+"/"+totalGroupPrivacy);
		return this.vehicles.isEmpty();
	}
	
}
