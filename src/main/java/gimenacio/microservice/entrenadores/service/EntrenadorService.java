package gimenacio.microservice.entrenadores.service;


import gimenacio.microservice.entrenadores.model.Entrenador;
import gimenacio.microservice.entrenadores.repository.EntrenadorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EntrenadorService {

    private final EntrenadorRepository entrenadorRepository;

    public EntrenadorService(EntrenadorRepository entrenadorRepository) {
        this.entrenadorRepository = entrenadorRepository;
    }

    /**
     * Registra un nuevo entrenador en el sistema.
     */
    public Entrenador registrarEntrenador(Entrenador entrenador) {
        return entrenadorRepository.save(entrenador);
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
     * Actualiza la información de un entrenador.
     */
    public Entrenador actualizarEntrenador(Long id, Entrenador entrenadorActualizado) {
        Entrenador entrenadorExistente = obtenerEntrenador(id);
        if (entrenadorExistente != null) {
            entrenadorExistente.setNombre(entrenadorActualizado.getNombre());
            entrenadorExistente.setEspecialidad(entrenadorActualizado.getEspecialidad());
            entrenadorExistente.setCalificacion(entrenadorActualizado.getCalificacion());
            entrenadorExistente.setDisponible(entrenadorActualizado.getDisponible());
            return entrenadorRepository.save(entrenadorExistente);
        }
        return null;
    }

    /**
     * Elimina un entrenador del sistema.
     */
    public void eliminarEntrenador(Long id) {
        entrenadorRepository.deleteById(id);
    }
}
