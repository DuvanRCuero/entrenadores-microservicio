package gimenacio.microservice.entrenadores.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguracion {

    @Bean
    public TopicExchange intercambioEntrenadores() {
        return new TopicExchange("entrenadores.intercambio");
    }

    @Bean
    public Queue colaEntrenadoresRegistro() {
        return new Queue("entrenadores.registro", true);
    }

    @Bean
    public Queue colaEntrenadoresActualizacion() {
        return new Queue("entrenadores.actualizacion", true);
    }

    @Bean
    public Queue colaEntrenadoresEliminacion() {
        return new Queue("entrenadores.eliminacion", true);
    }

    @Bean
    public Binding enlaceRegistro(Queue colaEntrenadoresRegistro, TopicExchange intercambioEntrenadores) {
        return BindingBuilder.bind(colaEntrenadoresRegistro).to(intercambioEntrenadores).with("entrenadores.registro");
    }

    @Bean
    public Binding enlaceActualizacion(Queue colaEntrenadoresActualizacion, TopicExchange intercambioEntrenadores) {
        return BindingBuilder.bind(colaEntrenadoresActualizacion).to(intercambioEntrenadores).with("entrenadores.actualizacion");
    }

    @Bean
    public Binding enlaceEliminacion(Queue colaEntrenadoresEliminacion, TopicExchange intercambioEntrenadores) {
        return BindingBuilder.bind(colaEntrenadoresEliminacion).to(intercambioEntrenadores).with("entrenadores.eliminacion");
    }
}
