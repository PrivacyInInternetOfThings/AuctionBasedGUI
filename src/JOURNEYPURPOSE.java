
public enum JOURNEYPURPOSE {
	PARTOFWORK(1), COMMUTINGTOWORK(0.5), SCHOOL(0.5), OTHER(0.3);

	double utilityValue;

	private JOURNEYPURPOSE(double val) {
		this.utilityValue = val;
	}

	public double getValue() {
		return this.utilityValue;
	}

	public static String abbreviation(JOURNEYPURPOSE e) {
		switch (e) {
		case PARTOFWORK:
			return "POW";
		case COMMUTINGTOWORK:
			return "CTW";
		case SCHOOL:
			return "SCH";
		case OTHER:
			return "OTH";
		default:
			return null;
		}
	}
}