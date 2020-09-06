package quasar.utilidad;

import java.util.Arrays;
import java.util.Map;

import quasar.dto.salida.PosicionDTO;
import quasar.excepcion.QuasarException;
import quasar.model.Satelite;

/**
 * Clase encargada de gestionar todo lo referente a al calculo de la posicion origen del mensaje
 */
public class Ubicador {

	
	/**
	 * Metodo que se encarga de calcular la posicion de la fuente de origen del
	 * mensaje, realiza validaciones de información necesaria y posibilidad de
	 * calcular el origen del mensaje.
	 * 
	 * Utiliza el método de pitagoras para las operaciones
	 * 
	 * @param distancias Mapa con la información de las distancias en donde la key
	 *                   es el nombre del satelite y el value es la distancia
	 * @return retorna objeto con la posición cálculada del origen del mensaje
	 * @throws QuasarException indica: <br>
	 *                         UBICACIONES_INCOMPLETAS_EXCEPTION -> Información
	 *                         incompleta para el calculo del origen <br>
	 *                         SIN_CALCULO_UBICACION_EXCEPTION -> Indica que con la
	 *                         información suministrada no se puede calcular la
	 *                         ubicacion <br>
	 *                         LOCALIZADOR_EXCEPTION -> Error generar en el proceso
	 *                         de calculo del origen <br>
	 */
	public static PosicionDTO getUbicacion(Map<String, Double> distancias) throws QuasarException {

		try {

			// Se valida que la data contenga la minima informacion necesaria para su
			// calculo
			if (distancias == null || (distancias.containsKey(Satelite.NOMBRE_KENOBI)
					&& distancias.containsKey(Satelite.NOMBRE_SKYWALKER) & distancias.containsKey(Satelite.NOMBRE_SATO)) == false) {

				throw new QuasarException(QuasarException.UBICACIONES_INCOMPLETAS_EXCEPTION);
			}

			double distanciaAKenobyCuadrado = Math.pow(distancias.get(Satelite.NOMBRE_KENOBI), 2);
			double distanciaASkywalkerCuadrado = Math.pow(distancias.get(Satelite.NOMBRE_SKYWALKER), 2);
			double distanciaASatoCuadrado = Math.pow(distancias.get(Satelite.NOMBRE_SATO), 2);

			double distanciaKenobiASkywalkerCuadrado = Math.pow((Satelite.UBICACION_KENOBI[0] - Satelite.UBICACION_SKYWALKER[0]), 2)
					+ Math.pow((Satelite.UBICACION_KENOBI[1] - Satelite.UBICACION_SKYWALKER[1]), 2);
			double distanciaSkywalkerASatoCuadrado = Math.pow((Satelite.UBICACION_SKYWALKER[0] - Satelite.UBICACION_SATO[0]), 2)
					+ Math.pow((Satelite.UBICACION_SKYWALKER[1] - Satelite.UBICACION_SATO[1]), 2);

			// Se valida que con la data que se tiene se pueda trianguar la ubicación

			double[] ordenComparacionKenobiASkaywalker = { Math.sqrt(distanciaKenobiASkywalkerCuadrado),
					Math.sqrt(distanciaASkywalkerCuadrado), Math.sqrt(distanciaAKenobyCuadrado) };
			Arrays.sort(ordenComparacionKenobiASkaywalker);

			double[] ordenComparacionSkywalkerASato = { Math.sqrt(distanciaSkywalkerASatoCuadrado), Math.sqrt(distanciaASkywalkerCuadrado),
					Math.sqrt(distanciaASatoCuadrado) };
			Arrays.sort(ordenComparacionSkywalkerASato);

			// Se valida si las distancias entre los satelites y el origen son mayores a la distancia entre los satelites
			if ((ordenComparacionKenobiASkaywalker[2] - ordenComparacionKenobiASkaywalker[1]
					- ordenComparacionKenobiASkaywalker[0] > 0)
					|| (ordenComparacionSkywalkerASato[2] - ordenComparacionSkywalkerASato[1]
							- ordenComparacionSkywalkerASato[0] > 0)) {
			
				throw new QuasarException(QuasarException.SIN_CALCULO_UBICACION_EXCEPTION);
			}
					

			// Empieza proceso de cálculos de coordenadas

			// se usa para simplificar la complejidad de las siguientes ecuaciones
			double calculoParcialParaEcuaciones = distanciaASkywalkerCuadrado
					- (Math.pow(Satelite.UBICACION_SKYWALKER[1], 2) + Math.pow(Satelite.UBICACION_SKYWALKER[0], 2)) - distanciaAKenobyCuadrado
					+ (Math.pow(Satelite.UBICACION_KENOBI[0], 2) + Math.pow(Satelite.UBICACION_KENOBI[1], 2));

			// Valor del cálculo para el numerador utilizado para encontrar Equis
			double valorEquisNumerador = distanciaASatoCuadrado - Math.pow(Satelite.UBICACION_SATO[0], 2) - Math.pow(Satelite.UBICACION_SATO[1], 2)
					- distanciaAKenobyCuadrado + Math.pow(Satelite.UBICACION_KENOBI[0], 2) + Math.pow(Satelite.UBICACION_KENOBI[1], 2)
					- ( 2 * Satelite.UBICACION_KENOBI[1] * ( calculoParcialParaEcuaciones / (2 * ( Satelite.UBICACION_KENOBI[1] - Satelite.UBICACION_SKYWALKER[1] ) ) ) ) 
					+ ( 2 * Satelite.UBICACION_SATO[1] * ( calculoParcialParaEcuaciones / (2 * (Satelite.UBICACION_KENOBI[1] - Satelite.UBICACION_SKYWALKER[1] ) ) ) );

			// Valor del cálculo para el denumerador utilizado para encontrar Equis
			double valorEquisDenumerador = (2 * Satelite.UBICACION_KENOBI[0]) - (2 * Satelite.UBICACION_SATO[0])
					+ ( ( 4 * Satelite.UBICACION_KENOBI[1] * Satelite.UBICACION_SKYWALKER[0] ) / (2 * (Satelite.UBICACION_KENOBI[1] - Satelite.UBICACION_SKYWALKER[1])))
					- ( ( 4 * Satelite.UBICACION_KENOBI[0] * Satelite.UBICACION_KENOBI[1] )  / (2 * (Satelite.UBICACION_KENOBI[1] - Satelite.UBICACION_SKYWALKER[1])))
					- ( ( 4 * Satelite.UBICACION_SATO[1] * Satelite.UBICACION_SKYWALKER[0] ) / (2 * (Satelite.UBICACION_KENOBI[1] - Satelite.UBICACION_SKYWALKER[1])))
					+ ( ( 4 * Satelite.UBICACION_SATO[1] * Satelite.UBICACION_KENOBI[0] ) / (2 * (Satelite.UBICACION_KENOBI[1] - Satelite.UBICACION_SKYWALKER[1])));

			double valorEquisCalculado = valorEquisNumerador / valorEquisDenumerador;

			double valorYeCalculado = (calculoParcialParaEcuaciones + (2 * Satelite.UBICACION_SKYWALKER[0] * valorEquisCalculado)
					- (2 * Satelite.UBICACION_KENOBI[0] * valorEquisCalculado)) / (2 * (Satelite.UBICACION_KENOBI[1] - Satelite.UBICACION_SKYWALKER[1]));
			
		
			return new PosicionDTO(Util.formatearDigito(valorEquisCalculado), Util.formatearDigito(valorYeCalculado));

		} catch (QuasarException q) {

			throw q;

		} catch (Exception e) {
e.printStackTrace();
			throw new QuasarException(QuasarException.LOCALIZADOR_EXCEPTION);
		}

	}

}
