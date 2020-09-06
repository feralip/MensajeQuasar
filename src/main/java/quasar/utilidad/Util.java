package quasar.utilidad;

import java.text.DecimalFormat;

/**
 * Clase utilitaria, con metodos genericos que se podrían reutilizar mas adelante
 */
public class Util {
	
	public static Double formatearDigito(Double valorFormatear) {
		
		 DecimalFormat twoDForm = new DecimalFormat("#.##");
		    return Double.valueOf(twoDForm.format(valorFormatear));

	}

}
