package quasar.servicio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import quasar.dto.entrada.ListaSateliteDTO;
import quasar.dto.entrada.MensajeSateliteDTO;
import quasar.dto.salida.PosicionDTO;
import quasar.dto.salida.RespuestaDTO;
import quasar.utilidad.Desencriptor;
import quasar.utilidad.Ubicador;

@RestController
@RequestMapping("/topsecret")
public class TopSecret {

	@PostMapping
	public ResponseEntity listarBitacora(@RequestBody ListaSateliteDTO listaSatelites) {

		try {
			//Si no hay satelites en la lista devuelve error 404
			if (listaSatelites == null || listaSatelites.getSatelites() == null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			
			
			//Procesa la información enviada por los satelites y la clasifica para su procesamiento
			List<MensajeSateliteDTO> satelitesProcesar = listaSatelites.getSatelites();
			//Guarda la información de los mensajes enviados
			List<String[]> mensajesProcesar = new ArrayList<>();
			int tamanoMaximoMensaje = 0;
			//Guarda la información de las ubicaciones
			Map<String,Double> distanciasSatelites = new HashMap();
			for(MensajeSateliteDTO iteraMensaje: satelitesProcesar) {
			
				int tamanoMensaje = iteraMensaje.getMessage().length;
				if(tamanoMaximoMensaje < tamanoMensaje) {
					tamanoMaximoMensaje = tamanoMensaje;
				}
				
				distanciasSatelites.put(iteraMensaje.getName(), iteraMensaje.getDistance());
				
				mensajesProcesar.add(iteraMensaje.getMessage());
			}
			
			String mensajeDesencriptado = Desencriptor.getMensaje(mensajesProcesar, tamanoMaximoMensaje);
			PosicionDTO posicion = Ubicador.getUbicacion(distanciasSatelites);
			RespuestaDTO respuesta = new RespuestaDTO(mensajeDesencriptado, posicion);

			return new ResponseEntity(respuesta, HttpStatus.OK);
			
		} catch (Exception e) {
			
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
