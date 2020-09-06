package quasar.servicio;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import quasar.dto.entrada.ListaSateliteDTO;
import quasar.dto.entrada.MensajeSateliteDTO;
import quasar.dto.salida.RespuestaDTO;
import quasar.excepcion.QuasarException;
import quasar.model.Satelite;

class TestMensaje {


	/**
	 * Caso de prueba para validar la excepcion que se genera cuando el array de mensajes viene vacio
	 */
	@Test
	void mensajeVacio() {

		// las siguientes lineas contienen los calculos necesarios para encontrar las
		// distancias de los satelites
		double x = 0;
		double y = 0;

		Double distanciaKenobi = Math.sqrt(Math.pow(Satelite.UBICACION_KENOBI[0] - x, 2) + Math.pow(Satelite.UBICACION_KENOBI[1] - y, 2));
		Double distanciaSkywalker = Math.sqrt(Math.pow(Satelite.UBICACION_SKYWALKER[0] - x, 2) + Math.pow(Satelite.UBICACION_SKYWALKER[1] - y, 2));
		Double distanciaSato = Math.sqrt(Math.pow(Satelite.UBICACION_SATO[0] - x, 2) + Math.pow(Satelite.UBICACION_SATO[1] - y, 2));

		// las isguientes lineas contienen las combinaciones de mensajes para la
		// validacion de los errores de los mismos
		TopSecret desencriptador = new TopSecret();

		ListaSateliteDTO lista = new ListaSateliteDTO();
		lista.setSatelites(new ArrayList<>());
		String[] mensajeKenoby = {};
		lista.getSatelites().add(new MensajeSateliteDTO("kenobi", distanciaKenobi, mensajeKenoby));
		String[] mensajeSkywalker = {};
		lista.getSatelites().add(new MensajeSateliteDTO("skywalker", distanciaSkywalker, mensajeSkywalker));
		String[] mensajeSato = {};
		lista.getSatelites().add(new MensajeSateliteDTO("sato", distanciaSato, mensajeSato));

		ResponseEntity<String> response = desencriptador.listarBitacora(lista);

		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
		assertEquals(response.getBody(),
				new QuasarException(QuasarException.MENSAJE_NO_DECODIFICADO_EXCEPTION).getMessage());

	}

	/**
	 * Caso de prueba para validar la excepcion que se genera cuando el mensaje viene incompleto
	 */
	@Test
	void mensajeIncompleto() {

		// las siguientes lineas contienen los calculos necesarios para encontrar las
		// distancias de los satelites
		double x = 0;
		double y = 0;

		Double distanciaKenobi = Math.sqrt(Math.pow(Satelite.UBICACION_KENOBI[0] - x, 2) + Math.pow(Satelite.UBICACION_KENOBI[1] - y, 2));
		Double distanciaSkywalker = Math.sqrt(Math.pow(Satelite.UBICACION_SKYWALKER[0] - x, 2) + Math.pow(Satelite.UBICACION_SKYWALKER[1] - y, 2));
		Double distanciaSato = Math.sqrt(Math.pow(Satelite.UBICACION_SATO[0] - x, 2) + Math.pow(Satelite.UBICACION_SATO[1] - y, 2));

		// las isguientes lineas contienen las combinaciones de mensajes para la
		// validacion de los errores de los mismos
		TopSecret desencriptador = new TopSecret();

		ListaSateliteDTO lista = new ListaSateliteDTO();
		lista.setSatelites(new ArrayList<>());
		String[] mensajeKenoby = { "este", "", "", "mensaje", "" };
		lista.getSatelites().add(new MensajeSateliteDTO("kenobi", distanciaKenobi, mensajeKenoby));
		String[] mensajeSkywalker = { "este", "", "", "mensaje", "" };
		lista.getSatelites().add(new MensajeSateliteDTO("skywalker", distanciaSkywalker, mensajeSkywalker));
		String[] mensajeSato = { "", "", "", "", "" };
		lista.getSatelites().add(new MensajeSateliteDTO("sato", distanciaSato, mensajeSato));

		ResponseEntity<String> response = desencriptador.listarBitacora(lista);

		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
		assertEquals(response.getBody(),
				new QuasarException(QuasarException.MENSAJE_NO_DECODIFICADO_EXCEPTION).getMessage());

	}

	/**
	 * Caso de prueba para validar cuando el mensaje se encuentra completo
	 */
	@Test
	void mensajeCorrecto() {

		// las siguientes lineas contienen los calculos necesarios para encontrar las
		// distancias de los satelites
		double x = 0;
		double y = 0;

		Double distanciaKenobi = Math.sqrt(Math.pow(Satelite.UBICACION_KENOBI[0] - x, 2) + Math.pow(Satelite.UBICACION_KENOBI[1] - y, 2));
		Double distanciaSkywalker = Math.sqrt(Math.pow(Satelite.UBICACION_SKYWALKER[0] - x, 2) + Math.pow(Satelite.UBICACION_SKYWALKER[1] - y, 2));
		Double distanciaSato = Math.sqrt(Math.pow(Satelite.UBICACION_SATO[0] - x, 2) + Math.pow(Satelite.UBICACION_SATO[1] - y, 2));
		
		System.out.println("Distancia Kenoby " + distanciaKenobi);
		System.out.println("Distancia Skywalker " + distanciaSkywalker);
		System.out.println("Distancia Sato " + distanciaSato);

		// las isguientes lineas contienen las combinaciones de mensajes para la
		// validacion de los errores de los mismos
		TopSecret desencriptador = new TopSecret();

		ListaSateliteDTO lista = new ListaSateliteDTO();
		lista.setSatelites(new ArrayList<>());
		String[] mensajeKenoby = { "este", "", "", "mensaje", "", "", "", "alianza" };
		lista.getSatelites().add(new MensajeSateliteDTO("kenobi", distanciaKenobi, mensajeKenoby));
		String[] mensajeSkywalker = { "", "es", "", "", "encriptado", "", "" };
		lista.getSatelites().add(new MensajeSateliteDTO("skywalker", distanciaSkywalker, mensajeSkywalker));
		String[] mensajeSato = { "", "", "un", "", "", "por", "la" };
		lista.getSatelites().add(new MensajeSateliteDTO("sato", distanciaSato, mensajeSato));

		ResponseEntity<RespuestaDTO> response = desencriptador.listarBitacora(lista);

		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().getMessage(), "este es un mensaje encriptado por la alianza ");

	}

}
