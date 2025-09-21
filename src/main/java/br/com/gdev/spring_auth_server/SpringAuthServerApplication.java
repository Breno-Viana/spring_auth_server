package br.com.gdev.spring_auth_server;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringAuthServerApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder sb = new SpringApplicationBuilder(SpringAuthServerApplication.class);
        sb.run(args);
        ConfigurableApplicationContext context = sb.context();
        context.setId("oauth-server-id");

    }

}
