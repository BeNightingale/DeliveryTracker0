package track;

import com.google.gson.*;
import track.model.Deliverer;
import track.model.dto.DeliveryDto;
import track.model.dto.StatusChange;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static track.DeserializerHelper.getAsStringOrNull;

public class PolishPostJsonDeserializer implements JsonDeserializer<DeliveryDto> {
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
        final JsonElement numberElem = jsonObject.get("number");
        final String deliveryNumber = getAsStringOrNull(numberElem);
        final JsonObject mailInfoObject = jsonObject.getAsJsonObject("mailInfo");
        final JsonElement finishedElem = mailInfoObject == null ? null : mailInfoObject.get("finished");
        final Boolean finished = finishedElem == null ? null : finishedElem.getAsBoolean();
        // TODO bezpieczniej jest wybrac status
        // Statusy (name) są uporządkowane od najstarszego do najbliższego obecnej chwili; najświeższy status jest ostatni na liście.
        final JsonArray eventsArray = mailInfoObject == null ? null : mailInfoObject.getAsJsonArray("events");
        List<StatusChange> statusChangesList;
        if (eventsArray == null) {
            statusChangesList = Collections.emptyList();
        } else {
            statusChangesList = new ArrayList<>();
            for (int i = 0; i < eventsArray.size(); i++) {
                final JsonObject arrayObject = eventsArray.get(i).getAsJsonObject();
                if (arrayObject == null) {
                    continue;
                }
                final JsonElement nameElem = arrayObject.get("name");
                final String changedStatus = getAsStringOrNull(nameElem);
                final JsonElement changeTimestampElem = arrayObject.get("time");
                final String changeTimestamp = getAsStringOrNull(changeTimestampElem);
                final StatusChange statusChange = new StatusChange(
                        changedStatus,
                        DeserializerHelper.parseStringToLocalDateTime(changeTimestamp)
                );
                statusChangesList.add(statusChange);
            }
            statusChangesList.sort(Comparator.comparing(StatusChange::getStatusChangeTimeStamp));// rosnąco ustawia czas, czyli ostatnie miejsce ma najświeższy
        }
        final String currentStatus = statusChangesList.isEmpty() ?
                null :
                statusChangesList
                        .get(statusChangesList.size() - 1)
                        .getStatus();
        // Wpisuje status przekazany przez Pocztę Polską = krótki opis statusu.
        // Statusy maja nazwy tj. statusy w Poczty Polskiej.
        return DeliveryDto.builder()
                .deliveryNumber(deliveryNumber)
                .deliveryStatus(currentStatus)
                .statusDescription(currentStatus)
                .statusChangesList(statusChangesList)
                .deliverer(Deliverer.POCZTA_POLSKA)
                .finished(finished)
                .build();
    }
}
