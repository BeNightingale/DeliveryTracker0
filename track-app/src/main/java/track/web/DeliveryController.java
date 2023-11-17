package track.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import track.InPostJsonDeserializer;
import track.StatusMapper;
import track.model.Delivery;
import track.model.DeliveryStatus;
import track.model.dto.DeliveryDto;
import track.repository.DeliveryRepository;
import track.service.DeliveryService;
import track.service.HttpCaller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deliveryapi")
@Slf4j
public class DeliveryController {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryService deliveryService;
    private Gson gson = initGson();

    @GetMapping("/deliveries_count")
    public long getDeliveriesCount() {
        return deliveryRepository.findAll().stream().mapToInt(d -> 1).count();
    }

    @GetMapping("/deliveries")
    public List<Delivery> getDeliveries() {
         return deliveryRepository.findAll().stream().toList();
    }

    @GetMapping("/deliveries/{deliveryNumber}")
    public Delivery getDeliveryByNumber(@PathVariable String deliveryNumber) {
        Optional<Delivery> op = deliveryRepository.findDeliveryByDeliveryNumber(deliveryNumber);
        log.debug("op{}", op);
        return op.orElse(new Delivery());
    }

    @GetMapping("/active")
    public String writesth(@PathVariable String deliveryNumber) {
        return HttpCaller.callHttpGetMethod("", deliveryNumber);
       // return "Hello!";
    }
    @GetMapping("/apijsonforone")
    public DeliveryDto toDeliveryDto(@PathVariable String deliveryNumber) {
        String json = HttpCaller.callHttpGetMethod("", deliveryNumber);
        return gson.fromJson(json, DeliveryDto.class);
        // return "Hello!";
    }

    @GetMapping("/activedeliveries")
    public ResponseEntity<Integer> getDeliveriesWithActiveStatusesAndUpdate() {
        final List<Delivery> activeDeliveries = deliveryRepository.findByDeliveryStatusIn(StatusMapper.getActiveStatusesList());
        if (activeDeliveries == null || activeDeliveries.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        final List<DeliveryDto> deliveryDtoList = new ArrayList<>();// będzie mieć statusy InPosta
        for (Delivery delivery : activeDeliveries) {
            if (delivery == null) {
                continue;
            }
            log.debug("Found active delivery in database with delivery_number = {}", delivery.getDeliveryNumber());
            String json = HttpCaller.callHttpGetMethod("", delivery.getDeliveryNumber());
            DeliveryDto deliveryDto = gson.fromJson(json, DeliveryDto.class);
            deliveryDto.setDeliveryId(delivery.getDeliveryId());
            deliveryDto.setDeliveryDescription(delivery.getDeliveryDescription());
            deliveryDtoList.add(deliveryDto);
        }
        Integer rowsChanged = deliveryService.updateActiveDeliveriesStatuses(deliveryDtoList);
        return ResponseEntity.ok().body(rowsChanged);
    }

    private Gson initGson() {
        return new GsonBuilder()
                .registerTypeAdapter(DeliveryDto.class, new InPostJsonDeserializer())
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();
    }
}
