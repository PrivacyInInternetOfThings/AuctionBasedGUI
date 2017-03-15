
public enum VEHICLETYPE {
	AMBULANCE(1), FIRETRUCK(0.95), POLICE(0.9), CAR(0.4), TAXI(0.4), VAN(0.3), BUS(0.3), MINIBUS(0.3), MOTORCYCLE(0.2), AGRICULTURALVEHICLE(0.1);

	double utilityValue;

	private VEHICLETYPE(double val) {
		this.utilityValue = val;
	}

	public double getValue() {
		return this.utilityValue;
	}

	public static String abbreviation(VEHICLETYPE v) {
		if (v == VEHICLETYPE.AMBULANCE) {
			return "AMB";
		} else if (v == VEHICLETYPE.FIRETRUCK) {
			return "FIR";
		} else if (v == VEHICLETYPE.POLICE) {
			return "POL";
		} else if (v == VEHICLETYPE.CAR) {
			return "CAR";
		} else if (v == VEHICLETYPE.TAXI) {
			return "TAX";
		} else if (v == VEHICLETYPE.VAN) {
			return "VAN";
		} else if (v == VEHICLETYPE.BUS) {
			return "BUS";
		} else if (v == VEHICLETYPE.MINIBUS) {
			return "MBS";
		} else if (v == VEHICLETYPE.MOTORCYCLE) {
			return "MTR";
		} else if (v == VEHICLETYPE.AGRICULTURALVEHICLE) {
			return "AGR";
		}
		return null;
	}
}