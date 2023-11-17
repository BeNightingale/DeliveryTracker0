package track;

import com.google.gson.*;
import track.model.dto.DeliveryDto;
import track.model.dto.StatusChange;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InPostJsonDeserializer implements JsonDeserializer<DeliveryDto> {


    @Override
    public DeliveryDto deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        if (jsonElement == null) {
            return null;
        }
        final JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (jsonObject == null) {
            return null;
        }
        final JsonElement deliveryNumberElem = jsonObject.get("tracking_number");
        final String deliveryNumber = getAsStringOrNull(deliveryNumberElem);
        final JsonElement statusElem = jsonObject.get("status");
        final String status = getAsStringOrNull(statusElem);
        final JsonArray trackingDetailsArray = jsonObject.getAsJsonArray("tracking_details");
        List<StatusChange> statusChangesList;
        if (trackingDetailsArray == null) {
            statusChangesList = Collections.emptyList();
        } else {
            statusChangesList = new ArrayList<>();
            for (int i = 0; i < trackingDetailsArray.size(); i++) {
                final JsonObject arrayObject = trackingDetailsArray.get(i).getAsJsonObject();
                if (arrayObject == null) {
                    continue;
                }
                final JsonElement changedStatusElem = arrayObject.get("status");
                final String changedStatus = getAsStringOrNull(changedStatusElem);
                final JsonElement changeTimestampElem = arrayObject.get("datetime");
                final String changeTimestamp = getAsStringOrNull(changeTimestampElem);
                final StatusChange statusChange = new StatusChange(changedStatus, changeTimestamp);
                statusChangesList.add(statusChange);
            }
        }
        return DeliveryDto.builder()
                .deliveryNumber(deliveryNumber)
                .deliveryStatus(status)
                .statusChangesList(statusChangesList)
                .deliverer("InPost")
                .build();
    }

    public String getAsStringOrNull(JsonElement jsonElement) {
        return jsonElement == null ? null : jsonElement.getAsString();
    }
}