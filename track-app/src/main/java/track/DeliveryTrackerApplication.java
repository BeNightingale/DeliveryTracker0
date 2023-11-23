package track;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import track.model.dto.StatusChange;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@SpringBootApplication
public class DeliveryTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliveryTrackerApplication.class, args);
//        List<StatusChange> statusChangesList = new ArrayList<>();
//        statusChangesList.add(new StatusChange("DE", LocalDateTime.of(2022, 11, 2, 11, 12)));
//        statusChangesList.add(new StatusChange("aa", LocalDateTime.of(2011,1,2,1,1,1)));
//        statusChangesList.add(new StatusChange("z", LocalDateTime.of(2023,12,2,2,2,2)));
//        statusChangesList.sort(Comparator.comparing(StatusChange::getStatusChangeTimeStamp));
//        System.out.println(statusChangesList.toString());
//
//        System.out.println(LocalDateTime.parse("2023-11-22T10:36:47.000"));//+01:00"));
    }
}
