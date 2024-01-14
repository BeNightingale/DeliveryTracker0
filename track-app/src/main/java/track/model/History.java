package track.model;

import jakarta.persistence.*;
import lombok.*;
import track.model.dto.DeliveryDto;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    @Column(name="delivery_number") // Powinno być prawie unikatowe i niepuste, ale dla różnych dostawców może się powtórzyć.
    private String deliveryNumber;
    @Enumerated(value = EnumType.STRING)
    @Column(name="delivery_status")
    private DeliveryStatus deliveryStatus;
    @Column(name="status_description")
    private String statusDescription;
    @Column(name="status_change_datetime")
    private LocalDateTime statusChangeDatetime;

//    public History createHistoryFromDeliveryDtoInfo(int deliveryId, DeliveryDto deliveryDto) {
//        this.deliveryId = deliveryId;
//        this.deliveryNumber = deliveryDto.getDeliveryNumber();
//        this.deliveryStatus = deliveryDto.;
//        this.statusDescription = statusDescription;
//        this.statusChangeDatetime = statusChangeDatetime;
//    }
}
