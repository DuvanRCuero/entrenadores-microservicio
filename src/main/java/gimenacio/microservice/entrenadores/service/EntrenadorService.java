package gimenacio.microservice.entrenadores.service;

import gimenacio.microservice.entrenadores.model.Entrenador;
import gimenacio.microservice.entrenadores.repository.EntrenadorRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EntrenadorService {

    private final EntrenadorRepository entrenadorRepository;
    private final RabbitTemplate rabbitTemplate;

    public EntrenadorService(EntrenadorRepository entrenadorRepository, RabbitTemplate rabbitTemplate) {
        this.entrenadorRepository = entrenadorRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Registra un nuevo entrenador en el sistema y envía una notificación a RabbitMQ.
     */
    public Entrenador registrarEntrenador(Entrenador entrenador) {
        Entrenador nuevoEntrenador = entrenadorRepository.save(entrenador);

        // Enviar mensaje a RabbitMQ
        String mensaje = "👨‍🏫 Nuevo entrenador registrado: " + entrenador.getNombre() + " - " + entrenador.getEspecialidad();
        rabbitTemplate.convertAndSend("entrenadores.intercambio", "entrenadores.registro", mensaje);

        return nuevoEntrenador;
    }

    /**
     * Obtiene la lista de todos los entrenadores.
     */
    @Transactional(readOnly = true)
    public List<Entrenador> listarEntrenadores() {
        return entrenadorRepository.findAll();
    }

    /**
     * Obtiene un entrenador por su ID.
     */
    @Transactional(readOnly = true)
    public Entrenador obtenerEntrenador(Long id) {
        Optional<Entrenador> entrenadorOpt = entrenadorRepository.findById(id);
        return entrenadorOpt.orElse(null);
    }

    /**
     * Actualiza la información de un entrenador y notifica el cambio a RabbitMQ.
     */
    public Entrenador actualizarEntrenador(Long id, Entrenador entrenadorActualizado) {
        Entrenador entrenadorExistente = obtenerEntrenador(id);
        if (entrenadorExistente != null) {
            entrenadorExistente.setNombre(entrenadorActualizado.getNombre());
            entrenadorExistente.setEspecialidad(entrenadorActualizado.getEspecialidad());
            entrenadorExistente.setCalificacion(entrenadorActualizado.getCalificacion());
            entrenadorExistente.setDisponible(entrenadorActualizado.getDisponible());
            Entrenador entrenadorGuardado = entrenadorRepository.save(entrenadorExistente);

            // Enviar notificación de actualización a RabbitMQ
            String mensaje = "🔄 Entrenador actualizado: " + entrenadorGuardado.getNombre();
            rabbitTemplate.convertAndSend("entrenadores.intercambio", "entrenadores.actualizacion", mensaje);

            return entrenadorGuardado;
        }
        return null;
    }

    /**
     * Elimina un entrenador del sistema y envía un mensaje de eliminación a RabbitMQ.
     */
    public void eliminarEntrenador(Long id) {
        Optional<Entrenador> entrenadorOpt = entrenadorRepository.findById(id);
        if (entrenadorOpt.isPresent()) {
            String mensaje = "❌ Entrenador eliminado: " + entrenadorOpt.get().getNombre();
            entrenadorRepository.deleteById(id);

            // Notificar eliminación a RabbitMQ
            rabbitTemplate.convertAndSend("entrenadores.intercambio", "entrenadores.eliminacion", mensaje);
        }
    }
}
