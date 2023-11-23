package track.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
public class StatusChange {
    private final String status;
    private final LocalDateTime statusChangeTimeStamp;
}
