package track;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import track.repository.DeliveryRepository;

@SpringBootApplication
public class DeliveryTrackerApplication {

    public static void main(String[] args) {
      SpringApplication.run(DeliveryTrackerApplication.class, args);
    }
}
