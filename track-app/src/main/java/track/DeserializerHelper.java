package track;

import com.google.gson.JsonElement;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

@Slf4j
public class DeserializerHelper {

    private DeserializerHelper() {
        // do nothing
    }

    public static String getAsStringOrNull(JsonElement jsonElement) {
        return jsonElement == null ? null : jsonElement.getAsString();
    }

    public static LocalDateTime parseStringToLocalDateTime(String stringTimestamp) {
        try {
            log.debug("Data zmiany statusu stringTimestamp = {}.", stringTimestamp);
            return StringUtils.isEmpty(stringTimestamp) ? null : LocalDateTime.parse(stringTimestamp);
        } catch (Exception ex) {
            log.debug("Niepowodzenie parsowania daty zmiany statusu, stringTimestamp = {}.", stringTimestamp);
        }
        return null;
    }
}
