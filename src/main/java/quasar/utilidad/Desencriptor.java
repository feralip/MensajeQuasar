package quasar.utilidad;

import java.util.List;

import quasar.excepcion.QuasarException;

/**
 * Clase encargada de gestionar todo lo referente a la desencripción de los mensajes recibidos
 */
public class Desencriptor {

	/**
	 * Metodo encargado de desencriptar el mensaje enviado a los satelites de Quasar, se encarga de recorrer los diferentes
	 * mensajes enviados a los satelites y concatenar el mensaje	 * 
	 * @param mensajes Lista de array con el contenido de los mensajes
	 * @param tamañoMaximo Indica el tamaño del array mas grande de la lista de mensajes 
	 * @return mensaje desencriptado
	 * @throws QuasarException indica:
	 * MENSAJE_NO_DECODIFICADO_EXCEPTION -> El mensaje tenia información incompleta
	 * DESENCRIPTADOR_EXCEPTION -> Error generar en el proceso de desencriptado
	 */
	public static String getMensaje(List<String[]> mensajes, int tamañoMaximo) throws QuasarException {

		try {

			String[] mensaje = new String[tamañoMaximo];

			for (String[] iteraMensaje : mensajes) {

				int contadorPalabra = 0;
				for (String iteraPalabra : iteraMensaje) {

					if (mensaje[contadorPalabra] == null && iteraPalabra != null && iteraPalabra != "") {

						mensaje[contadorPalabra] = iteraPalabra;
					}
					contadorPalabra++;
				}

			}

			//Se valida que el mensaje haya quedado bien desencriptado
			StringBuilder mensajeDecodificado = new StringBuilder("");
			for (String iteraValidadorMensaje : mensaje) {

				//Si el mensaje contiene espacios vacios se da que no fue bien desencriptado
				if (iteraValidadorMensaje == null) {
					throw new QuasarException(QuasarException.MENSAJE_NO_DECODIFICADO_EXCEPTION);
				}

				mensajeDecodificado.append(iteraValidadorMensaje).append(" ");
			}
			
			if(mensajeDecodificado.toString().equals("")) {
				throw new QuasarException(QuasarException.MENSAJE_NO_DECODIFICADO_EXCEPTION);
			}


			return mensajeDecodificado.toString();
			
		}catch(QuasarException q) {
			throw q;
		}catch(Exception e) {
			throw new QuasarException(QuasarException.DESENCRIPTADOR_EXCEPTION);
		}
		
	}

}
