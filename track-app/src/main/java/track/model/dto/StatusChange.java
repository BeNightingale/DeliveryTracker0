package track.model.dto;

import lombok.*;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
public class StatusChange {
    private final String status;
    private final String statusChangeTimeStamp;
}
