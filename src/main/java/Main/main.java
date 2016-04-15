package Main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import javax.persistence.*;

@SpringBootApplication
@ComponentScan(basePackages = {"Controllers"})

public class main {
    public static void main(String[] args) {
        SpringApplication.run(main.class, args);
    }
}

