package track.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import track.mapper.InPostStatusMapper;
import track.mapper.Mapper;
import track.mapper.PolishPostStatusMapper;
import track.model.Deliverer;
import track.model.Delivery;
import track.model.DeliveryStatus;
import track.model.dto.DeliveryDto;
import track.repository.DeliveryRepository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static track.model.Deliverer.INPOST;
import static track.model.Deliverer.POCZTA_POLSKA;

@Service
@AllArgsConstructor
@Slf4j
public class DeliveryService {

    private DeliveryRepository deliveryRepository;

    public int updateActiveDeliveriesStatuses(List<DeliveryDto> deliveryDtoList) {
        log.info("Init: deliveryDtoList = {}.", deliveryDtoList);
        if (deliveryDtoList == null || deliveryDtoList.isEmpty()) {
            log.debug("Nothing to update. List provided by deliverer api is empty.");
            return 0;
        }
        int counter = 0;
        for (DeliveryDto dto : deliveryDtoList) {
            counter += updateDeliveryStatus(dto);
        }
        return counter;
    }

    protected int updateDeliveryStatus(DeliveryDto deliveryDto) {
        if (deliveryDto == null) {
            log.debug("DeliveryDto is null.");
            return 0;
        }
        final Delivery delivery = deliveryRepository.findDeliveryByDeliveryNumberAndDeliverer(deliveryDto.getDeliveryNumber(), INPOST);
        log.debug("[Before update] State delivery in database: {}", delivery);
        if (delivery == null) {
            return 0;
        }
        log.info("Found delivery in database with deliveryNumber = {} and status {}.", delivery.getDeliveryNumber(), delivery.getDeliveryStatus());
        // Mapowanie na status api DeliveryTracker.
        final Deliverer deliverer = delivery.getDeliverer();
        final DeliveryStatus deliveryDtoStatus = Mapper.mapperFunctions.get(deliverer).apply(deliveryDto.getDeliveryStatus());
        log.debug("Delivery in {} api with deliveryNumber = {} has current status {}.",
                deliverer, delivery.getDeliveryNumber(), delivery.getDeliveryStatus());
        if (deliveryDtoStatus == delivery.getDeliveryStatus())
            return 0;
        delivery.setDeliveryStatus(deliveryDtoStatus);
        delivery.setStatusDescription(deliveryDto.getStatusDescription());
        delivery.setFinished(deliveryDto.getFinished());
        final Delivery afterUpdate = deliveryRepository.save(delivery);
        log.debug("[update] New delivery state expected after update in database: {}.", afterUpdate);
        return 1;
    }
}
