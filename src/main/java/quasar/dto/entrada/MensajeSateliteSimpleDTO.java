package quasar.dto.entrada;

public class MensajeSateliteSimpleDTO {
	
	private Double distance;
	private String[] message;
	
	public MensajeSateliteSimpleDTO( Double distance, String[] message) {
	
		this.distance = distance;
		this.message = message;
	}
	
	
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public String[] getMessage() {
		return message;
	}
	public void setMessage(String[] message) {
		this.message = message;
	}

}
