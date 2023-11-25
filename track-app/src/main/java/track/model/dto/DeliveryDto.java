package track.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import track.mapper.InPostStatusMapper;
import track.mapper.Mapper;
import track.model.Deliverer;
import track.model.Delivery;

import java.util.List;

import static track.model.DeliveryStatus.UNKNOWN;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@Builder
public class DeliveryDto {

    private int deliveryId;
    @NotNull
    private String deliveryNumber;
    private String deliveryStatus; // status string dokładnie tj. pobrany z api dostawcy (z jsona); może być null -> będzie zmapowany na UNKNOWN
    @Size(max = 3000)
    private String statusDescription;
    @NotNull
    private Deliverer deliverer;
    @Size(max = 2000)
    private String deliveryDescription;
    private Boolean finished;
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
        delivery.setDeliveryStatus(
                this.deliveryStatus == null ?
                        UNKNOWN :
                        Mapper.mapperFunctions
                                .get(this.deliverer)
                                .apply(this.deliveryStatus));
        delivery.setStatusDescription(this.statusDescription);
        delivery.setDeliverer(this.deliverer);
        delivery.setDeliveryDescription(this.deliveryDescription);
        delivery.setFinished(this.finished);
        return delivery;
    }
}
