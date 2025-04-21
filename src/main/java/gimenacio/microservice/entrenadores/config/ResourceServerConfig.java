package gimenacio.microservice.entrenadores.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Usamos el nuevo DSL authorizeHttpRequests en lugar de authorizeRequests
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/actuator/**").permitAll()   // equivalencia a antMatchers
                        .anyRequest().authenticated()
                )
                // Configuramos el Resource Server para validar JWT
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                )
                // Desactivamos CSRF dado que expone una API REST
                .csrf(cs -> cs.disable());

        return http.build();
    }
}
