package track.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import track.mapper.Mapper;
import track.model.Deliverer;
import track.model.Delivery;
import track.model.DeliveryStatus;
import track.model.dto.DeliveryDto;
import track.repository.DeliveryRepository;

import java.util.List;

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
        final Deliverer deliverer = deliveryDto.getDeliverer();
        final Delivery delivery = deliveryRepository.findDeliveryByDeliveryNumberAndDeliverer(deliveryDto.getDeliveryNumber(), deliverer);
        log.debug("[Before update] State delivery in database: {}", delivery);
        if (delivery == null) {
            return 0;
        }
        log.info("[Before update] Found delivery in database with deliveryNumber = {} and status {}.", delivery.getDeliveryNumber(), delivery.getDeliveryStatus());
        // Mapowanie na status api DeliveryTracker.
        final DeliveryStatus deliveryDtoStatus = Mapper.statusMapperFunctions
                .get(deliverer)
                .apply(deliveryDto.getDeliveryStatus());
        log.debug("[Before update] Delivery in {} api with deliveryNumber = {} has current status {}.",
                deliverer, delivery.getDeliveryNumber(), delivery.getDeliveryStatus());
        if (deliveryDtoStatus == delivery.getDeliveryStatus())
            return 0;
        delivery.setDeliveryStatus(deliveryDtoStatus);
        delivery.setStatusDescription(deliveryDto.getStatusDescription());
        delivery.setFinished(deliveryDto.isFinished());
        final Delivery afterUpdate = deliveryRepository.save(delivery);
        log.debug("[update] New delivery state expected after update in database: {}.", afterUpdate);
        return 1;
    }
}
