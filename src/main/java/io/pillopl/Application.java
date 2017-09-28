package io.pillopl;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Random;
import java.util.UUID;

@EnableScheduling
@EnableBinding(Source.class)
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Application {

    private final UserRepository userRepository;

    public Application(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.run(args);
    }

    @Scheduled(fixedRate = 2000L)
    public void randomUsers() {
        User user = new User(UUID.randomUUID());
        user.activate();
        user.changeNicknameTo("Name" + new Random().nextInt(10));
        userRepository.save(user);
    }

}
