package quasar.excepcion;

/**
 * Clase encargada del manejo de las excepciones del programa
 */
public class QuasarException extends Exception {
	
	public static final int DESENCRIPTADOR_EXCEPTION = 1;
	public static final int LOCALIZADOR_EXCEPTION = 2;
	public static final int MENSAJE_NO_DECODIFICADO_EXCEPTION = 3;
	public static final int UBICACIONES_INCOMPLETAS_EXCEPTION = 4;
	public static final int SIN_CALCULO_UBICACION_EXCEPTION = 5;
	
	private int codigoExcepcion ;
	
	public QuasarException(int codigoExcepcion) {
		super();
		this.codigoExcepcion = codigoExcepcion;
		
	}
	
	
	
	@Override
	public String getMessage(){
        
        String mensaje="";
         
        switch(codigoExcepcion){
            case DESENCRIPTADOR_EXCEPTION:
                mensaje="Ocurrió un error desenciptando el mensaje de la transmisión.";
                break;
            case LOCALIZADOR_EXCEPTION:
                mensaje="Ocurrió un error localizando el origen del mensaje.";
                break;
            case MENSAJE_NO_DECODIFICADO_EXCEPTION:
                mensaje="Error, no se pudo decodificar el mensaje.";
                break;
            case UBICACIONES_INCOMPLETAS_EXCEPTION:
                mensaje="Error, falta información para el cálculo el origen del mensaje.";
                break;
            case SIN_CALCULO_UBICACION_EXCEPTION:
                mensaje="Error, no se puede calcular el origen del mensaje.";
                break;

        }
         
        return mensaje;
         
    }

}
