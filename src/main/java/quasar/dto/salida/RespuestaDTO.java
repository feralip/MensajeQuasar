package quasar.dto.salida;

public class RespuestaDTO {
	
	private PosicionDTO position ; 
	private String message;
	
	public RespuestaDTO(String message, PosicionDTO position) {
		
		this.position = position;
		this.message = message;
		
	}
	
	public PosicionDTO getPosition() {
		return position;
	}
	public void setPosition(PosicionDTO position) {
		this.position = position;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
