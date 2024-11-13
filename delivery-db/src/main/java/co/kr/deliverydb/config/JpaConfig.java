package co.kr.deliverydb.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EntityScan(basePackages = "co.kr.deliverydb.domain")
@EnableJpaRepositories(basePackages = "co.kr.deliverydb.repository")
public class JpaConfig {
}
