package pl.specialist.searchexpert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SearchexpertApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchexpertApplication.class, args);
    }

}
