package track.mapper;

import org.apache.commons.lang3.StringUtils;
import track.model.DeliveryStatus;

import static track.model.DeliveryStatus.*;

public class PolishPostStatusMapper {

    private PolishPostStatusMapper() {
        // do nothing
    }

    public static DeliveryStatus toDeliveryStatusMapper(String polishPostStatusName) {
        if (StringUtils.isEmpty(polishPostStatusName)) {
            return NOT_FOUND;
        }
        if (polishPostStatusName.toLowerCase().matches("(rejestracja przesyłki|otrzymano dane elektroniczne przesyłki).*")) {
            return CONFIRMED;
        }
        if (polishPostStatusName.toLowerCase().matches("(nadanie|przesyłka przyjęta w punkcie|przyjęcie przesyłki).*")){
            return HANDED_TO_SHIPPING_COMPANY;
        }
        if (polishPostStatusName.toLowerCase().matches("")){
            return IN_SHIPPING_PARCEL_LOCKER;
        }
        if (polishPostStatusName.toLowerCase().matches("(wysłanie przesyłki|przyjęto w polsce|nadejście).*")) {
            return ON_THE_ROAD;
        }
        if (polishPostStatusName.toLowerCase().matches("(przygotowano do doręczenia|przekazano do doręczenia).*")) {
            return HANDED_OUT_FOR_DELIVERY;
        }
        if (polishPostStatusName.toLowerCase().matches(
                "(do odbioru w placówce|awizo przesyłki|awizo - przesyłka do odbioru|ponowne awizo|awizo - do ponownego doręczenia|powiadomienie email o oczekiwaniu przesyłki|powiadomienie sms o oczekiwaniu przesyłki).*")) {
            return WAITING_FOR_COLLECTING;
        }
        if (polishPostStatusName.toLowerCase().matches(
                "((doręczenie|przesyłka odebrana|odebrano|odebranie przesyłki w urzędzie).*)|(wydanie przesyłki uprawnionemu do odbioru)|(powiadomienie sms o doręczeniu przesyłki do skrzynki pocztowej)")) {
            return DELIVERED;
        }
        return NOT_STANDARD_STAGE;
    }
}
