package gimenacio.microservice.entrenadores;

import gimenacio.microservice.entrenadores.model.Entrenador;
import gimenacio.microservice.entrenadores.repository.EntrenadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private EntrenadorRepository entrenadorRepository;

    @Override
    public void run(String... args) throws Exception {
        // Cargar entrenadores de ejemplo
        Entrenador entrenador1 = new Entrenador();
        entrenador1.setNombre("Carlos Rodríguez");
        entrenador1.setEspecialidad("Yoga");
        entrenador1.setCalificacion(4.8);
        entrenador1.setDisponible(true);
        entrenadorRepository.save(entrenador1);

        Entrenador entrenador2 = new Entrenador();
        entrenador2.setNombre("Ana Martínez");
        entrenador2.setEspecialidad("Spinning");
        entrenador2.setCalificacion(4.6);
        entrenador2.setDisponible(true);
        entrenadorRepository.save(entrenador2);

        System.out.println("Entrenadores de ejemplo cargados exitosamente.");
    }
}
