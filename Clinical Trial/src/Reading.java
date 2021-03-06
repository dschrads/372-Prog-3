public class Reading {
	private String readingId;
	private String type;
	private double value;
	private String bpValue;
	private long date;

	protected Reading(String readingId, String type, double value, long date) {
		this.readingId = readingId;
		this.type = type;
		this.value = value;
		this.date = date;
	}

	protected Reading(String readingId, String type, String bpValue, long date) {
		this.readingId = readingId;
		this.type = type;
		this.bpValue = bpValue;
		this.date = date;
	}

	public String getReadingId() {
		return readingId;
	}

	public String getType() {
		return type;
	}

	public double getValue() {
		return value;
	}

	public String getBpValue() {
		return bpValue;
	}

	public Long getDate() {
		return date;
	}

	public String toString() {
		String string = "Reading ID: " + readingId + "\n" + "Type: " + type + "\n";
		if (bpValue == null) {
			string = string + "Value: " + value + "\n";
		}else{
			string = string + "Value: " + bpValue + "\n";
		}

		string = string + "Date: " + date;
		return string;
	}
}