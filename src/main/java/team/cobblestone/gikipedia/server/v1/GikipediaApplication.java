package team.cobblestone.gikipedia.server.v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GikipediaApplication {

    static void main(String[] args) {
        SpringApplication.run(GikipediaApplication.class, args);
    }

}
