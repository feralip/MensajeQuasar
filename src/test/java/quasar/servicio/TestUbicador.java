package quasar.servicio;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import quasar.dto.entrada.ListaSateliteDTO;
import quasar.dto.entrada.MensajeSateliteDTO;
import quasar.dto.salida.PosicionDTO;
import quasar.dto.salida.RespuestaDTO;
import quasar.model.Satelite;

class TestUbicador {
	
	/**
	 * Caso de prueba para validar cuando la información de las distancias son incorrectas
	 */
	@Test
	void distanciaIncorrecta() {
		
		
		Double distanciaKenobi = 1d;
		Double distanciaSkywalker = 1d;
		Double distanciaSato = 1d;
		
		TopSecret desencriptador = new TopSecret();

		ListaSateliteDTO lista = new ListaSateliteDTO();
		lista.setSatelites(new ArrayList<>());
		String[] mensajeKenoby = { "este"};
		lista.getSatelites().add(new MensajeSateliteDTO("kenobi", distanciaKenobi, mensajeKenoby));
		String[] mensajeSkywalker = { "" };
		lista.getSatelites().add(new MensajeSateliteDTO("skywalker", distanciaSkywalker, mensajeSkywalker));
		String[] mensajeSato = { "" };
		lista.getSatelites().add(new MensajeSateliteDTO("sato", distanciaSato, mensajeSato));

		ResponseEntity<String> response = desencriptador.listarBitacora(lista);

		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
		assertEquals(response.getBody(), "Error, no se puede calcular el origen del mensaje.");
	}
	
	/**
	 * Caso de prueba para validar cuando la lista de satelites enviados es incorrecta
	 */
	@Test
	void satelitesIncompletos() {
		
		double x = 0;
		double y = 0;
		
		Double distanciaKenobi = Math.sqrt(Math.pow(Satelite.UBICACION_KENOBI[0] - x, 2) + Math.pow(Satelite.UBICACION_KENOBI[1] - y, 2));
		Double distanciaSkywalker = Math.sqrt(Math.pow(Satelite.UBICACION_SKYWALKER[0] - x, 2) + Math.pow(Satelite.UBICACION_SKYWALKER[1] - y, 2));
		
		TopSecret desencriptador = new TopSecret();

		ListaSateliteDTO lista = new ListaSateliteDTO();
		lista.setSatelites(new ArrayList<>());
		String[] mensajeKenoby = { "este", "", "", "mensaje", "", "", "", "alianza" };
		lista.getSatelites().add(new MensajeSateliteDTO("kenobi", distanciaKenobi, mensajeKenoby));
		String[] mensajeSkywalker = { "", "es", "", "", "encriptado", "", "" };
		lista.getSatelites().add(new MensajeSateliteDTO("skywalker", distanciaSkywalker, mensajeSkywalker));
		String[] mensajeSato = { "", "es", "", "", "encriptado", "", "" };
		lista.getSatelites().add(new MensajeSateliteDTO("satoooo", distanciaSkywalker, mensajeSato));

		ResponseEntity<String> response = desencriptador.listarBitacora(lista);

		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
		assertEquals(response.getBody(), "Error, no se pudo decodificar el mensaje.");
	}
	
	/**
	 * Caso de prueba validar la información exitosa
	 */
	@Test
	void distanciaCorrecta() {
		
		
		double x = 18;
		double y = 18;
		
		Double distanciaKenobi = Math.sqrt(Math.pow(Satelite.UBICACION_KENOBI[0] - x, 2) + Math.pow(Satelite.UBICACION_KENOBI[1] - y, 2));
		Double distanciaSkywalker = Math.sqrt(Math.pow(Satelite.UBICACION_SKYWALKER[0] - x, 2) + Math.pow(Satelite.UBICACION_SKYWALKER[1] - y, 2));
		Double distanciaSato = Math.sqrt(Math.pow(Satelite.UBICACION_SATO[0] - x, 2) + Math.pow(Satelite.UBICACION_SATO[1] - y, 2));
		
		
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
		assertEquals(response.getBody().getPosition(), new PosicionDTO(x,y));
		
		
	}

}
