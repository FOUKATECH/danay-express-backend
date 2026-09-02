package cm.danayexpress.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Point d'entrée de l'application Danay Express Backend.
 *
 * Architecture : monolithe modulaire (voir CDC section 15 et 16).
 * Organisation "package by feature" : chaque module métier (referentiel,
 * parcautomobile, exploitation, etc.) est autonome et suit la même
 * structure interne (controller / service / repository / entity / dto /
 * mapper / exception / enums).
 */
@SpringBootApplication
@EnableCaching
@EnableJpaAuditing
public class DanayExpressBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DanayExpressBackendApplication.class, args);
    }
}
