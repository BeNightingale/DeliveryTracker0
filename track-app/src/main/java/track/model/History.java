package track.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@ToString
@Table(name = "history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="delivery_id")
    private int deliveryId;
    @Column(name="delivery_number") // powinno być prawie unikatowe i niepuste!
    private String deliveryNumber;
    @Enumerated(value = EnumType.STRING)
    @Column(name="delivery_status")
    private DeliveryStatus deliveryStatus;
    @Column(name="status_description")
    private String statusDescription;
    @Column(name="status_change_datetime")
    private LocalDateTime statusChangeDatetime;
}
