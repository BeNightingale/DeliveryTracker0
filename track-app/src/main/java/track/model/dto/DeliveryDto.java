package track.model.dto;

import lombok.*;

import java.util.List;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@Builder
public class DeliveryDto {
    private int deliveryId;
    private String deliveryNumber;
    private String deliveryStatus; // status dokładnie tj. pobrany z api InPost (z jsona)
    private String deliverer;
    private String deliveryDescription;
    private List<StatusChange> statusChangesList;

//    public DeliveryDto(Delivery delivery) {
//        this.deliveryId = delivery.getDeliveryId();
//        this.deliveryNumber = delivery.getDeliveryNumber();


//        this.deliveryStatus = delivery.getDeliveryStatus();
//        this.deliverer = delivery.getDeliverer();
//    }
}
