package br.com.gdev.spring_auth_server;


import br.com.gdev.spring_auth_server.services.StorageService;
import br.com.gdev.spring_auth_server.config.StoragePropertiesConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties(StoragePropertiesConfig.class)
public class SpringAuthServerApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder sb = new SpringApplicationBuilder(SpringAuthServerApplication.class);
        sb.run(args);
        ConfigurableApplicationContext context = sb.context();
        context.setId("oauth-server-id");

    }

    @Bean
    CommandLineRunner init(StorageService service){
        return (args) -> {
            service.deleteAll();
            service.init();
        };
    }

}
