package track.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import track.model.Deliverer;
import track.model.Delivery;
import track.model.DeliveryStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {

    List<Delivery> findDeliveryByDeliveryStatus(@NonNull DeliveryStatus deliveryStatus);

    Optional<Delivery> findDeliveryByDeliveryNumber(@NonNull String deliveryNumber);

    List<Delivery> findByDeliveryStatusIn(List<DeliveryStatus> deliveryStatusList);

    List<Delivery> findByDelivererAndDeliveryStatusIn(@NonNull Deliverer deliverer, List<DeliveryStatus> deliveryStatusList);

    Delivery findDeliveryByDeliveryNumberAndDeliverer(@NonNull String deliveryNumber, @NonNull Deliverer deliverer);

    @Transactional
    int deleteByDeliveryNumberAndDeliverer(@NonNull String deliveryNumber, @NonNull Deliverer deliverer);

    @Transactional
    int deleteByDeliveryId(int id);

//    @Transactional
//    Delivery save(@NonNull Delivery delivery);
}
