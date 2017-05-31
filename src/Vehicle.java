
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class Vehicle {

	public VEHICLETYPE vehicleType;
	public JOURNEYPURPOSE journeyType;
	public MALFUNCTIONTYPE malfunctionType;
	public int ageOfCar;
	public int groupOrder;
	public double lostPrivacy;
	public double totalPrivacy;
	public boolean isTurn;
	public int id;
	public static int lastId = 0;
	public int reference = 0;
	public static int randomSeed =1000;
	public static Random rand = new Random(randomSeed);
	
	public double threshold;

	// public double vehiclePrivacy;
	// public double emergencyPrivacy;
	// public double malfunctionPrivacy;
	// public double peoplePrivacy;
	public double[] privacy = new double[4];

	public boolean[] enabled = new boolean[4];

	public static double proportionVehicleType = 0.3;
	public static double proportionjourneyType = 0.45;
	public static double proportionMalfunctionType = 0.1;
	public static double proportionVehicleAge = 0.15;

	public double utility;

	public static NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
	public static DecimalFormat df = (DecimalFormat) nf;
	public static NumberFormat formatter = df;

//	public Vehicle(VEHICLETYPE vehicle, JOURNEYPURPOSE emergency, MALFUNCTIONTYPE malfunction, int age, int id) {
//		this.vehicleType = vehicle;
//		this.journeyType = emergency;
//		this.malfunctionType = malfunction;
//		this.ageOfCar = age;
//		this.utility = 0;
//		this.lostPrivacy = 0;
//		this.id = id;
//		this.threshold = 0.8;
//		/*
//		 * setPrivacyRandom(); for (int i = 0; i < 4; i++) {
//		 * System.out.print(privacy[i] + " "); } System.out.println();
//		 * System.out.println();
//		 */
//	}
	public Vehicle(VEHICLETYPE vehicle, JOURNEYPURPOSE emergency, MALFUNCTIONTYPE malfunction, int age) {
		this.vehicleType = vehicle;
		this.journeyType = emergency;
		this.malfunctionType = malfunction;
		this.ageOfCar = age;
		this.utility = 0;
		this.lostPrivacy = 0;
		this.threshold = 0.8;
		this.id = Vehicle.lastId;
		Vehicle.lastId++;
		/*
		 * setPrivacyRandom(); for (int i = 0; i < 4; i++) {
		 * System.out.print(privacy[i] + " "); } System.out.println();
		 * System.out.println();
		 */
	}
	

	public void clear() {
		for (int i = 0; i < 4; i++) {
			enabled[i] = false;
		}
		this.lostPrivacy = 0;
		this.utility = 0;
	}
	

	public void setPrivacy(double vehicle, double emergency, double malfunction, double people) {
		this.privacy[0] = vehicle;
		this.privacy[1] = emergency;
		this.privacy[2] = malfunction;
		this.privacy[3] = people;
		totalPrivacy = vehicle + emergency + malfunction + people;
		for (int i = 0; i < 4; i++) {
			// this.privacy[i] /= 4;
		}
		/*
		 * for (int i = 0; i < 4; i++) { System.out.print(privacy[i] + " "); }
		 * System.out.println(); System.out.println();
		 */
	}

	public void setPrivacyRandom() {
		
		totalPrivacy = 0;

		for (int i = 0; i < 4; i++) {
			this.privacy[i] = rand.nextDouble();
			privacy[i] = (int) (privacy[i] * 1000) / 1000.0;
			totalPrivacy += privacy[i];
		}
	}
	public void setReference(int ref){
		this.reference = ref;
	}

	public void setThreshold(double t) {
		this.threshold = t;
	}
	public void setThreshold(int dayOfWeek, int timeOfDay, int isUrban, int weatherCondition, int roadSurface, int specialCondition, int lightCondition, int ageOfDriver, int engineCapacity){
		if(dayOfWeek == 1){//Sunday
			this.threshold -= this.threshold*0.1;
		}else if (dayOfWeek == 7){//Saturday
			this.threshold -= this.threshold*0.05;
		}
		
		if(timeOfDay == 1){// 08:00 -> 20:00
			this.threshold += this.threshold*0.05;
		}else{ //20:00 -> 08:00
			this.threshold -= this.threshold*0.05;
		}
		
		if(isUrban == 1){
			this.threshold += this.threshold*0.01;
		}else if(isUrban == 2){
			this.threshold -= this.threshold*0.1;
		}
		
		this.threshold -= this.threshold*(0.1/(8-weatherCondition));
		
		this.threshold -= (0.1/(8-roadSurface));
		
		if(specialCondition > 0){
			this.threshold -= 0.1;
		}
		
		if(lightCondition >= 4 && lightCondition <= 7){
			this.threshold -= this.threshold*(0.1/(8-lightCondition));
		}
		
		this.threshold += this.threshold*0.1/ageOfDriver;
		
		this.threshold += this.threshold*0.1/engineCapacity;
		
		if(this.threshold < 0){
			this.threshold = 0;
		}else if(this.threshold > 1){
			this.threshold = 1;
		}
	}

	/**
	 * 
	 * @return indexes of properties that are not enabled
	 */
	public ArrayList<Integer> getEnabledIndex() {

		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < enabled.length; i++) {
			if (!enabled[i]) {
				list.add(i);
			}
		}
		return list;
	}

	public int getMinPrivacy() {
		int minIndex = -1;
		double minVal = 1;
		ArrayList<Integer> list = getEnabledIndex();
		for (int i = 0; i < list.size(); i++) {
			if (privacy[i] < minVal) {
				minIndex = list.get(i);
				minVal = privacy[i];
			}
		}
		if (minIndex >= 0)
			enabled[minIndex] = true;

		return minIndex;
	}

	public int getMinPrivacy(boolean isTurnBased) {
		int minIndex = -1;
		ArrayList<Integer> list = getEnabledIndex();
		if (!list.isEmpty()) {
			minIndex = list.get(0);
			enabled[minIndex] = true;
		}
		return minIndex;
	}

	public double makeOffer(double opponentOffer, boolean isTurnBased) {
		double newOffer = 0;
		double offerLostPrivacy = 0;
		ArrayList<Integer> used = new ArrayList<>();
		int min;
		do {
			if (isTurnBased) {
				min = getMinPrivacy(isTurnBased);
			} else {
				min = getMinPrivacy();
			}
			if (min == 0 && privacy[0] < this.threshold) {
				System.out.println("\tVehicle Type Offer\n\t\tPrivacy Lost = " + privacy[0] + "\n\t\tUtility Gained = "
						+ formatter.format(this.vehicleType.getValue() * proportionVehicleType));
				offerLostPrivacy += privacy[0];
				used.add(0);
				utility += this.vehicleType.getValue() * proportionVehicleType;
				newOffer += this.vehicleType.getValue() * proportionVehicleType;
			}
			if (min == 1 && privacy[1] < this.threshold) {
				System.out
						.println("\tEmergency Type Offer\n\t\tPrivacy Lost = " + privacy[1] + "\n\t\tUtility Gained = "
								+ formatter.format(this.journeyType.getValue() * proportionjourneyType));
				offerLostPrivacy += privacy[1];
				used.add(1);
				utility += this.journeyType.getValue() * proportionjourneyType;
				newOffer += this.journeyType.getValue() * proportionjourneyType;
			}
			if (min == 2 && privacy[2] < this.threshold) {
				System.out
						.println("\tMalfunction Type Offer\n\t\tPrivacy Lost= " + privacy[2] + "\n\t\tUtility Gained = "
								+ formatter.format(this.malfunctionType.getValue() * proportionMalfunctionType));
				offerLostPrivacy += privacy[2];
				used.add(2);
				utility += this.malfunctionType.getValue() * proportionMalfunctionType;
				newOffer += this.malfunctionType.getValue() * proportionMalfunctionType;
			}
			if (min == 3 && privacy[3] < this.threshold) {
				System.out.println("\tNumber of People Offer\n\t\tPrivacy Lost= " + privacy[3] + "\n\tUtility Gained= "
						+ formatter.format(this.ageOfCar / 50.0 * proportionVehicleAge));
				offerLostPrivacy += privacy[3];
				used.add(3);
				utility += (1 - this.ageOfCar / 50.0) * proportionVehicleAge;
				newOffer += (1 - this.ageOfCar / 50.0) * proportionVehicleAge;
			}
			if (min == -1) {
				break;
			}
		} while (newOffer <= opponentOffer);

		if (newOffer <= opponentOffer) {
			for (int i = 0; i < used.size(); i++) {
				enabled[used.get(i)] = false;
			}
			System.out.println();
			System.out.println("\t--No Offer--");
			return 0;
		}
		this.lostPrivacy += offerLostPrivacy;
		return newOffer;

	}

	public double calculateUtilityPoints() {
		double res = 0;

		if (privacy[0] < this.threshold) {

			res += this.vehicleType.getValue() * proportionVehicleType;
		}
		if (privacy[1] < this.threshold) {

			res += this.journeyType.getValue() * proportionjourneyType;
		}
		if (privacy[2] < this.threshold) {

			res += this.malfunctionType.getValue() * proportionMalfunctionType;
		}
		if (privacy[3] < this.threshold) {

			res += this.ageOfCar / 50.0 * proportionVehicleAge;
		}

		return res;

	}

	public String toString() {

		return this.id + "\t" + VEHICLETYPE.abbreviation(this.vehicleType) + "\t"
				+ JOURNEYPURPOSE.abbreviation(this.journeyType) + "\t"
				+ MALFUNCTIONTYPE.abbreviation(this.malfunctionType) + "\t" + this.ageOfCar;

	}

}
