package track.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import track.StatusMapper;
import track.model.Deliverer;
import track.model.Delivery;

import java.util.List;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@Builder
public class DeliveryDto {

    private int deliveryId;
    @NotNull
    private String deliveryNumber;
    private String deliveryStatus; // status dokładnie tj. pobrany z api InPost (z jsona)
    @NotNull
    private Deliverer deliverer;
    @Size(max = 2000)
    private String deliveryDescription;
    private List<StatusChange> statusChangesList;

//    public DeliveryDto(Delivery delivery) {
//        this.deliveryId = delivery.getDeliveryId();
//        this.deliveryNumber = delivery.getDeliveryNumber();
//        this.deliveryStatus = delivery.getDeliveryStatus();
//        this.deliverer = delivery.getDeliverer();
//    }

    public Delivery toDelivery() {
        final Delivery delivery = new Delivery();
        delivery.setDeliveryId(this.deliveryId);
        delivery.setDeliveryNumber(this.deliveryNumber);
        delivery.setDeliveryStatus(StatusMapper.toDeliveryStatusMapper(this.deliveryStatus));
        delivery.setDeliverer(this.deliverer);
        delivery.setDeliveryDescription(this.deliveryDescription);
        return delivery;
    }
}
