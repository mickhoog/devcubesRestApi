package Main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"Controllers"})

public class main {

    private static final Logger logger = LoggerFactory.getLogger(main.class);


    public static void main(String[] args) {
        SpringApplication.run(main.class, args);
    }

    public void run(String... strings) throws Exception {

/*

        List<User> users = new ArrayList<>();
        users.add(new User("voor", "achter", new GameInformation(50)));
        users.add(new User("2", "2", new GameInformation(2)));
        userRepository.save(users);
        for (User user : userRepository.findAll()) {
            logger.info(user.toString());
        }*/

    }


}

