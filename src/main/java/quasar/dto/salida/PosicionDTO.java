package quasar.dto.salida;

public class PosicionDTO {
	
	private Double x;
	private Double y;
	
	public PosicionDTO(Double x, Double y) {
		
		this.x = x;
		this.y = y;
	}
	
	public Double getX() {
		return x;
	}
	public void setX(Double x) {
		this.x = x;
	}
	public Double getY() {
		return y;
	}
	public void setY(Double y) {
		this.y = y;
	}
	
	public boolean equals(Object posicion) {
		
		PosicionDTO valorComparar = (PosicionDTO) posicion;
		
		if(this.x.compareTo(valorComparar.x) == 0  && this.y.compareTo(valorComparar.y) == 0 ) {
			return true;
		}
		
		return false;
	}
	
	

}
