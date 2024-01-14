package track.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import track.model.DeliveryStatus;
import track.model.History;

import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Integer> {

    Optional<History> findByDeliveryIdAndDeliveryStatus(int deliveryId, DeliveryStatus deliveryStatus);

}
