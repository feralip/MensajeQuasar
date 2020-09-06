package quasar.utilidad;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase encargada de centralizar los mensajes enviados individualmente por los satelites
 */
public class UnificadorInformacion {
	
	private static final UnificadorInformacion unificadorInformacion = new UnificadorInformacion();
	
	private Map<String, Double> distanciaSatelites;
	private Map<String, String[]> mensajeSatelite;
	
	
	public static UnificadorInformacion getInstance() {
				
		return unificadorInformacion;
		
	}

	public Map<String, Double> getDistanciaSatelites() {
		if(this.distanciaSatelites == null) {
			this.distanciaSatelites = new HashMap();
		}
		return distanciaSatelites;
	}

	public Map<String, String[]> getMensajeSatelite() {
		if(this.mensajeSatelite == null) {
			this.mensajeSatelite = new HashMap();
		}
		return mensajeSatelite;
	}

	

}
