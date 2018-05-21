package campus.hackday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HackdayApplication {

  public static void main(String[] args) {
    SpringApplication.run(HackdayApplication.class, args);
  }
}
