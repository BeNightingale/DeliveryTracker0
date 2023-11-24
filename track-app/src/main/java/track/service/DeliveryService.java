package track.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import track.InPostStatusMapper;
import track.model.Delivery;
import track.model.DeliveryStatus;
import track.model.dto.DeliveryDto;
import track.repository.DeliveryRepository;

import java.util.List;

import static track.model.Deliverer.INPOST;

@Service
@AllArgsConstructor
@Slf4j
public class DeliveryService {

    DeliveryRepository deliveryRepository;

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
        if (delivery == null) {
            return 0;
        }
        log.info("Found delivery in database with deliveryNumber = {} and status {}.", delivery.getDeliveryNumber(), delivery.getDeliveryStatus());
        // Mapowanie na status api DeliveryTracker.
        final DeliveryStatus deliveryDtoStatus = InPostStatusMapper.toDeliveryStatusMapper(deliveryDto.getDeliveryStatus());
        log.debug("Delivery in InPost api with deliveryNumber = {} has status {}.", delivery.getDeliveryNumber(), delivery.getDeliveryStatus());
        if (deliveryDtoStatus == delivery.getDeliveryStatus())
            return 0;
        delivery.setDeliveryStatus(deliveryDtoStatus);
        delivery.setStatusDescription(deliveryDto.getStatusDescription());
        deliveryRepository.save(delivery);
        return 1;
    }
}
