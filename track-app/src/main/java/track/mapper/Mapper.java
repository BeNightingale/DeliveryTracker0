package track.mapper;

import track.model.Deliverer;
import track.model.DeliveryStatus;

import java.util.Map;
import java.util.function.Function;

import static track.model.Deliverer.INPOST;
import static track.model.Deliverer.POCZTA_POLSKA;

public class Mapper {

    private Mapper() {
        // do nothing
    }

    private static final Function<String, DeliveryStatus> inPostMapFunction = InPostStatusMapper::toDeliveryStatusMapper;
    private static final Function<String, DeliveryStatus> polishPostMapFunction = PolishPostStatusMapper::toDeliveryStatusMapper;

    public static final Map<Deliverer, Function<String, DeliveryStatus>> mapperFunctions = Map.of(
            INPOST, inPostMapFunction,
            POCZTA_POLSKA, polishPostMapFunction
    );
}
