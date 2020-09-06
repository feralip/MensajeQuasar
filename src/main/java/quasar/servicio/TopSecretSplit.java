package quasar.servicio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import quasar.dto.entrada.ListaSateliteDTO;
import quasar.dto.entrada.MensajeSateliteDTO;
import quasar.dto.entrada.MensajeSateliteSimpleDTO;
import quasar.dto.salida.PosicionDTO;
import quasar.dto.salida.RespuestaDTO;
import quasar.model.Satelite;
import quasar.utilidad.Desencriptor;
import quasar.utilidad.Ubicador;
import quasar.utilidad.UnificadorInformacion;

@RestController
@RequestMapping
public class TopSecretSplit {

	@PostMapping("/topsecret_split/{satelite}")
	public ResponseEntity listarBitacora(@RequestBody MensajeSateliteSimpleDTO mensajeSatelite,
			@PathVariable("satelite") String satelite) {

		try {
			// Si no hay satelites en la lista devuelve error
			if (mensajeSatelite == null || satelite == null || mensajeSatelite.getMessage() == null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}

			// Si el nombre del satelite ingresado no es valido retorna error
			if (!Arrays.asList(Satelite.LISTA_NOMBRE_SATELITES).contains(satelite)) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}

			// Guarda la información de las ubicaciones y mensajes en el unificador
			UnificadorInformacion.getInstance().getDistanciaSatelites().put(satelite, mensajeSatelite.getDistance());
			UnificadorInformacion.getInstance().getMensajeSatelite().put(satelite, mensajeSatelite.getMessage());

			// Guarda la información de los mensajes enviados
			List<String[]> mensajesProcesar = new ArrayList<>();
			int tamanoMaximoMensaje = 0;
			Set<String> listaLlavesMensaje = UnificadorInformacion.getInstance().getMensajeSatelite().keySet();
			for (String iteraLLaveMensaje : listaLlavesMensaje) {

				String[] mensaje = UnificadorInformacion.getInstance().getMensajeSatelite().get(iteraLLaveMensaje);
				int tamanoMensaje = mensaje.length;
				if (tamanoMaximoMensaje < tamanoMensaje) {
					tamanoMaximoMensaje = tamanoMensaje;
				}

				mensajesProcesar.add(mensaje);
			}

			String mensajeDesencriptado = Desencriptor.getMensaje(mensajesProcesar, tamanoMaximoMensaje);
			PosicionDTO posicion = Ubicador.getUbicacion(UnificadorInformacion.getInstance().getDistanciaSatelites());
			RespuestaDTO respuesta = new RespuestaDTO(mensajeDesencriptado, posicion);

			return new ResponseEntity(respuesta, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/topsecret_split")
	public ResponseEntity listarBitacora() {

		try {

			// Guarda la información de los mensajes enviados
			List<String[]> mensajesProcesar = new ArrayList<>();
			int tamanoMaximoMensaje = 0;
			Set<String> listaLlavesMensaje = UnificadorInformacion.getInstance().getMensajeSatelite().keySet();
			for (String iteraLLaveMensaje : listaLlavesMensaje) {

				String[] mensaje = UnificadorInformacion.getInstance().getMensajeSatelite().get(iteraLLaveMensaje);
				int tamanoMensaje = mensaje.length;
				if (tamanoMaximoMensaje < tamanoMensaje) {
					tamanoMaximoMensaje = tamanoMensaje;
				}

				mensajesProcesar.add(mensaje);
			}

			String mensajeDesencriptado = Desencriptor.getMensaje(mensajesProcesar, tamanoMaximoMensaje);
			PosicionDTO posicion = Ubicador.getUbicacion(UnificadorInformacion.getInstance().getDistanciaSatelites());
			RespuestaDTO respuesta = new RespuestaDTO(mensajeDesencriptado, posicion);

			return new ResponseEntity(respuesta, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

}
