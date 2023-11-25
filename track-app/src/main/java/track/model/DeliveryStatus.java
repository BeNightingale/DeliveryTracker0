package track.model;

import lombok.Getter;

@Getter
public enum DeliveryStatus {

    UNKNOWN("The parcel number just persisted in database. Status not updated by InPost api yet."),
    NOT_FOUND("The parcel number wasn't found."),
    CONFIRMED("Prepared by the sender. The shipment's journey has not started yet."),
    HANDED_TO_SHIPPING_COMPANY("The parcel has been received from the sender."),
    IN_SHIPPING_PARCEL_LOCKER("Parcel sent. It is waiting to be taken out of the parcel locker by the deliverer."),
    ON_THE_ROAD("The parcel continues its journey."),
    HANDED_OUT_FOR_DELIVERY("The parcel will be delivered to the recipient no later than the next working day. It can be delivered even late evening."),
    WAITING_IN_RECEIVING_PARCEL_LOCKER("Waiting for pick up."),
    WAITING_FOR_COLLECTING("The parcel is waiting to be collected."),
    DELIVERED("The recipient received the parcel."),
    NOT_STANDARD_STAGE("Other not standard parcel journey stage.");

    private final String description;

    DeliveryStatus(String description) {
        this.description = description;
    }
}
