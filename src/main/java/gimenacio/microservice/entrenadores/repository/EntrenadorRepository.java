package gimenacio.microservice.entrenadores.repository;


import gimenacio.microservice.entrenadores.model.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrenadorRepository extends JpaRepository<Entrenador, Long> {
}
