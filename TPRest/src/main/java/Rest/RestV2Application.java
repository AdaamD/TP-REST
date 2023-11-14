package Rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//Indique où trouver les Models
@EntityScan(basePackages = "Rest.Models")
@EnableJpaRepositories(basePackages = "Rest.Repository")
@SpringBootApplication(scanBasePackages = {"Rest.Data", "Rest.Controller"})
public class RestV2Application {

    public static void main(String[] args) {
        SpringApplication.run(RestV2Application.class, args);
    }

}
