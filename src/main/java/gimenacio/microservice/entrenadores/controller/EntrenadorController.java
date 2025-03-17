package gimenacio.microservice.entrenadores.controller;

import gimenacio.microservice.entrenadores.model.Entrenador;
import gimenacio.microservice.entrenadores.service.EntrenadorService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@FeignClient(name = "entrenadores", url = "http://localhost:8085")
@RestController
@RequestMapping("/entrenadores")
public class EntrenadorController {

    private final EntrenadorService entrenadorService;

    public EntrenadorController(EntrenadorService entrenadorService) {
        this.entrenadorService = entrenadorService;
    }

    /**
     * Crear un nuevo entrenador.
     */
    @PostMapping
    public ResponseEntity<Entrenador> crearEntrenador(@RequestBody Entrenador entrenador) {
        Entrenador nuevoEntrenador = entrenadorService.registrarEntrenador(entrenador);
        return new ResponseEntity<>(nuevoEntrenador, HttpStatus.CREATED);
    }

    /**
     * Obtener la lista de todos los entrenadores.
     */
    @GetMapping
    public ResponseEntity<List<Entrenador>> listarEntrenadores() {
        List<Entrenador> entrenadores = entrenadorService.listarEntrenadores();
        return ResponseEntity.ok(entrenadores);
    }

    /**
     * Obtener un entrenador por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Entrenador> obtenerEntrenador(@PathVariable Long id) {
        Entrenador entrenador = entrenadorService.obtenerEntrenador(id);
        if (entrenador != null) {
            return ResponseEntity.ok(entrenador);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Actualizar un entrenador existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Entrenador> actualizarEntrenador(@PathVariable Long id, @RequestBody Entrenador entrenador) {
        Entrenador entrenadorActualizado = entrenadorService.actualizarEntrenador(id, entrenador);
        if (entrenadorActualizado != null) {
            return ResponseEntity.ok(entrenadorActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Eliminar un entrenador.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEntrenador(@PathVariable Long id) {
        entrenadorService.eliminarEntrenador(id);
        return ResponseEntity.noContent().build();
    }
}
