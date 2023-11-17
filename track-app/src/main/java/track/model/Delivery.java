package track.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="delivery_id")
    private int deliveryId;
    @Column(name="delivery_number")
    private String deliveryNumber;
    @Column(name="delivery_status")
    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus deliveryStatus;
    @Column(name="deliverer")
    private String deliverer;
    @Column(name = "delivery_description")
    private String deliveryDescription;
}
