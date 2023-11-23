package track.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@ToString
@Table(name = "deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="delivery_id")
    private int deliveryId;
    @Column(name="delivery_number") // powinno być prawie unikatowe i niepuste!
    private String deliveryNumber;
    @Enumerated(value = EnumType.STRING)
    @Column(name="delivery_status")
    private DeliveryStatus deliveryStatus;
    @Enumerated(value = EnumType.STRING)
    @Column(name="deliverer")
    private Deliverer deliverer;
    @Column(name = "delivery_description")
    private String deliveryDescription;
}
