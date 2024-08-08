package estudomicroservice.microserviceclients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MicroserviceClientsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceClientsApplication.class, args);
    }

}
