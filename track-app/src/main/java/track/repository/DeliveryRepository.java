package track.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import track.model.Delivery;
import track.model.DeliveryStatus;
import track.model.dto.DeliveryDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {

    Optional<Delivery> findByDeliveryStatus(@NonNull DeliveryStatus deliveryStatus);

    Optional<Delivery> findDeliveryByDeliveryNumber(@NonNull String deliveryNumber);

    List<Delivery> findByDeliveryStatusIn(List<DeliveryStatus> deliveryStatusList);
}
